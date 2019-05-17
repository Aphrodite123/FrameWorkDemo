package com.aphrodite.framework.model.network.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Aphrodite on 2019/5/17.
 */
public class BaseResponseInterceptor implements Interceptor {
    private static final String TAG = BaseResponseInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (null == chain) {
            return null;
        }

        Request request = chain.request();
        Response response = chain.proceed(chain.request());
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        if (null == response) {
            return null;
        }

        Log.i(TAG, "Request info: " + request.toString());
        Log.i(TAG, "Response info: " + content);

        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}
