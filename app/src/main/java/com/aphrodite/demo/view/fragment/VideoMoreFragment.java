package com.aphrodite.demo.view.fragment;

import android.graphics.Color;

import com.aphrodite.demo.BuildConfig;
import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.model.api.RequestApi;
import com.aphrodite.demo.view.fragment.base.BaseFragment;
import com.aphrodite.framework.model.network.api.RetrofitInitial;

/**
 * Created by Aphrodite on 2019/8/15.
 */
public class VideoMoreFragment extends BaseFragment {
    private RetrofitInitial mRetrofitInit;
    private RequestApi mRequestApi;

    @Override
    protected int getViewId() {
        return R.layout.fragment_video_more;
    }

    @Override
    protected void initView() {
        setTitleText(R.string.video_more);
        setTitleColor(Color.BLACK);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        mRetrofitInit = FrameApplication.getApplication().getRetrofitInit(false, BuildConfig.SERVER_URL, null);
        mRequestApi = mRetrofitInit.getRetrofit().create(RequestApi.class);
    }
}
