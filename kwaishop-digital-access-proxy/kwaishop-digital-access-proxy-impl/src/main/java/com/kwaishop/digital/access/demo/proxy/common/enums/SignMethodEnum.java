package com.kwaishop.digital.access.demo.proxy.common.enums;

/**
 * @author zhangyiying
 * Created on 2022-05-16
 */

public enum SignMethodEnum {
    MD5,
    HMAC_SHA256;

    private SignMethodEnum() {
    }

    public static SignMethodEnum from(String name) {
        SignMethodEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            SignMethodEnum typeEnum = var1[var3];
            if (typeEnum.name().equals(name)) {
                return typeEnum;
            }
        }

        return MD5;
    }
}

