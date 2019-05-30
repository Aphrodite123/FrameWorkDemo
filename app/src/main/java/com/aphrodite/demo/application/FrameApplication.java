package com.aphrodite.demo.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.aphrodite.demo.application.base.BaseApplication;
import com.aphrodite.demo.model.database.migration.GlobalRealmMigration;
import com.aphrodite.demo.utils.LogUtils;
import com.aphrodite.framework.model.network.api.RetrofitInitial;
import com.aphrodite.framework.utils.ToastUtils;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.FileNotFoundException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

import static com.aphrodite.demo.config.RuntimeConfig.DATABASE_NAME;
import static com.aphrodite.demo.config.RuntimeConfig.DATABASE_VERSION;

/**
 * Created by Aphrodite on 2018/7/26.
 */
public class FrameApplication extends BaseApplication {
    private static FrameApplication mIpenApplication;

    private int mActivityCount;

    private RealmConfiguration mRealmConfiguration;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    protected void initSystem() {
        this.mIpenApplication = this;

        registerActivityLifecycleCallbacks(lifecycleCallbacks);

        Logger.addLogAdapter(new AndroidLogAdapter());

        initRealm();

        initStetho();

        initToast();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static FrameApplication getApplication() {
        return mIpenApplication;
    }

    private void initRealm() {
        Realm.init(this);
        createGlobalRealm();
    }

    public boolean isAppBackground() {
        return 0 == mActivityCount;
    }

    public RetrofitInitial getRetrofitInit(boolean isJson, String baseUrl) {
        RetrofitInitial retrofitInitial = new RetrofitInitial
                .Builder()
                .with(getApplication())
                .isJson(isJson)
                .baseUrl(baseUrl)
                .build();
        return retrofitInitial;
    }

    private void createGlobalRealm() {
        mRealmConfiguration = new RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .schemaVersion(DATABASE_VERSION)
                //开发阶段，删除旧版本数据
//                .deleteRealmIfMigrationNeeded()
                .migration(new GlobalRealmMigration())
                .build();
        Realm.setDefaultConfiguration(mRealmConfiguration);
    }

    public Realm getGlobalRealm() throws FileNotFoundException {
        Realm realm;
        if (null == mRealmConfiguration) {
            mRealmConfiguration = new RealmConfiguration.Builder()
                    .name(DATABASE_NAME)
                    .schemaVersion(DATABASE_VERSION)
                    .migration(new GlobalRealmMigration())
                    .build();
        }

        try {
            realm = Realm.getInstance(mRealmConfiguration);
        } catch (RealmMigrationNeededException e) {
            LogUtils.e("Enter getGlobalRealm method.RealmMigrationNeededException: " + e);
            Realm.migrateRealm(mRealmConfiguration, new GlobalRealmMigration());
            realm = Realm.getInstance(mRealmConfiguration);
        }

        return realm;
    }

    /**
     * 退出程序
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * 初始化Stetho调试工具
     */
    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build());
    }

    private void initToast() {
        ToastUtils.init(this);
    }

    private Application.ActivityLifecycleCallbacks lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            mActivityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            mActivityCount--;
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

}
