package com.aphrodite.demo.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.config.AppConfig;
import com.aphrodite.demo.view.activity.base.BaseActivity;
import com.aphrodite.demo.view.fragment.BeautyListFragment;
import com.aphrodite.demo.view.fragment.BeautyMoreFragment;
import com.aphrodite.demo.view.fragment.ContactsFragment;
import com.aphrodite.demo.view.fragment.VideoMoreFragment;
import com.aphrodite.framework.utils.ToastUtils;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.beauty_recommend_ll)
    LinearLayout mBeautyRecommend;
    @BindView(R.id.beauty_more)
    TextView mBeautyMore;
    @BindView(R.id.video_more)
    TextView mVideoMore;
    @BindView(R.id.contacts)
    TextView mContacts;

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
    }

    @Override
    protected void initData() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, new BeautyListFragment()).commit();
        String permissions[] = {Manifest.permission.READ_CONTACTS};
        if (!hasPermission(permissions)) {
            requestPermission(permissions, AppConfig.PermissionType.CONTACTS_PERMISSION);
        }
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

    @OnClick(R.id.beauty_recommend_ll)
    public void onBeautyRecommend() {
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, new BeautyListFragment()).commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.beauty_more)
    public void onBeautyMore() {
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, new BeautyMoreFragment()).commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.video_more)
    public void onVideoMore() {
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, new VideoMoreFragment()).commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.contacts)
    public void onContacts() {
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, new ContactsFragment()).commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

}
