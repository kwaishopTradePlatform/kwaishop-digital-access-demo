package com.kwaishop.digital.access.demo.proxy;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kwaishop.digital.access.demo.proxy.common.enums.SignMethodEnum;
import com.kwaishop.digital.access.demo.proxy.common.utils.AccessTokenUtils;
import com.kwaishop.digital.access.demo.proxy.common.utils.HttpClientUtils;
import com.kwaishop.digital.access.demo.proxy.common.utils.JsonUtils;
import com.kwaishop.digital.access.demo.proxy.common.utils.SignUtils;
import com.kwaishop.digital.access.demo.proxy.common.utils.WebUtils;
import com.kwaishop.digital.access.demo.proxy.request.BaseProxyReq;
import com.kwaishop.digital.access.demo.proxy.response.BaseProxyResp;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangkwei
 * Created on 2022-05-23
 */
@Service
@Slf4j
public class OpenAdaptorServiceImpl implements OpenAdaptorService {

    @Value("${kwaishop.url}")
    private String url;
    @Value("${kwaishop.appKey}")
    private String appKey;
    @Value("${kwaishop.version}")
    private String version;
    @Value("${kwaishop.signMethod}")
    private String signMethod;
    @Value("${kwaishop.signSecret}")
    private String signSecret;


    @Override
    public BaseProxyResp execute(BaseProxyReq baseProxyReq) {
        try {
            if (BaseProxyReq.getMethod().equals(baseProxyReq.getHttpMethod())) {
                Map<String, String> params = getParams(baseProxyReq);
                String responseStr = HttpClientUtils.getRequest(baseProxyReq.buildApiUrl(url), new HashMap<>(), params);

                JsonNode result = JsonUtils.toJSONNode(responseStr).get("result");
                if (Integer.parseInt(String.valueOf(result)) == 1) {
                    return BaseProxyResp.ofSuccess(responseStr);
                }
                return BaseProxyResp.ofFail(responseStr);
            } else if (BaseProxyReq.postMethod().equals(baseProxyReq.getHttpMethod())) {
                RequestConfig requestConfig = RequestConfig.custom().build();
                Map<String, String> params = getParams(baseProxyReq);
                String serverUrl = baseProxyReq.buildApiUrl(url) + "?" + WebUtils.buildQueryParams(params, "UTF-8");
                String responseStr = HttpClientUtils.postJsonInPool(serverUrl,
                        new HashMap<>(), params, requestConfig);
                JsonNode result = JsonUtils.toJSONNode(responseStr).get("result");
                if (Integer.parseInt(String.valueOf(result)) == 1) {
                    return BaseProxyResp.ofSuccess(responseStr);
                }
                return BaseProxyResp.ofFail(responseStr);
            }

            return BaseProxyResp.ofFail("传入http方法错误");
        } catch (Exception e) {
            log.error("OpenAdaptorServiceImpl execute error. param:{}", JsonUtils.toJSONString(baseProxyReq.getParam()), e);
            return BaseProxyResp.ofFail("http 调用失败");
        }
    }

    private Map<String, String> getParams(BaseProxyReq baseProxyReq) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", AccessTokenUtils.accessToken);
        params.put("method", baseProxyReq.getApiName());
        params.put("param", JsonUtils.toJSONString(baseProxyReq.getParam()));
        params.put("appkey", appKey);
        params.put("version", version);
        params.put("signMethod", signMethod);
        params.put("timestamp", System.currentTimeMillis() + "");

        SignMethodEnum signMethodEnum = SignMethodEnum.from(signMethod);
        String sign = SignUtils.sign(params, signSecret, signMethodEnum);
        params.put("sign", sign);
        return params;
    }


}
