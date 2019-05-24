package com.aphrodite.demo.config;

import com.aphrodite.demo.BuildConfig;
import com.aphrodite.framework.config.base.BaseConfig;

/**
 * Created by Aphrodite on 2018/7/31.
 * 本地配置文件信息，程序打开时设置初始化参数
 */
public class RuntimeConfig extends BaseConfig {
    /**
     * APP版本号
     */
    public static final String APP_VERSION = BuildConfig.VERSION_NAME;

    /**
     * API VERSION
     */
    public static final int API_VERSION = 1;

    /**
     * 数据库名称
     */
    public static final String DATABASE_NAME = "frame.realm";

    /**
     * 用户数据库名称
     */
    public static final String USER_DATABASE_NAME = "frame_%s.realm";

    /**
     * 数据库版本号
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * 是否输出日志，默认不输出
     * true: 输出日志
     * false: 不输出日志
     */
    public static boolean OUTPUT_LOG = BuildConfig.IS_DEBUG;

    /**
     * HOST 地址
     */
    public static String HOME_PAGE_HOST = BuildConfig.SERVER_URL;

}
