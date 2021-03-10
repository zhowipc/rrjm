package wolf.night.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import wolf.night.service.entity.SysRole;
import wolf.night.service.entity.SysUserRole;
import wolf.night.service.mapper.SysRoleMapper;
import wolf.night.service.mapper.SysUserRoleMapper;
import wolf.night.service.service.SysRoleService;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public List<SysRole> getRolesByUserId(Integer id) {
        return roleMapper.getRolesByUserId(id);
    }
}
