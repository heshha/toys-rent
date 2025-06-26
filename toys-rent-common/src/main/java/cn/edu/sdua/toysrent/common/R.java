package cn.edu.sdua.toysrent.common;

import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class R<T> {

    @Schema(description = "状态码")
    private Integer  code;

    @Schema(description = "返回信息")
    private String msg;

    @Schema(description = "返回数据")
    private T data;

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMsg("ok");
        r.setData(data);
        return r;
    }

    public static R ok(){
        R r = new R<>();
        r.setCode(200);
        r.setMsg("ok");
        return r;
    }

    public static  R error() {
        R r = new R<>();
        r.setCode(500);
        r.setMsg("error");
        return r;
    }

    public static R error(ExceptionCodeEnum exceptionCodeEnum){
        R r = new R<>();
        r.setCode(exceptionCodeEnum.getCode());
        r.setMsg(exceptionCodeEnum.getMsg());
        return r;
    }

    public static R error(ExceptionCodeEnum exceptionCodeEnum, Object data){
        R r = new R<>();
        r.setCode(exceptionCodeEnum.getCode());
        r.setMsg(exceptionCodeEnum.getMsg());
        r.setData(data);
        return r;
    }
    public static  R error(Integer code, String msg) {
        R r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static  R error(Integer code,  String msg, Object data) {
        R r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
