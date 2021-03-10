package wolf.night.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import wolf.night.service.entity.vo.R;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtils {
    public static  void outErr(HttpServletResponse response) {
        out(response, 450, "没有权限");
    }
    public static void out(HttpServletResponse response, R r) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(r));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResponseUtils.out");
        }

    }
    public static void out(HttpServletResponse response, Integer code, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(R.err().code(code).message(message)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResponseUtils.out");
        }

    }
}
