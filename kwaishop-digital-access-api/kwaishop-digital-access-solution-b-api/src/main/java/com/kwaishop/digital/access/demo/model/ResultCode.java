package com.kwaishop.digital.access.demo.model;

/**
 * API 接口返回状态码
 * <p>
 * Write the code. Change the world.
 *
 * @author trang
 */
public enum ResultCode {

    SUCCESS(1, "成功"),

    /**
     * 300-399是客户端需要做些事
     */
    LOGIN_REQUIRED(302, "需要重新登录"),

    /**
     * 400-499是客户端错误
     */
    BAD_REQUEST(400, "非法请求"),
    UNAUTHORIZED(401, "未授权，请联系管理员"),
    FORBIDDEN(403, "该页面禁止访问"),
    NOT_FOUND(404, "页面未找到"),
    LOGIN_FAIL(410, "登录失败"),
    PARAM_INVALID(411, "参数校验失败"),

    /**
     * 500-599是服务端错误
     */
    SERVICE_BUSY(500, "内部系统繁忙"),
    ;

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
