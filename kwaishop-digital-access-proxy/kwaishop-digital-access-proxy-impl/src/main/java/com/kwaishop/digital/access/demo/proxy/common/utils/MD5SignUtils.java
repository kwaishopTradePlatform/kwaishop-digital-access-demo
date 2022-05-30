package com.kwaishop.digital.access.demo.proxy.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author zhangyiying
 * Created on 2022-05-16
 */
public class MD5SignUtils {
    public MD5SignUtils() {
    }

    public static String sign(String input) {
        return DigestUtils.md5Hex(input);
    }
}
