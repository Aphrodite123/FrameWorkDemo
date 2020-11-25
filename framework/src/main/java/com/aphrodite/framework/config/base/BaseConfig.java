package com.aphrodite.framework.config.base;

import android.os.Environment;

import com.aphrodite.framework.BuildConfig;

import java.io.File;

/**
 * Created by Aphrodite on 2018/7/26.
 */
public class BaseConfig {
    public static String PACKAGE_NAME = BuildConfig.APPLICATION_ID;

    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;
    public static final String ROOT_PATH = SDCARD_PATH + PACKAGE_NAME + File.separator;

    /**
     * 图片缓存文件夹
     */
    public static final String IMAGE_CACHE_DIR = "image_cache/";
    public static final String PATCH_CACHE_DIR = "patch_cache/";
    public static final String PATCH_FILENAME = "mistudy.patch";
    public static final String DEST_APK = "dest.apk";
    public static final String SHARE_MEDIA_DIR = "share_media/";
    /**
     * 图片后缀名
     */
    public static final String IMAGE_SUFFIX = ".jpg";
    public static final String IMAGE_SUFFIX_PNG = ".png";
    /**
     * 网页缓存路径
     */
    public static final String WEB_CACHE_DIR = "web_cache/";
    /**
     * 本地DOM缓存路径
     */
    public static final String DOM_CACHE_DIR = "dom_cache/";
    /**
     * 本地APP缓存路径
     */
    public static final String APP_CACHE_DIR = "app_cache/";
    /**
     * 减号
     */
    public static final String MINUS = "-";
    /**
     * 百分号
     */
    public static final String PERCENT = "%";
    /**
     * 斜杠
     */
    public static final String SLASH = "/";
    /**
     * App版本前缀，V
     */
    public static final String PREFIX_VERSION = "V";
    /**
     * 多媒体文件类型选择图片，任意格式
     */
    public static final String IMAGE_FILE = "image/*";
    /**
     * 本地文件路径
     */
    public static final String FILE_PATH = "file/";
    /**
     * 歌词本地缓存路径
     */
    public static final String LYRIC_PATH = "lyric/";
    /**
     * 点
     */
    public static final String POINT = ".";
    /**
     * 钱 符号
     */
    public static final String SIGN_DOLLAR = "¥";

    public static final String SUFFIX_ZIP = ".zip";

    /**
     * RN包本地根目录
     */
    public static final String RN_BUNDLE_ROOT = "react-native-bundle/";

    /**
     * RN包本地 assets 目录
     */
    public static final String RN_BUNDLE_ASSETS = "assets/";

    /**
     * RN包本地路径
     */
    public static final String RN_BUNDLE_PATH = RN_BUNDLE_ROOT + RN_BUNDLE_ASSETS + "index.bundle";

}
