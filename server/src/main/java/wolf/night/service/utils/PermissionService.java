package wolf.night.service.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("wol")
public class PermissionService {
    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        System.out.println("123");
        return true;
    }
}
