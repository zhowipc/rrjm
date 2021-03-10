package wolf.night.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import wolf.night.service.entity.SysUser;
import wolf.night.service.entity.vo.GetUserVo;
import wolf.night.service.entity.vo.R;
import wolf.night.service.service.SysUserService;
import wolf.night.service.utils.AdminUtils;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@RestController
@RequestMapping("/system/cfg/user")
public class SysUserController {
    @Autowired
    private SysUserService userService;

    @PostMapping("/11")
    public R ss(@RequestBody String msg) {
        System.out.println(msg);
        return R.ok();
    }


    @GetMapping("/addUser")
    public R addUser(SysUser user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        boolean save = userService.save(user);
        if (save) {
            return R.ok();
        }
        return R.err();
    }

    @GetMapping
    public R getAllUserByCondition(GetUserVo getUserVo) {
        List<SysUser> users = userService.getAllUserByCondition(getUserVo);
        return R.ok().data(users);
    }

    @DeleteMapping
    public R delUser(@RequestBody SysUser user) {
        userService.removeById(user.getId());
        return R.ok();
    }

    @PostMapping("/{userId}/{rids}")
    public R updateUserRoles(@PathVariable("userId") Integer userId, @PathVariable("rids") Integer[] rids) {

        int i = userService.updateUserRoles(userId, rids);
        if (rids != null && i == rids.length) {
            return R.ok();
        }
        return R.err();
    }

    @GetMapping("/{username}")
    public R checkUsername(@PathVariable("username") String username) {
        if (userService.getOne(new QueryWrapper<SysUser>().eq("username", username)) == null) {
            return R.ok();
        } else {
            return R.err();
        }
    }

    @PutMapping
    public R updateInfo(@RequestBody SysUser user, Authentication authentication) {
        int i = userService.updateUserInfo(user,authentication);

        return R.ok().data(user);
    }

    @PostMapping("/modifiedPass")
    public R modifiedPass(@RequestBody String oldpass, @RequestBody String pass) {
        boolean flag = userService.modifiedPass(oldpass, pass);
        return flag ? R.ok().message("修改成功") : R.err().message("旧密码输入错误");
    }
}

