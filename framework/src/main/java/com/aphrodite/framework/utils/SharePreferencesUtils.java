package com.aphrodite.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * Created by Aphrodite on 2017/11/30.
 */

public class SharePreferencesUtils {
    /**
     * Application Context，否则可能出现内存泄漏
     */
    private static Context mContext;

    private static String mSharePrefPame;

    private static final int SHARE_PREF_DEFAULT_VALUE = -1;

    public static void init(Context context, String sharePrefPame) {
        mContext = context;
        mSharePrefPame = sharePrefPame;
    }

    /**
     * 保存String参数
     *
     * @param key   key
     * @param value value
     */
    public static void saveString(String key, String value) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取各项String配置参数
     *
     * @param key key
     * @return value by key
     */
    public static String getStringByKey(String key) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return "";
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    /**
     * 获取各项String配置参数,自定义默认值
     *
     * @param key          key
     * @param defaultValue default value
     * @return value by key
     */
    public static String getStringByKey(String key, String defaultValue) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return defaultValue;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * 保存int参数
     *
     * @param key   key
     * @param value value
     */
    public static void saveInt(String key, int value) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取各项int配置参数
     *
     * @param key key
     * @return value by key
     */
    public static int getIntByKey(String key) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return SHARE_PREF_DEFAULT_VALUE;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, SHARE_PREF_DEFAULT_VALUE);
    }

    /**
     * 获取各项int配置参数,自定义默认值
     *
     * @param key          key
     * @param defaultValue default value
     * @return value by key
     */
    public static int getIntByKey(String key, int defaultValue) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return defaultValue;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 保存long类型参数
     *
     * @param key   key
     * @param value value
     */
    public static void saveLong(String key, long value) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取各项long类型配置参数
     *
     * @param key key
     * @return value by key
     */
    public static long getLongByKey(String key) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return SHARE_PREF_DEFAULT_VALUE;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, SHARE_PREF_DEFAULT_VALUE);
    }

    /**
     * 获取各项long类型配置参数,自定义默认值
     *
     * @param key          key
     * @param defaultValue default value
     * @return value by key
     */
    public static long getLongByKey(String key, long defaultValue) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return defaultValue;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * 保存boolean类型参数
     *
     * @param key   key
     * @param value value
     */
    public static void saveBoolean(String key, boolean value) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取boolean类型参数
     *
     * @param key key
     * @return value by key
     */
    public static boolean getBooleanByKey(String key) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return false;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * 获取boolean类型参数,自定义默认值
     *
     * @param key          key
     * @param defaultValue default value
     * @return value by key
     */
    public static boolean getBooleanByKey(String key, boolean defaultValue) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return defaultValue;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 根据key清空数据
     *
     * @param key key
     */
    public static void clearObjByKey(String key) {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清空SharedPreferences
     */
    public static void clear() {
        if (null == mContext || TextUtils.isEmpty(mSharePrefPame)) {
            return;
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mSharePrefPame, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
