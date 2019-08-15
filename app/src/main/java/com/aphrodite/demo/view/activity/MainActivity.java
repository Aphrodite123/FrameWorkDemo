package com.aphrodite.demo.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.view.activity.base.BaseActivity;
import com.aphrodite.demo.view.fragment.BeautyListFragment;
import com.aphrodite.demo.view.fragment.BeautyMoreFragment;
import com.aphrodite.demo.view.fragment.VideoMoreFragment;
import com.aphrodite.framework.utils.ToastUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.beauty_recommend_ll)
    LinearLayout mBeautyRecommend;
    @BindView(R.id.beauty_more)
    TextView mBeautyMore;
    @BindView(R.id.video_more)
    TextView mVideoMore;

    private FragmentManager mFragmentManager;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setStatusBarColor(this);
    }

    @Override
    protected void initListener() {
        mBeautyRecommend.setOnClickListener(this);
        mBeautyMore.setOnClickListener(this);
        mVideoMore.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, new BeautyListFragment()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1000) {
            //双击退出
            ToastUtils.showMessage(R.string.press_exit_again);
            mExitTime = System.currentTimeMillis();
        } else {
            // 退出
            FrameApplication.getApplication().exit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.beauty_recommend_ll:
                mFragmentManager.beginTransaction().replace(R.id.main_content_root, new BeautyListFragment()).commit();
                break;
            case R.id.beauty_more:
                mFragmentManager.beginTransaction().replace(R.id.main_content_root, new BeautyMoreFragment()).commit();
                break;
            case R.id.video_more:
                mFragmentManager.beginTransaction().replace(R.id.main_content_root, new VideoMoreFragment()).commit();
            default:
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
}
