package com.aphrodite.framework.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

/**
 * Description:对象工具集
 * Name:         ObjectUtils
 * Author:       zhangjingming
 * Date:         2016-07-05
 */
public class ObjectUtils {
    /**
     * 对象是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(Object s) {
        if (s == null) {
            return true;
        }
        if ((s instanceof String) && (((String) s).trim().length() == 0)) {
            return true;
        }
        if (s instanceof Map) {
            return ((Map<?, ?>) s).isEmpty();
        }
        if (s instanceof Collection) {
            return ((Collection<?>) s).isEmpty();
        }
        if (s instanceof Object[]) {
            return (((Object[]) s).length == 0);
        }
        return false;
    }

    /**
     * 索引是否越界
     *
     * @param s 对象
     * @param i 索引
     * @return
     */
    public static boolean isOutOfBounds(Object s, int i) {
        if (s == null || i < 0) {
            return true;
        }
        if (s instanceof Map) {
            return i >= ((Map<?, ?>) s).size();
        }
        if (s instanceof Collection) {
            return i >= ((Collection<?>) s).size();
        }
        if (s instanceof Object[]) {
            return i >= ((Object[]) s).length;
        }
        return false;
    }

    public static String urlFormat(String key, String value) {
        return key + "=" + value;
    }

    public static String urlFormat(String key, int value) {
        return key + "=" + value;
    }

    public static String urlFormat(String key, boolean value) {
        return key + "=" + value;
    }

    public static String urlFormat(String key, float value) {
        return key + "=" + value;
    }

    public static String urlFormat(String key, long value) {
        return key + "=" + value;
    }

    public static String urlFormat(String key, double value) {
        return key + "=" + value;
    }

    public static String urlFormat(String key, char value) {
        return key + "=" + value;
    }

    public static String urlEncodeFormat(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value))
            return "";

        try {
            return key + "=" + URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 写对象到本地文件，默认保存到data/data/#package/files/目录
     *
     * @param context  上下文
     * @param object   对象
     * @param filename 保存的文件名
     */
    public static void write(Context context, Object object, String filename) {
        if (null == context || null == object || TextUtils.isEmpty(filename))
            return;

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件中读取对象，默认从data/data/#package/files/目录获取文件
     *
     * @param context  上下文
     * @param filename 文件名
     * @return 对象
     */
    public static Object read(Context context, String filename) {
        if (null == context || TextUtils.isEmpty(filename))
            return null;

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            fis = context.openFileInput(filename);
            ois = new ObjectInputStream(fis);
            obj = ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return obj;
        }
    }
}
