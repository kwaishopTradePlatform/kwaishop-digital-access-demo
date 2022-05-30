package com.kwaishop.digital.access.demo.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-12
 */
@Data
public class ServiceResponse<T> {
    /**
     * 结果码
     */
    private int result;
    /**
     * 错误描述
     */
    private String errorMsg;
    /**
     * 数据
     */
    private T data;

    public ServiceResponse(int result, String errorMsg, T data) {
        this.result = result;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static <T> ServiceResponse<T> ok(T result) {
        return new ServiceResponse<T>(ResultCode.SUCCESS.getCode(), null, result);
    }

    public static <T> ServiceResponse<T> error(int code, String errorMessage) {
        return new ServiceResponse<T>(code, errorMessage, null);
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
