package wolf.night.service.controller;


import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import wolf.night.service.entity.SysMenu;
import wolf.night.service.entity.vo.R;
import wolf.night.service.service.SysMenuService;

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
@RequestMapping("/system/cfg")
public class SysMenuController {
    @Autowired
    private SysMenuService menuService;

    @GetMapping("menu")
    public R Menu() {
        List<SysMenu> menus = menuService.getMenusByUserId();
        return R.ok().data(menus);
    }

    @GetMapping("perV")
    public R getAllPerV() {
        List<String> allPerv = menuService.getAllPermissionValue();

        return R.ok().data(allPerv);
    }

    @GetMapping("getVbyId")
    public R getPerVbyId(Integer userId) {
        List<String> perV = menuService.getPermissionValueByUserId(userId);
        return R.ok().data(perV);
    }
}

