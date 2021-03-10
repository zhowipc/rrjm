package wolf.night.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wolf.night.service.config.security.component.JwtTokenUtil;
import wolf.night.service.entity.Admin;
import wolf.night.service.entity.SysRole;
import wolf.night.service.entity.SysUser;
import wolf.night.service.entity.SysUserRole;
import wolf.night.service.entity.vo.GetUserVo;
import wolf.night.service.entity.vo.LoginVo;
import wolf.night.service.entity.vo.R;
import wolf.night.service.mapper.SysUserMapper;
import wolf.night.service.mapper.SysUserRoleMapper;
import wolf.night.service.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wolf.night.service.utils.AdminUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public R login(LoginVo loginVo, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (captcha == null || !captcha.equalsIgnoreCase(loginVo.getCode())) {
            return R.err().message("验证码输入错误，请重新输入");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
        if (userDetails == null || !new BCryptPasswordEncoder().matches(loginVo.getPassword(), userDetails.getPassword())) {
            return R.err().message("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()) {
            return R.err().message("账号被禁用，请联系管理员");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtTokenUtil.generatorToken(userDetails);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("tokenHead", tokenHead);
        data.put("11", SecurityContextHolder.getContext());
        return R.sub("登录成功", data);
    }

    @Override
    public SysUser getUserInfoByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public List<SysRole> getRolesByUserId(Integer id) {
        return userMapper.getRolesByUserId(id);
    }

    @Override
    public List<SysUser> getAllUserByCondition(GetUserVo getUserVo) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        List<SysUser> users = userMapper.getAllUserByCond(getUserVo, AdminUtils.getCurrentAdmin().getId());

        return users;
    }

    @Override
    public int updateUserRoles(Integer userId, Integer[] rids) {
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        Integer result = 0;
        if (rids != null && rids.length != 0) {
            result = userRoleMapper.updateUserRoles(userId, rids);
        }
        return result;
    }

    @Override
    public int updateUserInfo(SysUser user, Authentication authentication) {
        user.setId(AdminUtils.getCurrentAdmin().getId());
        int i = userMapper.updateUserInfo(user);
        if (i == 1) {
            SysUser user1 = userMapper.selectOne(new QueryWrapper<SysUser>().eq("id", AdminUtils.getCurrentAdmin().getId()));
            Admin admin = new Admin();
            BeanUtils.copyProperties(user1, admin);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            Admin currentAdmin = AdminUtils.getCurrentAdmin();
            System.out.println("=-----------------" + currentAdmin);
        }
        return i;
    }

    @Override
    public boolean modifiedPass(String oldpass, String pass) {
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().eq("id", AdminUtils.getCurrentAdmin().getId()));
        if (new BCryptPasswordEncoder().matches(oldpass, user.getPassword())) {
            userMapper.updatePass(new BCryptPasswordEncoder().encode(pass), AdminUtils.getCurrentAdmin().getId());
            return true;
        }
        return false;
    }
}
