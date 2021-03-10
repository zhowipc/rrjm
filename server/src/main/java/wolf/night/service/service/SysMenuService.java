package wolf.night.service.service;

import wolf.night.service.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> getMenusWithRole();

    List<SysMenu> getMenusByUserId();

    List<String> getAllPermissionValue();

    List<String> getPermissionValueByUserId(Integer userId);
}
