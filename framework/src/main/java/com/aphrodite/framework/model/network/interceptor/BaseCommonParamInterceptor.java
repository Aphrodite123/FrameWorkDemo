package com.aphrodite.framework.model.network.interceptor;

import com.aphrodite.framework.utils.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aphrodite on 2019/8/21.
 */
public class BaseCommonParamInterceptor implements Interceptor {
    protected Map<String, String> mParams = new HashMap<>();

    public BaseCommonParamInterceptor() {
    }

    public BaseCommonParamInterceptor(Map<String, String> body, Map<String, String> params) {
        this.mParams = params;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (null == chain) {
            return null;
        }

        Request originalRequest = chain.request();
        HttpUrl.Builder builder = originalRequest.url().newBuilder();
        if ("POST".equals(originalRequest.method())) {
            for (Map.Entry<String, String> entry : mBody.entrySet()) {
                if (null == entry) {
                    continue;
                }
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        if (!ObjectUtils.isEmpty(mParams)) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                if (null == entry) {
                    continue;
                }

                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        HttpUrl httpUrl = builder.build();
        Request request = originalRequest
                .newBuilder()
                .method(originalRequest.method(), originalRequest.body())
                .url(httpUrl)
                .build();

        return chain.proceed(request);
    }
}
