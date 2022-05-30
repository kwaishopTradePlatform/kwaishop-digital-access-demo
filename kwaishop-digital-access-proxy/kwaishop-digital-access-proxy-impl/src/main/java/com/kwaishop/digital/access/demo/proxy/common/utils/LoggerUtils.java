package com.kwaishop.digital.access.demo.proxy.common.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kwaishop.digital.access.demo.domain.common.utils.KsStringUtils;

public class LoggerUtils {
    public static void logApiError(Logger logger, String appKey, String apiMethod, String url, Map<String, String> params, Throwable throwable) {
        StringBuilder logStr = new StringBuilder();
        logStr.append(formatDateTime(new Date()));
        logStr.append("*_*");
        logStr.append(appKey);
        logStr.append("*_*");
        logStr.append(apiMethod);
        logStr.append("*_*");
        logStr.append(url);

        try {
            logStr.append("*_*");
            logStr.append(WebUtils.buildQueryParams(params, "UTF-8"));
        } catch (IOException var8) {
        }

        logger.log(Level.WARNING, logStr.toString(), throwable);
    }

    private static String formatDateTime(Date date) {
        return KsStringUtils.formatDateTime(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    private LoggerUtils() {
    }
}