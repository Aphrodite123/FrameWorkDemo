package com.aphrodite.demo.application.base;

import android.app.Application;

/**
 * Created by Aphrodite on 2018/7/26.
 */
public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSystem();
    }

    protected abstract void initSystem();

}
