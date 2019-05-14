package com.aphrodite.framework.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

/**
 * Description:路径工具集
 * Name:         PathUtils
 * Author:       zhangjingming
 * Date:         2016-07-05
 */

public class PathUtils {
    /**
     * 外置存储是否可用
     *
     * @return
     */
    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取外置存储卡路径
     *
     * @param context
     * @return
     */
    public static String getExternalStorageDir(Context context) {
        if (null == context || null == context.getExternalCacheDir())
            return "";

        return context.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 获取内置存储卡路径
     *
     * @param context
     * @return
     */
    public static String getInternalStorageDir(Context context) {
        if (null == context || null == context.getFilesDir())
            return "";

        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取app包存储路径
     *
     * @param context
     * @return
     */
    public static String getAppStorageDir(Context context) {
        if (isExternalStorageAvailable())
            return getExternalStorageDir(context);
        else
            return getInternalStorageDir(context);
    }

    /**
     * 从url中提取文件名，取最后一个后缀名
     *
     * @param url url
     * @return
     */
    public static String getFileNameFromUrl(String url) {
        if (TextUtils.isEmpty(url))
            return "";

        int last = url.lastIndexOf("/");
        return url.substring(last + 1);
    }

    public static String getExternalFileDir(Context context) {
        if (null == context || null == context.getExternalFilesDir(null))
            return "";

        return context.getExternalFilesDir(null).getAbsolutePath();
    }

}
