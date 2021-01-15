package com.aphrodite.demo.config;

import com.aphrodite.demo.BuildConfig;
import com.aphrodite.framework.config.base.BaseConfig;

/**
 * Created by Aphrodite on 2019/5/28.
 */
public class AppConfig extends BaseConfig {
    public static String PACKAGE_NAME = BuildConfig.APPLICATION_ID;

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

    /**
     * 权限
     */
    public interface PermissionType {
        int BASE = 0x0000;

        int WRITE_EXTERNAL_PERMISSION = BASE + 1;

        int REQUEST_CALL_PERMISSION = BASE + 2;

        int OVERLAY_PERMISSION = BASE + 3;

        int CAMERA_PERMISSION = BASE + 4;

        int LOCATION_PERMISSION = BASE + 5;

        int PHONE_PERMISSION = BASE + 6;

        int CONTACTS_PERMISSION = BASE+7;
    }

}
