package com.aphrodite.framework.utils.base64.inter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Aphrodite on 2019/5/13.
 */
public interface Encoder {
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
    int encode(byte[] data, int off, int length, OutputStream out)
            throws IOException;

    /**
     * 解密
     *
     * @param data   密文
     * @param off    偏移
     * @param length 长度
     * @param out    输出流
     * @return 明文
     * @throws IOException 异常
     */
    int decode(byte[] data, int off, int length, OutputStream out)
            throws IOException;

    /**
     * 解密
     *
     * @param data 密文
     * @param out  输出流
     * @return 明文
     * @throws IOException 异常
     */
    int decode(String data, OutputStream out) throws IOException;
}
