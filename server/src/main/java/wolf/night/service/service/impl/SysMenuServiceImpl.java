package wolf.night.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import wolf.night.service.entity.Admin;
import wolf.night.service.entity.SysMenu;
import wolf.night.service.entity.SysUser;
import wolf.night.service.mapper.SysMenuMapper;
import wolf.night.service.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<SysMenu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    @Override
    public List<SysMenu> getMenusByUserId() {
        Admin principal = (Admin) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = principal.getId();
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        List<SysMenu> menus = (List<SysMenu>) stringObjectValueOperations.get("menu_" + id);
        if (CollectionUtils.isEmpty(menus)) {
            menus = menuMapper.getMenusByUserId(id);
            stringObjectValueOperations.set("menu_" + id, menus);
        }
        return menus;

    }

    @Override
    public List<String> getAllPermissionValue() {
        return menuMapper.getAllPermissionValue();
    }

    @Override
    public List<String> getPermissionValueByUserId(Integer userId) {
        return menuMapper.getPermissionValueByUserId(userId);
    }
}
