package wolf.night.service.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import wolf.night.service.entity.Admin;

public class AdminUtils {
    public static Admin getCurrentAdmin() {
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
