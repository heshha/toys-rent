package cn.edu.sdua.toysrent.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    private Integer code;

    private String msg;

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ExceptionCodeEnum enume) {
        super(enume.getMsg());
        this.code = enume.getCode();
        this.msg = enume.getMsg();
    }
}
