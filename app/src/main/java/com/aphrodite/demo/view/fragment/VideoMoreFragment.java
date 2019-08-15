package com.aphrodite.demo.view.fragment;

import android.graphics.Color;

import com.aphrodite.demo.R;
import com.aphrodite.demo.view.fragment.base.BaseFragment;

/**
 * Created by Aphrodite on 2019/8/15.
 */
public class VideoMoreFragment extends BaseFragment {


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

    }
}
