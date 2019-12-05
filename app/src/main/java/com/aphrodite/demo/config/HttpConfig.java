package com.aphrodite.demo.config;

import com.aphrodite.framework.config.base.BaseConfig;

/**
 * Created by Aphrodite on 2018/7/31.
 */
public class HttpConfig extends BaseConfig {
    public static final String URL_HTTP_PREFIX = "http://";

    /**
     * url参数分隔符
     */
    public static final char URL_PARAM_SEPARATOR = '&';

    /**
     * 等于号
     */
    public static final char EQUAL_SYMBOL = '=';

    /**
     * 网络请求超时
     */
    public static final int DEFAULT_TIMEOUT = 1000;

    public static final String URL_MORE_BEAUTY = "https://www.buxiuse.com/";
}
