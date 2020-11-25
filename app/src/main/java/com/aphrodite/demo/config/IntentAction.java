package com.aphrodite.demo.config;

/**
 * Created by Aphrodite on 2018/7/26.
 */
public interface IntentAction {
    String ACTION_SUFFIX = AppConfig.PACKAGE_NAME + ".view.";

    interface BeautyDetailsAction {
        String ACTION = ACTION_SUFFIX + "BEAUTYDETAILS";

        String TYPE = "source_type";

        String ID = "source_id";

        String URL = "source_url";

        String CID = "source_cid";
    }

}
