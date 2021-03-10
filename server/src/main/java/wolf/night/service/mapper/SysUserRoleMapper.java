package wolf.night.service.mapper;

import org.apache.ibatis.annotations.Param;
import wolf.night.service.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    Integer updateUserRoles(@Param("userId") Integer userId, @Param("rids") Integer[] rids);
}
