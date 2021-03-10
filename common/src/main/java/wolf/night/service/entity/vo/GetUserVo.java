package wolf.night.service.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class GetUserVo extends Page implements Serializable {
    private String nickName;
    private String address;
    private String phone;
    private String sex;
    private String status;
    private Date startTime;
    private Date endTime;


}
