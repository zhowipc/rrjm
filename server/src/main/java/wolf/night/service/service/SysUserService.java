package wolf.night.service.service;

import org.springframework.security.core.Authentication;
import wolf.night.service.entity.SysRole;
import wolf.night.service.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import wolf.night.service.entity.vo.GetUserVo;
import wolf.night.service.entity.vo.LoginVo;
import wolf.night.service.entity.vo.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
public interface SysUserService extends IService<SysUser> {

    R login(LoginVo loginVo, HttpServletRequest request);

    SysUser getUserInfoByUsername(String username);

    List<SysRole> getRolesByUserId(Integer id);

    List<SysUser> getAllUserByCondition(GetUserVo getUserVo);

    int updateUserRoles(Integer userId, Integer[] rids);

    int updateUserInfo(SysUser user, Authentication authentication);

    boolean modifiedPass(String oldpass, String pass);
}
