package com.aphrodite.demo.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.config.AppConfig;
import com.aphrodite.demo.view.activity.base.BaseActivity;
import com.aphrodite.demo.view.fragment.GroupFragment;
import com.aphrodite.demo.view.fragment.HomeFragment;
import com.aphrodite.demo.view.fragment.UserFragment;
import com.aphrodite.demo.view.fragment.TopicFragment;
import com.aphrodite.demo.view.fragment.base.BaseFragment;
import com.aphrodite.framework.utils.ObjectUtils;
import com.aphrodite.framework.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.home_page)
    LinearLayout mHomePage;
    @BindView(R.id.group_page)
    TextView mGroupPage;
    @BindView(R.id.topic_page)
    TextView mVideoMore;
    @BindView(R.id.user_page)
    TextView mContacts;

    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragments;

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

        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new GroupFragment());
        mFragments.add(new TopicFragment());
        mFragments.add(new UserFragment());

        mFragmentManager.beginTransaction().replace(R.id.main_content_root, new HomeFragment()).commit();

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

    @OnClick(R.id.home_page)
    public void onHomePage() {
        if (ObjectUtils.isOutOfBounds(mFragments, 0)) {
            return;
        }
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, new HomeFragment()).commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.group_page)
    public void onGroupPage() {
        if (ObjectUtils.isOutOfBounds(mFragments, 1)) {
            return;
        }
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, mFragments.get(1)).commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.topic_page)
    public void onTopicPage() {
        if (ObjectUtils.isOutOfBounds(mFragments, 2)) {
            return;
        }
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, mFragments.get(2)).commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.user_page)
    public void onUserPage() {
        if (ObjectUtils.isOutOfBounds(mFragments, 3)) {
            return;
        }
        mFragmentManager.beginTransaction().replace(R.id.main_content_root, mFragments.get(3)).commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

}
