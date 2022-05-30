package com.kwaishop.digital.access.demo.proxy.response;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-23
 */
@Data
public class BaseProxyResp {
    private static final Integer SUCCESS = 1;
    private static final Integer FAIL = -1;
    private Integer code;
    private String msg;
    private String data;

    public boolean isSuccess() {
        return SUCCESS.equals(code);
    }

    public static BaseProxyResp ofSuccess(String data) {
        BaseProxyResp baseProxyResp = new BaseProxyResp();
        baseProxyResp.setCode(SUCCESS);
        baseProxyResp.setMsg("");
        baseProxyResp.setData(data);
        return baseProxyResp;
    }

    public static BaseProxyResp ofFail(String msg) {
        BaseProxyResp baseProxyResp = new BaseProxyResp();
        baseProxyResp.setCode(FAIL);
        baseProxyResp.setMsg(msg);
        baseProxyResp.setData("");
        return baseProxyResp;
    }

}
