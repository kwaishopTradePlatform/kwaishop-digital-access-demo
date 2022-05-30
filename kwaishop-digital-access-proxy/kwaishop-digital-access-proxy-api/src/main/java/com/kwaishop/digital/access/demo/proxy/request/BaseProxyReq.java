package com.kwaishop.digital.access.demo.proxy.request;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-23
 */
@Data
public class BaseProxyReq<T> {
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";

    private String apiName;
    private String httpMethod;
    private T param;

    public String buildApiUrl(String url) {
        return url + "/" + apiName.replaceAll("\\.", "/");
    }

    public static String getMethod() {
        return GET_METHOD;
    }

    public static String postMethod() {
        return POST_METHOD;
    }
}
