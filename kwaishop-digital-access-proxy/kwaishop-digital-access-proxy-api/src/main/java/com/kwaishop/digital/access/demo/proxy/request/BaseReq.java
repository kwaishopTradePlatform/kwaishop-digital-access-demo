package com.kwaishop.digital.access.demo.proxy.request;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-12
 */
@Data
public class BaseReq<T> {
    private String url;

    private String method;

    private Integer version;

    private String accessToken;

    private String appKey;

    private String signMethod;

    private String sign;

    private Long timestamp;

    private T param;
}
