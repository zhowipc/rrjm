package wolf.night.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import wolf.night.service.entity.SysUser;
import wolf.night.service.entity.vo.LoginVo;
import wolf.night.service.entity.vo.R;
import wolf.night.service.service.SysRoleService;
import wolf.night.service.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class LoginController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;

    @GetMapping("login")
    public R login(LoginVo loginVo, HttpServletRequest request) {
        return userService.login(loginVo, request);
    }

    @GetMapping("info")
    public R getInfo(Principal principal) {
        String username = principal.getName();
        SysUser userInfoByUsername = userService.getUserInfoByUsername(username);
        userInfoByUsername.setPassword(null);
        userInfoByUsername.setRoles(roleService.getRolesByUserId(userInfoByUsername.getId()));
        return R.ok().data(userInfoByUsername);
    }
    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
