package com.aphrodite.framework.model.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aphrodite on 2019/5/17.
 */
public class BaseHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (null == chain) {
            return null;
        }

        Request originalRequest = chain.request();
        Request request = originalRequest.newBuilder()
                .header("User-Agent", "Android, xxx")
                .header("Accept", "*/*")
                .header("Content-type", "application/json")
                .method(originalRequest.method(), originalRequest.body())
                .build();
        return chain.proceed(request);
    }
}
