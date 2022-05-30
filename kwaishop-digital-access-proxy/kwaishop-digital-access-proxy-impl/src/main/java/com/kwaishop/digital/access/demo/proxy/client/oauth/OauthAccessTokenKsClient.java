package com.kwaishop.digital.access.demo.proxy.client.oauth;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;

import com.kwaishop.digital.access.demo.proxy.common.utils.HttpClientUtils;
import com.kwaishop.digital.access.demo.proxy.common.utils.JsonUtils;
import com.kwaishop.digital.access.demo.proxy.common.utils.LoggerUtils;
import com.kwaishop.digital.access.demo.proxy.common.utils.RequestCheckUtils;
import com.kwaishop.digital.access.demo.proxy.response.KsAccessTokenResponse;

/**
 * @author pengjianfei
 * Created on 2022-05-18
 */
public class OauthAccessTokenKsClient {
    protected static final Logger logger = Logger.getLogger(OauthAccessTokenKsClient.class.getName());
    protected String serverRestUrl;
    protected String appKey;
    protected String appSecret;
    protected int connectTimeout;
    protected int readTimeout;
    private Proxy proxy;

    public OauthAccessTokenKsClient(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.connectTimeout = 15000;
        this.readTimeout = 30000;
    }

    public OauthAccessTokenKsClient(String appKey, String appSecret, int connectTimeout, int readTimeout) {
        this(appKey, appSecret);
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }


    public OauthAccessTokenKsClient(String appKey, String appSecret, String serverRestUrl) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.connectTimeout = 15000;
        this.readTimeout = 30000;
        this.serverRestUrl = serverRestUrl;
    }


    public KsAccessTokenResponse getAccessToken(String code) throws KsMerchantApiException {
        RequestCheckUtils.checkNotEmpty(this.appKey, "appKey");
        RequestCheckUtils.checkNotEmpty(this.appSecret, "appSecret");

        try {
            String response = HttpClientUtils.getRequest(this.serverRestUrl + "/oauth2/access_token", new HashMap<>(), this.buildOauthAccessTokenRequest(code));
            if (response == null) {
                throw new KsMerchantApiException("ks oauth access_token api responseBody parse fail");
            } else {
                return ((KsAccessTokenResponse) JsonUtils.toJavaObject(response, KsAccessTokenResponse.class));
            }
        } catch (Exception var4) {
            LoggerUtils.logApiError(logger, this.appKey, "ks.oauth.access.token", "https://open.kwaixiaodian.com/oauth2/access_token", (Map)null, var4);
            throw new KsMerchantApiException(var4);
        }
    }

    public KsAccessTokenResponse refreshAccessToken(String refreshToken) throws KsMerchantApiException {
        RequestCheckUtils.checkNotEmpty(this.appKey, "appKey");
        RequestCheckUtils.checkNotEmpty(this.appSecret, "appSecret");

        try {
            String response = HttpClientUtils.getRequest(this.serverRestUrl + "/oauth2/refresh_token", new HashMap<>(),this.buildOauthRefreshTokenRequest(refreshToken));

            if (response == null) {
                throw new KsMerchantApiException("ks oauth refresh_token api responseBody parse fail");
            } else {
                return ((KsAccessTokenResponse) JsonUtils.toJavaObject(response, KsAccessTokenResponse.class));
            }
        } catch (Exception var4) {
            LoggerUtils.logApiError(logger, this.appKey, "ks.oauth.refresh.token", "https://open.kwaixiaodian.com/oauth2/access_token", (Map)null, var4);
            throw new KsMerchantApiException(var4);
        }
    }

    private Map<String, String> buildOauthAccessTokenRequest(String code) {
        Map<String, String> requestParams = new HashMap();
        requestParams.put("app_id", this.getAppKey());
        requestParams.put("app_secret", this.getAppSecret());
        requestParams.put("grant_type", "code");
        requestParams.put("code", code);
        return requestParams;
    }

    private Map<String, String> buildOauthRefreshTokenRequest(String refreshToken) {
        Map<String, String> requestParams = new HashMap();
        requestParams.put("app_id", this.getAppKey());
        requestParams.put("app_secret", this.getAppSecret());
        requestParams.put("grant_type", "refresh_token");
        requestParams.put("refresh_token", refreshToken);
        return requestParams;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

}
