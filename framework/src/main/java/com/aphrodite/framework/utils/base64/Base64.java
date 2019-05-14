package com.aphrodite.framework.utils.base64;

import android.text.TextUtils;

import com.aphrodite.framework.utils.base64.inter.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Aphrodite on 2019/5/13.
 */
public class Base64 {
    /**
     * base64编解器
     */
    private static final Encoder encoder = new Base64Encoder();

    /**
     * 加密
     *
     * @param data 明文
     * @return 密文
     */
    public static byte[] encode(byte[] data) {
        if (data == null) {
            return new byte[0];
        }
        int len = (data.length + 2) / 3 * 4;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);

        try {
            encoder.encode(data, 0, data.length, bOut);
        } catch (IOException e) {
            throw new RuntimeException("exception encoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    /**
     * 加密
     *
     * @param data 明文
     * @param out  输出流
     * @return 密文
     * @throws IOException 异常
     */
    public static int encode(byte[] data, OutputStream out) throws IOException {
        return encoder.encode(data, 0, data.length, out);
    }

    /**
     * 加密
     *
     * @param data   明文
     * @param off    偏移
     * @param length 长度
     * @param out    输出流
     * @return 密文
     * @throws IOException 异常
     */
    public static int encode(byte[] data, int off, int length, OutputStream out)
            throws IOException {
        return encoder.encode(data, off, length, out);
    }

    /**
     * 解密
     *
     * @param data 密文
     * @return 明文
     */
    public static byte[] decode(byte[] data) {
        int len = data.length / 4 * 3;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
        try {
            encoder.decode(data, 0, data.length, bOut);
        } catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    /**
     * 解密
     *
     * @param data 密文
     * @return 明文
     */
    public static byte[] decode(String data) {
        if (TextUtils.isEmpty(data)) {
            return new byte[0];
        }

        int len = data.length() / 4 * 3;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
        try {
            encoder.decode(data, bOut);
        } catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }
        return bOut.toByteArray();
    }

    /**
     * 解密
     *
     * @param data 密文
     * @param out  输出流
     * @return 明文
     * @throws IOException 异常
     */
    public static int decode(String data, OutputStream out) throws IOException {
        return encoder.decode(data, out);
    }
}
