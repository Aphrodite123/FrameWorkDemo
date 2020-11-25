package com.aphrodite.framework.utils.base64;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zhangjingming on 2017/5/12.
 */

public class HMACSHA1 {
    private static final String ALG_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    /**
     * hmac_sha_1加密
     *
     * @param source 源文本
     * @param key    秘钥文本
     * @return 加密后文本
     */
    public static String encrypt(String source, String key) {
        if (TextUtils.isEmpty(source) || TextUtils.isEmpty(key))
            return "";

        try {
            byte[] data = key.getBytes(ENCODING);
            SecretKey secretKey = new SecretKeySpec(data, ALG_NAME);
            Mac mac = Mac.getInstance(ALG_NAME);
            mac.init(secretKey);
            byte[] text = mac.doFinal(source.getBytes(ENCODING));
            String hex = bytesToHexString(text);
            return new String(Base64.encode(hex.getBytes(ENCODING)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
