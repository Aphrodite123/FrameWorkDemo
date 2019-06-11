package com.aphrodite.demo.config;

import com.aphrodite.framework.config.base.BaseConfig;

/**
 * Created by Aphrodite on 2019/5/28.
 */
public class AppConfig extends BaseConfig {
    public interface IntentKey {
        String RECOMMEND_PAGE_URL = "recommend_page_url";
    }

    /**
     * 资源类型
     */
    public interface SourceType {
        int BASE = 0;

        /**
         * 美女推荐
         */
        int BEAUTY = BASE + 1;

        /**
         * 更多推荐
         */
        int RECOMMEND = BASE + 2;
    }

}
