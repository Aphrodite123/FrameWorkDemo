package com.aphrodite.framework.model.network.api;

import android.text.TextUtils;

import com.aphrodite.framework.model.network.interceptor.BaseHeaderInterceptor;
import com.aphrodite.framework.model.network.interceptor.BaseResponseInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aphrodite on 2019/5/17.
 */
public class RetrofitInitial {
    private int mDefaultTimeOut = 15;
    private CookieJar mCookieJar;
    private boolean mRetryConnect = false;
    private BaseHeaderInterceptor mHeaderInterceptor;
    private BaseResponseInterceptor mResponseInterceptor;
    private OkHttpClient.Builder mOkHttpBuilder;

    private String mBaseUrl;
    private Retrofit.Builder mRetrofitBuilder;

    private Retrofit mRetrofit;

    public RetrofitInitial(Builder builder) {
        initRetrofit(builder);
    }

    private void initRetrofit(Builder builder) {
        if (null == builder) {
            return;
        }

        this.mCookieJar = builder.mCookieJar;
        this.mHeaderInterceptor = builder.mHeaderInterceptor;
        this.mResponseInterceptor = builder.mResponseInterceptor;
        this.mOkHttpBuilder = builder.mOkHttpBuilder;
        this.mBaseUrl = builder.mBaseUrl;
        this.mRetrofitBuilder = builder.mRetrofitBuilder;

        if (null == mOkHttpBuilder) {
            mOkHttpBuilder = new OkHttpClient.Builder();

            if (null != mCookieJar) {
                mOkHttpBuilder.cookieJar(mCookieJar);
            }

            mOkHttpBuilder.connectTimeout(mDefaultTimeOut, TimeUnit.SECONDS);
            mOkHttpBuilder.writeTimeout(mDefaultTimeOut, TimeUnit.SECONDS);
            mOkHttpBuilder.readTimeout(mDefaultTimeOut, TimeUnit.SECONDS);

            mOkHttpBuilder.retryOnConnectionFailure(mRetryConnect);

            if (null != mHeaderInterceptor) {
                mOkHttpBuilder.addInterceptor(mHeaderInterceptor);
            }

            if (null != mResponseInterceptor) {
                mOkHttpBuilder.addInterceptor(mResponseInterceptor);
            }
        }

        if (null == mRetrofitBuilder) {
            mRetrofitBuilder = new Retrofit.Builder();

            if (!TextUtils.isEmpty(mBaseUrl)) {
                mRetrofitBuilder.baseUrl(mBaseUrl);
            }

            mRetrofitBuilder.addConverterFactory(GsonConverterFactory.create());
            mRetrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            mRetrofitBuilder.client(mOkHttpBuilder.build());
        }

        this.mRetrofit = mRetrofitBuilder.build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static class Builder {
        private CookieJar mCookieJar;
        private BaseHeaderInterceptor mHeaderInterceptor;
        private BaseResponseInterceptor mResponseInterceptor;
        private OkHttpClient.Builder mOkHttpBuilder;

        private String mBaseUrl;
        private Retrofit.Builder mRetrofitBuilder;

        public RetrofitInitial build() {
            return new RetrofitInitial(this);
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

        public Builder okHttpBuilder(OkHttpClient.Builder builder) {
            this.mOkHttpBuilder = builder;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.mBaseUrl = baseUrl;
            return this;
        }

        public Builder retrofitBuilder(Retrofit.Builder builder) {
            this.mRetrofitBuilder = builder;
            return this;
        }
    }
}
