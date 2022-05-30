package com.kwaishop.digital.access.demo.proxy.common.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-12
 */
//@Slf4j
public class HttpClientUtils {
    public static final int CR_TIME_OUT = 5000;
    public static final int CT_TIME_OUT = 3000;
    public static final int ST_TIME_OUT = 5000;

    private static final String PERF_TAG = "thirdHttpClient";

    private static final String HTTP_REQUEST_REQ = "req";
    private static final String HTTP_REQUEST_SUCCESS = "success";
    private static final String HTTP_REQUEST_FAIL = "fail";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getRequest(String requestUrl, Map<String, String> headers, Map<String, String> params) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CR_TIME_OUT)
                .setConnectTimeout(CT_TIME_OUT)
                .setSocketTimeout(ST_TIME_OUT)
                .build();
        return getRequest(requestUrl, headers, params, requestConfig);
    }

    public static String getRequest(String requestUrl, Map<String, String> headers, Map<String, String> params,
            RequestConfig requestConfig) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = getRequestInternal(requestUrl, headers, params, requestConfig, httpClient);
        return response.getBody();
    }

    public static String getRequest(String requestUrl, Map<String, String> headers, Map<String, String> params,
            RequestConfig requestConfig, CloseableHttpClient httpClient) {
        HttpResponse response = getRequestInternal(requestUrl, headers, params, requestConfig, httpClient);
        return response.getBody();
    }

    public static HttpResponse getRequestInternal(String requestUrl, Map<String, String> headers, Map<String, String> params,
            RequestConfig requestConfig, CloseableHttpClient httpClient) {
        HttpGet httpGet = new HttpGet();
        if (requestConfig != null) {
            httpGet.setConfig(requestConfig);
        }

        String url = requestUrl;
        if (MapUtils.isNotEmpty(params)) {
            url = requestUrl + "?" + buildQueryString(params);
        }
        httpGet.setURI(URI.create(url));
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(httpGet::addHeader);
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK != statusCode) {
//                log.warn("get request [{}] fail and status code = {}", url, statusCode);
            }
            HttpResponse response = new HttpResponse();
            response.setHeaders(httpResponse.getAllHeaders());
            String jsonResult = EntityUtils.toString(httpResponse.getEntity());
            response.setBody(jsonResult);
//            log.info("get request = [{}], response = {}", url, jsonResult);
            return response;
        } catch (Exception e) {
//            log.error("occur error on send get request to = {}", url, e);
        } finally {
            closeQuietly(httpResponse);
        }

        return (HttpResponse) httpResponse;
    }

    public static String buildQueryString(Map<String, String> params) {
        if (MapUtils.isEmpty(params)) {
            return StringUtils.EMPTY;
        }
        List<String> paramList = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            //ignore null value
            if (entry.getValue() == null) {
                continue;
            }
            try {
                paramList.add(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()));
            } catch (UnsupportedEncodingException e) {
//                log.error("buildQueryString encode failed", e);
                throw new RuntimeException(e);
            }
        }

        return Joiner.on("&").join(paramList);
    }

    public static <T> String postJson(String requestUrl, T body) {
        return postJson(requestUrl, Collections.emptyMap(), body);
    }

    public static <T> String postJson(String requestUrl, Map<String, String> headers, T body) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CR_TIME_OUT)
                .setConnectTimeout(CT_TIME_OUT)
                .setSocketTimeout(ST_TIME_OUT)
                .build();
        return postJson(requestUrl, headers, body, requestConfig, true);
    }

    // todo： http请求的成功失败返回值要判断出来，
    public static <T> String postJsonInPool(String requestUrl, Map<String, String> headers, T body,
            RequestConfig requestConfig) {
        return postJson(requestUrl, headers, body, requestConfig, true);
    }

    public static <T> String postJson(String requestUrl, Map<String, String> headers, T body,
            RequestConfig requestConfig, boolean userConnPool) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(requestUrl), "request url cannot be blank");
        HttpPost httpPost = new HttpPost(requestUrl);
        if (requestConfig != null) {
            httpPost.setConfig(requestConfig);
        }
        httpPost.addHeader("Content-Type", "application/json");
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(httpPost::addHeader);
        }
        String jsonParam = null;
        try {
            jsonParam = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
//            log.error();
        }
        StringEntity stringEntity = new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            httpResponse = httpClient.execute(httpPost);
            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            String entity = EntityUtils.toString(httpResponse.getEntity());
            if (HttpStatus.SC_OK != statusCode) {
//                log.warn("post request[{}] status code = {}, entity = {}", requestUrl, statusCode, entity);
            }
            return entity;
        } catch (Exception e) {
//            log.error("occur error on post http request to = {}", requestUrl, e);
        } finally {
            if (httpResponse != null) {
                final HttpEntity entity = httpResponse.getEntity();
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
//                    log.warn("entity consume exception", e);
                }
                //非连接池直接关闭连接
                if (!userConnPool) {
                    closeQuietly(httpResponse);
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String param = "%7B%22beginTime%22%3A1650844800000%2C%22endTime%22%3A1650931200000%2C%22type%22%3A9%2C%22pageSize%22%3A10%2C%22currentPage%22%3A1%2C%22queryType%22%3A1%2C%22negotiateStatus%22%3A0%2C%22pcursor%22%3A%22+%22%7D";

        String appKey = "ks683702719562282620";
        String appSecret = "zSCiWmGOk_diMCU9k3zHcg";
        String apiMethodName = "open.seller.order.refund.pcursor.list";
        String accessToken = "ChFvYXV0aC5hY2Nlc3NUb2tlbhJQR0DhKq37uZEXa5vVPsltqIMkm9CAY89zVHxJNG8RJ6znH2oX56s3PY8o49BZIkA9JP9-WuWn6CvzXl64zeEhYbsXh-wCpjxyGi9LmWLMWTEaEu874KGvz723CPikM0lrNGlXMyIgaoY9PaVA3FZ9q_vNvX6kVlTDAP-_IZp-lEazzjM9XsgoDzAB";
        String signSecret = "7482bac6555ee7a3184bcc48f4ef05cd";

        String url = "https://gw-merchant-staging.test.gifshow.com/open/seller/order/refund/pcursor/list";

        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("method", apiMethodName);
        params.put("param", param);
        params.put("sign", "27b2ddec3ab555c1edaf9fd902948321");
        params.put("appkey", appKey);
        params.put("version", "1");
        params.put("signMethod", "MD5");
        params.put("timestamp", "1652691410482");
        String response = getRequest(url, new HashMap<>(), params);
        System.out.println(response);

    }

    private static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    @Data
    public static class HttpResponse {
        /**
         * 响应头
         */
        private Header[] headers;

        /**
         * 响应数据
         */
        private String body;

        public void setHeaders(Header[] allHeaders) {
        }
    }
}
