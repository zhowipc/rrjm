package wolf.night.service.service;

import wolf.night.service.entity.SysRole;
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
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getRolesByUserId(Integer id);
}
