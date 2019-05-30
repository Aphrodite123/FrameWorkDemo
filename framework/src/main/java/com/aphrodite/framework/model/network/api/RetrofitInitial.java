package com.aphrodite.framework.model.network.api;

import android.content.Context;
import android.text.TextUtils;

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
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Aphrodite on 2019/5/17.
 */
public class RetrofitInitial {
    private Context mContext;
    private int mDefaultTimeOut = 15;
    private CookieJar mCookieJar;
    private boolean mRetryConnect = false;
    private BaseHeaderInterceptor mHeaderInterceptor;
    private BaseResponseInterceptor mResponseInterceptor;
    private long maxCacheSize;
    private OkHttpClient.Builder mOkHttpBuilder;

    private String mBaseUrl;
    private Retrofit.Builder mRetrofitBuilder;

    private Retrofit mRetrofit;
    private boolean mIsJson;

    public RetrofitInitial(Builder builder) {
        initRetrofit(builder);
    }

    private void initRetrofit(Builder builder) {
        if (null == builder) {
            return;
        }

        this.mContext = builder.mContext;
        this.mCookieJar = builder.mCookieJar;
        this.mHeaderInterceptor = builder.mHeaderInterceptor;
        this.mResponseInterceptor = builder.mResponseInterceptor;
        this.maxCacheSize = builder.maxCacheSize;
        this.mOkHttpBuilder = builder.mOkHttpBuilder;
        this.mBaseUrl = builder.mBaseUrl;
        this.mRetrofitBuilder = builder.mRetrofitBuilder;
        this.mIsJson = builder.mIsJson;

        if (null == mOkHttpBuilder) {
            mOkHttpBuilder = new OkHttpClient.Builder();

            mOkHttpBuilder.connectTimeout(mDefaultTimeOut, TimeUnit.SECONDS);
            mOkHttpBuilder.writeTimeout(mDefaultTimeOut, TimeUnit.SECONDS);
            mOkHttpBuilder.readTimeout(mDefaultTimeOut, TimeUnit.SECONDS);

            mOkHttpBuilder.retryOnConnectionFailure(mRetryConnect);

            if (null == mCookieJar) {
                ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));
                mOkHttpBuilder.cookieJar(cookieJar);
            } else {
                mOkHttpBuilder.cookieJar(mCookieJar);
            }

            if (null != mHeaderInterceptor) {
                mOkHttpBuilder.addInterceptor(mHeaderInterceptor);
            }

            if (null != mResponseInterceptor) {
                mOkHttpBuilder.addInterceptor(mResponseInterceptor);
            }

            mOkHttpBuilder.cache(new Cache(new File(PathUtils.getExternalFileDir(mContext)), maxCacheSize));
        }

        if (null == mRetrofitBuilder) {
            mRetrofitBuilder = new Retrofit.Builder();

            if (!TextUtils.isEmpty(mBaseUrl)) {
                mRetrofitBuilder.baseUrl(mBaseUrl);
            }

            if (mIsJson) {
                mRetrofitBuilder.addConverterFactory(GsonConverterFactory.create());
            } else {
                //拦截字符串类型
                mRetrofitBuilder.addConverterFactory(ScalarsConverterFactory.create());
            }
            mRetrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            mRetrofitBuilder.client(mOkHttpBuilder.build());
        }

        this.mRetrofit = mRetrofitBuilder.build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static class Builder {
        private Context mContext;
        private CookieJar mCookieJar;
        private BaseHeaderInterceptor mHeaderInterceptor;
        private BaseResponseInterceptor mResponseInterceptor;
        private long maxCacheSize = 10 * 1024 * 1024;
        private OkHttpClient.Builder mOkHttpBuilder;

        private String mBaseUrl;
        private Retrofit.Builder mRetrofitBuilder;
        private boolean mIsJson = true;

        public RetrofitInitial build() {
            return new RetrofitInitial(this);
        }

        public Builder with(Context context) {
            this.mContext = context;
            return this;
        }

        public Builder cookieJar(CookieJar cookieJar) {
            this.mCookieJar = cookieJar;
            return this;
        }

        public Builder headerInterceptor(BaseHeaderInterceptor headerInterceptor) {
            this.mHeaderInterceptor = headerInterceptor;
            return this;
        }

        public Builder responseInterceptor(BaseResponseInterceptor responseInterceptor) {
            this.mResponseInterceptor = responseInterceptor;
            return this;
        }

        public Builder cacheMaxSize(long size) {
            this.maxCacheSize = size;
            return this;
        }

        public Builder okHttpBuilder(OkHttpClient.Builder builder) {
            this.mOkHttpBuilder = builder;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.mBaseUrl = baseUrl;
            return this;
        }

        public Builder isJson(boolean isJson) {
            this.mIsJson = isJson;
            return this;
        }

        public Builder retrofitBuilder(Retrofit.Builder builder) {
            this.mRetrofitBuilder = builder;
            return this;
        }
    }
}
