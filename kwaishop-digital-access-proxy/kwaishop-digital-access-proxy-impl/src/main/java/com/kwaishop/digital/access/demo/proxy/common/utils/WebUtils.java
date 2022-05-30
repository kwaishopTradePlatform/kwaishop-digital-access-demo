package com.kwaishop.digital.access.demo.proxy.common.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.kwaishop.digital.access.demo.domain.common.utils.KsStringUtils;

public class WebUtils {
    public static URL buildGetUrl(String url, String query) throws IOException {
        return KsStringUtils.isEmpty(query) ? new URL(url) : new URL(buildRequestUrl(url, query));
    }

    public static String buildQueryParams(Map<String, String> params, String charset) throws IOException {
        if (params != null && !params.isEmpty()) {
            StringBuilder queryParams = new StringBuilder();
            Set<Entry<String, String>> entrySet = params.entrySet();
            boolean hasParam = false;
            Iterator var5 = entrySet.iterator();

            while(var5.hasNext()) {
                Entry<String, String> entry = (Entry)var5.next();
                String name = (String)entry.getKey();
                String value = (String)entry.getValue();
                if (!KsStringUtils.isBlank(name) && !KsStringUtils.isBlank(value)) {
                    if (hasParam) {
                        queryParams.append("&");
                    } else {
                        hasParam = true;
                    }

                    queryParams.append(name).append("=").append(URLEncoder.encode(value, charset));
                }
            }

            return queryParams.toString();
        } else {
            return null;
        }
    }

    public static String buildRequestUrl(String url, String... requestParams) {
        if (requestParams != null && requestParams.length != 0) {
            StringBuilder requestUrl = new StringBuilder(url);
            boolean hasQuery = url.contains("?");
            boolean hasPrepend = url.endsWith("?") || url.endsWith("&");
            String[] var5 = requestParams;
            int var6 = requestParams.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String indexQuery = var5[var7];
                if (!KsStringUtils.isBlank(indexQuery)) {
                    if (!hasPrepend) {
                        if (hasQuery) {
                            requestUrl.append("&");
                        } else {
                            requestUrl.append("?");
                            hasQuery = true;
                        }
                    }

                    requestUrl.append(indexQuery);
                    hasPrepend = false;
                }
            }

            return requestUrl.toString();
        } else {
            return url;
        }
    }

    private WebUtils() {
    }
}