package wolf.night.service.mapper;

import wolf.night.service.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> getMenusWithRole();

    List<SysMenu> getMenusByUserId(Integer id);

    List<String> getAllPermissionValue();

    List<String> getPermissionValueByUserId(Integer id);
}
