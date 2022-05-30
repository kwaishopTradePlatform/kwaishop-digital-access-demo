package com.kwaishop.digital.access.demo.proxy.common.utils;

import java.util.Collection;
import java.util.List;

import com.kwaishop.digital.access.demo.domain.common.utils.KsStringUtils;
import com.kwaishop.digital.access.demo.proxy.client.oauth.KsMerchantApiException;

public class RequestCheckUtils {
    public static void checkNotNull(Object value, String fieldName) throws KsMerchantApiException {
        if (value == null) {
            throw new KsMerchantApiException(12, "Missing required arguments:" + fieldName + "");
        }
    }

    public static void checkNotBlank(Object value, String fieldName) throws KsMerchantApiException {
        if (value == null || value instanceof String && KsStringUtils.isBlank((String)value)) {
            throw new KsMerchantApiException(12, "Missing required arguments:" + fieldName + "");
        }
    }

    public static void checkNotEmpty(Object value, String fieldName) throws KsMerchantApiException {
        if (value == null) {
            throw new KsMerchantApiException(12, "Missing required arguments:" + fieldName + "");
        } else if (value instanceof String && KsStringUtils.isBlank((String)value)) {
            throw new KsMerchantApiException(12, "Missing required arguments:" + fieldName + "");
        }
    }

    public static void checkNotEmptyCollection(Collection value, String fieldName) throws KsMerchantApiException {
        if (value == null) {
            throw new KsMerchantApiException(12, "Missing required arguments:" + fieldName + "");
        } else if (value.size() == 0) {
            throw new KsMerchantApiException(12, "not empty arguments:" + fieldName + "");
        }
    }

    public static void checkMaxLength(String value, int maxLength, String fieldName) throws KsMerchantApiException {
        if (value != null && value.length() > maxLength) {
            throw new KsMerchantApiException(13, "Invalid arguments:string length of " + fieldName + " can not be larger than " + maxLength + ".");
        }
    }

    public static void checkMaxListSize(String value, int maxSize, String fieldName) throws KsMerchantApiException {
        if (value != null) {
            String[] list = value.split(",");
            if (list.length > maxSize) {
                throw new KsMerchantApiException(13, "Invalid arguments:the array size of " + fieldName + " must be less than " + maxSize + ".");
            }
        }

    }

    public static void checkMaxListSize(List<String> list, int maxSize, String fieldName) throws KsMerchantApiException {
        if (list != null && list.size() > maxSize) {
            throw new KsMerchantApiException(13, "Invalid arguments:the array size of " + fieldName + " must be less than " + maxSize + ".");
        }
    }

    public static void checkMaxValue(Long value, long maxValue, String fieldName) throws KsMerchantApiException {
        if (value != null && value > maxValue) {
            throw new KsMerchantApiException(13, "Invalid arguments:the value of " + fieldName + " can not be larger than " + maxValue + ".");
        }
    }

    public static void checkMinValue(Long value, long minValue, String fieldName) throws KsMerchantApiException {
        if (value != null && value < minValue) {
            throw new KsMerchantApiException(13, "Invalid arguments:the value of " + fieldName + " can not be less than " + minValue + ".");
        }
    }

    private RequestCheckUtils() {
    }
}