package wolf.night.service.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class R implements Serializable {
    private Integer code;
    private String message;
    private Object obj;

    private R() {
    }

    public static R sub(String message, Object obj) {
        R r = new R();
        r.code(20000);
        r.message = message;
        r.obj = obj;
        return r;
    }

    public static R sub(String message) {
        R r = new R();
        r.code(20000);
        r.message = message;
        return r;
    }

    public static R ok() {
        R r = new R();
        r.code(20000);
        r.setMessage("操作成功");
        return r;
    }

    public static R err() {
        R r = new R();
        r.setCode(20001);
        r.setMessage("操作失败");
        return r;

    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }


    public R data(Object obj) {
        this.obj = obj;
        return this;
    }


}
