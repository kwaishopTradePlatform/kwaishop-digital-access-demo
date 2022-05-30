package com.kwaishop.digital.access.demo.proxy.client.oauth;

/**
 * @author pengjianfei
 */
public class KsMerchantApiException extends Exception {
    private int errorCode;
    private String errorMsg;

    public KsMerchantApiException() {
    }

    public KsMerchantApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public KsMerchantApiException(String message) {
        super(message);
    }

    public KsMerchantApiException(Throwable cause) {
        super(cause);
    }

    public KsMerchantApiException(int errorCode, String errorMsg) {
        super(errorCode + ":" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
}
