package cn.edu.sdua.toysrent.exception;

import lombok.Getter;

public enum ExceptionCodeEnum {

    USER_NOT_FOUND(10001, "用户不存在"),
    USER_ALREADY_EXISTS(10002, "用户名已存在"),
    TOY_NOT_FOUND(20001, "玩具不存在"),
    TOY_ALREADY_EXISTS(20002, "玩具已存在"),
    TOY_OUT_OF_STOCK(20003, "玩具库存不足"),
    TOY_IMAGE_NOT_FOUND(20004, "玩具图片不存在"),
    ORDER_NOT_FOUND(30001, "订单不存在"),
    ORDER_CLOSED(30003, "订单已关闭"),
    PAYMENT_NOT_FOUND(30005, "支付信息不存在"),
    INSUFFICIENT_BALANCE(30006, "余额不足"),
    LOGIN_CAPTCHA_CODE_EXPIRED(40001, "验证码已过期"),
    LOGIN_CAPTCHA_CODE_ERROR(40002, "验证码错误"),
    LOGIN_USERNAME_PASSWORD_ERROR(40003, "用户名或密码错误"),
    LOGIN_AUTH(40004, "未登录"),
    LOGIN_TOKEN_EXPIRED(40005, "登录已过期"),
    VALIDATE_ERROR(50001, "校验不通过"),
    MESSAGE_NOT_READABLE(50002, "消息格式错误"),
    RESOURCE_NOT_FOUND(50003, "找不到资源，请查看接口文档"),
    DATA_NOT_FOUND(50004, "没有符合条件的数据"),
    FILE_UPLOAD_ERROR(50005, "文件上传失败"),
    ADMIN_REQUIRED(50006, "没有管理员权限"),

    ;
    @Getter
    private Integer  code;
    @Getter
    private String msg;

    private ExceptionCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
