package wolf.night.service.mapper;

import org.apache.ibatis.annotations.Param;
import wolf.night.service.entity.SysRole;
import wolf.night.service.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import wolf.night.service.entity.vo.GetUserVo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserInfoByUsername(String username);

    List<SysRole> getRolesByUserId(Integer id);

    List<SysUser> getAllUserByCond(GetUserVo uservo, Integer id);

    int updateUserInfo(SysUser user);

    int updatePass(@Param("pass") String encode,@Param("id") Integer id);

}
