package com.aphrodite.framework.model.network.interceptor;

import com.aphrodite.framework.utils.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aphrodite on 2019/5/17.
 */
public class BaseHeaderInterceptor implements Interceptor {
    protected Map<String, String> mHeaders = new HashMap<>();

    public BaseHeaderInterceptor() {
    }

    public BaseHeaderInterceptor(Map<String, String> headers) {
        this.mHeaders = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (null == chain || ObjectUtils.isEmpty(mHeaders)) {
            return null;
        }

        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        for (Map.Entry<String, String> entry : mHeaders.entrySet()) {
            if (null == entry) {
                continue;
            }
            builder.header(entry.getKey(), entry.getValue());
        }

        builder.method(originalRequest.method(), originalRequest.body());
        Request request = builder.build();

        return chain.proceed(request);
    }
}
