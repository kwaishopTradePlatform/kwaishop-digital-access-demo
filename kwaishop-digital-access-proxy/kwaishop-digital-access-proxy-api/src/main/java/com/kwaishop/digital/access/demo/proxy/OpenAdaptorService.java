package com.kwaishop.digital.access.demo.proxy;

import com.kwaishop.digital.access.demo.proxy.request.BaseProxyReq;
import com.kwaishop.digital.access.demo.proxy.response.BaseProxyResp;

/**
 * @author zhangkwei
 * Created on 2022-05-23
 */
public interface OpenAdaptorService {
    BaseProxyResp execute(BaseProxyReq baseProxyReq);
}
