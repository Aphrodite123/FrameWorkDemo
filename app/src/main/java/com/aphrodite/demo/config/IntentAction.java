package com.aphrodite.demo.config;

import com.aphrodite.framework.config.base.BaseConfig;

/**
 * Created by Aphrodite on 2018/7/26.
 */
public interface IntentAction {

    String ACTION_SUFFIX = BaseConfig.PACKAGE_NAME + ".view.";

    interface BeautyDetailsAction {
        String ACTION = ACTION_SUFFIX + "BEAUTYDETAILS";

        String ID = "source_id";

        String URL = "source_url";
    }

}
