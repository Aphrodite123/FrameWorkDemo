package com.aphrodite.demo.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.aphrodite.demo.BuildConfig;
import com.aphrodite.demo.application.base.BaseApplication;
import com.aphrodite.demo.config.HttpConfig;
import com.aphrodite.demo.model.database.migration.GlobalRealmMigration;
import com.aphrodite.demo.utils.LogUtils;
import com.aphrodite.framework.model.network.api.RetrofitInitial;
import com.aphrodite.framework.model.network.interceptor.BaseHeaderInterceptor;
import com.aphrodite.framework.model.network.interceptor.BaseResponseInterceptor;
import com.aphrodite.framework.utils.PathUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aphrodite.demo.config.RuntimeConfig.DATABASE_NAME;
import static com.aphrodite.demo.config.RuntimeConfig.DATABASE_VERSION;

/**
 * Created by Aphrodite on 2018/7/26.
 */
public class FrameApplication extends BaseApplication {
    private static FrameApplication mIpenApplication;

    private int mActivityCount;

    private static RetrofitInitial mRetrofitInitial;

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

        initHttp();
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

    private void initHttp() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpBuilder.writeTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpBuilder.readTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpBuilder.retryOnConnectionFailure(true);

        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new
                SharedPrefsCookiePersistor(FrameApplication.getApplication()));
        okHttpBuilder.cookieJar(cookieJar);

        okHttpBuilder.cache(new Cache(new File(PathUtils.getExternalFileDir(this)), 10 * 1024 * 1024));

        BaseHeaderInterceptor headerInterceptor = new BaseHeaderInterceptor();
        BaseResponseInterceptor responseInterceptor = new BaseResponseInterceptor();
        okHttpBuilder.addInterceptor(headerInterceptor);
        okHttpBuilder.addInterceptor(responseInterceptor);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BuildConfig.SERVER_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.client(okHttpBuilder.build());

        mRetrofitInitial = new RetrofitInitial.Builder()
                .okHttpBuilder(okHttpBuilder)
                .retrofitBuilder(builder)
                .build();
    }

    public boolean isAppBackground() {
        return 0 == mActivityCount;
    }

    public static RetrofitInitial getRetrofitInit() {
        return mRetrofitInitial;
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
