package com.kwaishop.digital.access.demo.proxy.common.utils;

import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class HMACSHA256SignUtils {
    protected static final
    Logger logger = Logger.getLogger(HMACSHA256SignUtils.class.getName());

    public HMACSHA256SignUtils() {
    }

    public static String sign(String params, String secret) {
        String result = "";

        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            byte[] sha256HMACBytes = sha256HMAC.doFinal(params.getBytes());
            String hash = Base64.encodeBase64String(sha256HMACBytes);
            return hash;
        } catch (Exception var7) {
            logger.warning("HMACSHA256SignUtils sign failed, params=" + params + ", error=" + var7.getMessage());
            return result;
        }
    }
}