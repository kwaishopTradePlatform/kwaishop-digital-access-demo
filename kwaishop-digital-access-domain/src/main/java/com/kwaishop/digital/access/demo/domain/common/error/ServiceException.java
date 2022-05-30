package com.kwaishop.digital.access.demo.domain.common.error;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     *
     * @see DigitalErrorCode
     */
    private Long code;

    /**
     * 错误信息
     */
    private String info;

    public ServiceException(DigitalErrorCode errorCode) {
        this.code = errorCode.getErrorCode();
        this.info = errorCode.getErrorInfo();
    }

    public ServiceException(DigitalErrorCode errorCode, String message) {
        this.code = errorCode.getErrorCode();
        this.info = message;
    }

    public Long getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
