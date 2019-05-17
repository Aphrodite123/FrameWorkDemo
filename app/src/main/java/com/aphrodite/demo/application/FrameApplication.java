package com.aphrodite.demo.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.aphrodite.demo.BuildConfig;
import com.aphrodite.demo.application.base.BaseApplication;
import com.aphrodite.demo.config.HttpConfig;
import com.aphrodite.framework.model.network.api.RetrofitInitial;
import com.aphrodite.framework.model.network.interceptor.BaseHeaderInterceptor;
import com.aphrodite.framework.model.network.interceptor.BaseResponseInterceptor;
import com.aphrodite.framework.utils.PathUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aphrodite on 2018/7/26.
 */
public class FrameApplication extends BaseApplication {
    private static FrameApplication mIpenApplication;

    private int mActivityCount;

    private static RetrofitInitial mRetrofitInitial;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    protected void initSystem() {
        this.mIpenApplication = this;

        registerActivityLifecycleCallbacks(lifecycleCallbacks);

        initHttp();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static FrameApplication getApplication() {
        return mIpenApplication;
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
