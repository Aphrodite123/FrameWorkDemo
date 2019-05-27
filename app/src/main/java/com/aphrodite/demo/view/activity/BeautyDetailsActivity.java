package com.aphrodite.demo.view.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.aphrodite.demo.R;
import com.aphrodite.demo.config.IntentAction;
import com.aphrodite.demo.model.bean.BeautyBean;
import com.aphrodite.demo.view.activity.base.BaseActivity;
import com.aphrodite.demo.view.adapter.BeautyPagerAdapter;

import butterknife.BindView;

/**
 * Created by Aphrodite on 2019/5/27.
 */
public class BeautyDetailsActivity extends BaseActivity {
    @BindView(R.id.beauty_details_index)
    TextView mBeautyIndex;
    @BindView(R.id.beauty_details_total)
    TextView mBeautyTotal;
    @BindView(R.id.beauty_details_vp)
    ViewPager mViewPager;

    private String mId;
    private String mUrl;

    private BeautyPagerAdapter mPagerAdapter;

    @Override
    protected int getViewId() {
        return R.layout.activity_beauty_details;
    }

    @Override
    protected void initView() {
        mPagerAdapter = new BeautyPagerAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            mId = intent.getStringExtra(IntentAction.BeautyDetailsAction.ID);
            mUrl = intent.getStringExtra(IntentAction.BeautyDetailsAction.URL);
        }

        BeautyBean bean = new BeautyBean();
        bean.set_id(mId);
        bean.setUrl(mUrl);

        mPagerAdapter.addItem(bean);
    }
}
