package com.kwaishop.digital.access.demo.proxy.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.kwaishop.digital.access.demo.domain.common.utils.KsStringUtils;
import com.kwaishop.digital.access.demo.proxy.client.oauth.KsMerchantApiException;
import com.kwaishop.digital.access.demo.proxy.common.enums.SignMethodEnum;
import com.kwaishop.digital.access.demo.proxy.request.BaseReq;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-16
 */
@Slf4j
public class SignUtils {
    public SignUtils() {
    }

    public static Map<String, String> sign(BaseReq baseReq, String signSecret) {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", AccessTokenUtils.accessToken);
        params.put("method", baseReq.getMethod());
        params.put("param", JsonUtils.toJSONString(baseReq.getParam()));
        params.put("appkey", baseReq.getAppKey());
        params.put("version", baseReq.getVersion().toString());
        params.put("signMethod", baseReq.getSignMethod());
        params.put("timestamp", baseReq.getTimestamp().toString());

        SignMethodEnum signMethodEnum = SignMethodEnum.from(baseReq.getSignMethod());
        String sign = SignUtils.sign(params, signSecret, signMethodEnum);

        params.put("sign", sign);

        return params;
    }

    public static String sign(String param, String signSecret, SignMethodEnum signMethod) {
        StringBuilder sb = new StringBuilder();
        sb.append(param).append("&").append("signSecret").append("=").append(signSecret);
        String inputStr = sb.toString();
        switch(signMethod) {
            case MD5:
                return MD5SignUtils.sign(inputStr);
            case HMAC_SHA256:
                return HMACSHA256SignUtils.sign(inputStr, signSecret);
            default:
                return inputStr;
        }
    }

    public static String sign(Map<String, String> requestParamMap, String signSecret, SignMethodEnum signMethod) {
        return sign(getSignParam(requestParamMap), signSecret, signMethod);
    }

    public static boolean isSignMatch(String sign, String param, String signSecret, SignMethodEnum signMethod) {
        String newSign = sign(param, signSecret, signMethod);
        return KsStringUtils.equals(sign, newSign);
    }

    public static boolean isSignMatch(String sign, Map<String, String> requestParamMap, String signSecret,
            SignMethodEnum signMethod) {
        String newSign = sign(getSignParam(requestParamMap), signSecret, signMethod);
        return KsStringUtils.equals(sign, newSign);
    }

    public static String getSignParam(Map<String, String> requestParamMap) {
        String method = checkAndGetParam(requestParamMap, "method");
        String appKey = checkAndGetParam(requestParamMap, "appkey");
        String accessToken = checkAndGetParam(requestParamMap, "access_token");
        String version = (String) requestParamMap.get("version");
        String signMethod = (String) requestParamMap.get("signMethod");
        String timestamp = (String) requestParamMap.get("timestamp");
        String param = (String) requestParamMap.get("param");
        Map<String, String> signMap = new HashMap();
        signMap.put("method", method);
        signMap.put("appkey", appKey);
        signMap.put("access_token", accessToken);
        if (signMethod != null) {
            signMap.put("signMethod", signMethod);
        }

        if (version != null) {
            signMap.put("version", version);
        }

        if (timestamp != null) {
            signMap.put("timestamp", timestamp);
        }

        if (param != null) {
            signMap.put("param", param);
        }

        String signParam = SortUtils.sortAndJoin(signMap);
        return signParam;
    }

    public static String checkAndGetParam(Map<String, String> paramMap, String paramKey) {
        String value = (String) paramMap.get(paramKey);
        if (KsStringUtils.isBlank(value)) {
            throw new IllegalArgumentException(paramKey + " not exist");
        } else {
            return value;
        }
    }
}
