package com.kwaishop.digital.access.demo.domain.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.kwaishop.digital.access.demo.domain.common.error.DigitalErrorCode;
import com.kwaishop.digital.access.demo.domain.common.error.ServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Slf4j
public class PlatformEventSecurityUtil {
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";
    private static final String CHARSET = "UTF-8";

    public static String decode(String message, String privateKey) {
        try {
            if (KsStringUtils.isBlank(message) || KsStringUtils.isBlank(privateKey)) {
                throw new ServiceException(DigitalErrorCode.LIST_ORDER_ERROR, "消息密文为空");
            }
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            byte[] key = Base64.decodeBase64(privateKey);
            SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new byte[16]));
            return new String(cipher.doFinal(Base64.decodeBase64(message)), CHARSET);
        } catch (Exception e) {
            log.error("decode message fail", e);
            throw new ServiceException(DigitalErrorCode.LIST_ORDER_ERROR);
        }
    }
}
