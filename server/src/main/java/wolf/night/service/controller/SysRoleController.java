package wolf.night.service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import wolf.night.service.entity.SysRole;
import wolf.night.service.entity.vo.R;
import wolf.night.service.service.SysRoleService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@RestController
@RequestMapping("/system/cfg/role")
public class SysRoleController {
    @Autowired
    private SysRoleService roleService;

    @GetMapping
    public R getAllRoles() {
        List<SysRole> list = roleService.list(null);
        return R.ok().data(list);
    }


}

