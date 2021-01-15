package com.aphrodite.demo.view.activity.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphrodite.demo.R;
import com.aphrodite.framework.utils.ObjectUtils;
import com.aphrodite.framework.utils.UIUtils;
import com.aphrodite.framework.view.manager.ActivitysManager;
import com.aphrodite.framework.view.widget.dialog.LoadingDialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Aphrodite on 2019/5/20.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder;
    private ActivitysManager mActivityManager;
    private LoadingDialog mLoadingDialog;

    /**
     * Toolbar
     */
    protected Toolbar mToolbar;
    protected ImageView mLeftBtn;
    protected TextView mLeftText;
    private ImageView mClosePageBtn;
    protected LinearLayout mCenterLL;
    protected TextView mCenterText;
    protected TextView mDateText;
    protected ImageView mRightBtn;
    protected ImageView mSecondRightBtn;
    protected TextView mRightText;

    public static final int TITLE_FLAG_SHOW_LEFT_BACK = 1 << 0; // 显示左边返回按钮
    public static final int TITLE_FLAG_SHOW_LEFT_TEXT = 1 << 1; // 显示左边文字
    public static final int TITLE_FLAG_SHOW_RIGHT_BTN = 1 << 2; // 显示右边按钮
    public static final int TITLE_FLAG_SHOW_RIGHT_TEXT = 1 << 3; // 显示右边文字
    public static final int TITLE_FLAG_SHOW_SECOND_RIGHT_BTN = 1 << 4; // 显示右边第二个按钮
    public static final int TITLE_FLAG_SHOW_CLOSE_PAGE_BTN = 1 << 5; // 显示左边关闭按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        settingToolbar();
        initToolbar();
        initConfig();
        initView();
        initListener();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mActivityManager) {
            mActivityManager = ActivitysManager.getInstance();
        }
        mActivityManager.addActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissLoadingDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }

        if (null != mActivityManager) {
            mActivityManager.finishActivity(this);
            mActivityManager = null;
        }
    }

    private void initConfig() {
        //绑定Activity(注:必须在setContentView之后)
        mUnbinder = ButterKnife.bind(this);
    }

    protected abstract int getViewId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    private void settingToolbar() {
        // 4.4以上设备适用，4.4以下使用系统默认颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 修改状态栏颜色，支持6.0以上版本
     *
     * @param activity
     */
    protected void setStatusBarColor(Activity activity) {
        if (ObjectUtils.isEmpty(activity)) {
            return;
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 隐藏状态栏，实现全屏
     */
    protected void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置手机虚拟键,适配华为底部虚拟键
     */
    private void settingVirtualKey() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Android 5.0 以上 全透明
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 状态栏（以上几行代码必须，参考setStatusBarColor|setNavigationBarColor方法源码）
            window.setStatusBarColor(Color.TRANSPARENT);
            // 虚拟导航键
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Android 4.4 以上 半透明
            Window window = getWindow();
            // 状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 虚拟导航键
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void showKeyBoard(EditText editText) {
        if (null == editText) {
            return;
        }

        editText.clearFocus();
        editText.requestFocus();
        UIUtils.openSoftKeyboard(editText);
    }

    public void hideKeyBoard() {
        UIUtils.closeSoftKeyboard(this);
    }

    public void showLoadingDialog() {
        if (null == mLoadingDialog) {
            mLoadingDialog = new LoadingDialog(this, R.style.dialog_loading, R.drawable.loading_animation);
        }

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (null == mLoadingDialog) {
            return;
        }

        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (null == mToolbar)
            return;

        mToolbar.setContentInsetsAbsolute(0, 0);
        mLeftBtn = findViewById(R.id.iv_left_btn);
        mLeftText = findViewById(R.id.tv_left_text);
        mClosePageBtn = findViewById(R.id.close_page_btn);
        mCenterLL = findViewById(R.id.center_ll);
        mCenterText = findViewById(R.id.tv_center_text);
        mDateText = findViewById(R.id.date_tv);
        mRightBtn = findViewById(R.id.iv_right_btn);
        mSecondRightBtn = findViewById(R.id.right_second_iv);
        mRightText = findViewById(R.id.tv_right_text);
    }

    /**
     * 设置Toolbar按钮属性
     *
     * @param flag 标题栏按钮属性值
     * @see TITLE_FLAG_SHOW_LEFT_BACK = 1 << 0; // 显示左边返回按钮
     * @see TITLE_FLAG_SHOW_LEFT_TEXT = 1 << 1; // 显示左边文字
     * @see TITLE_FLAG_SHOW_RIGHT_BTN = 1 << 2; // 显示右边按钮
     * @see TITLE_FLAG_SHOW_RIGHT_TEXT = 1 << 3; // 显示右边文字
     * @see TITLE_FLAG_SHOW_SECOND_RIGHT_BTN = 1 << 4; // 显示右边第二个按钮
     * @see TITLE_FLAG_SHOW_CLOSE_PAGE_BTN = 1 << 5; // 显示左边关闭按钮
     */
    public void setToolbarFlag(int flag) {
        if (null != mLeftBtn) {
            if ((flag & TITLE_FLAG_SHOW_LEFT_BACK) > 0)
                mLeftBtn.setVisibility(View.VISIBLE);
            else
                mLeftBtn.setVisibility(View.GONE);
        }
        if (null != mLeftText) {
            if ((flag & TITLE_FLAG_SHOW_LEFT_TEXT) > 0)
                mLeftText.setVisibility(View.VISIBLE);
            else
                mLeftText.setVisibility(View.GONE);
        }
        if (null != mRightBtn) {
            if ((flag & TITLE_FLAG_SHOW_RIGHT_BTN) > 0)
                mRightBtn.setVisibility(View.VISIBLE);
            else
                mRightBtn.setVisibility(View.GONE);
        }
        if (null != mRightText) {
            if ((flag & TITLE_FLAG_SHOW_RIGHT_TEXT) > 0)
                mRightText.setVisibility(View.VISIBLE);
            else
                mRightText.setVisibility(View.GONE);
        }
        if (null != mSecondRightBtn) {
            if ((flag & TITLE_FLAG_SHOW_SECOND_RIGHT_BTN) > 0) {
                mSecondRightBtn.setVisibility(View.VISIBLE);
            } else {
                mSecondRightBtn.setVisibility(View.GONE);
            }
        }
        if (null != mClosePageBtn) {
            if ((flag & TITLE_FLAG_SHOW_CLOSE_PAGE_BTN) > 0) {
                mClosePageBtn.setVisibility(View.VISIBLE);
            } else {
                mClosePageBtn.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置右边按钮图片
     *
     * @param resId 图片ID
     */
    public void setLeftBtnRes(int resId) {
        if (mLeftBtn != null) {
            mLeftBtn.setImageResource(resId);
        }
    }

    /**
     * 设置左边按钮文字
     */
    protected void setLeftText(int textId) {
        if (mLeftText != null)
            mLeftText.setText(textId);
    }

    /**
     * 设置左边按钮文字
     */
    protected void setLeftText(String text) {
        if (mLeftText != null)
            mLeftText.setText(text);
    }

    /**
     * 设置日期
     *
     * @param text
     */
    protected void setDateText(String text) {
        if (null == mDateText) {
            return;
        }

        if (ObjectUtils.isEmpty(text)) {
            mDateText.setVisibility(View.GONE);
            return;
        }

        mDateText.setText(text);
        mDateText.setVisibility(View.VISIBLE);
    }

    /**
     * 设置日期
     *
     * @param textId
     */
    protected void setDateText(int textId) {
        if (ObjectUtils.isEmpty(textId)) {
            if (mDateText != null) {
                mDateText.setVisibility(View.GONE);
            }
            return;
        }

        if (mDateText != null) {
            mDateText.setText(textId);
            mDateText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置右边按钮文字
     */
    protected void setRightText(int textId) {
        if (mRightText != null)
            mRightText.setText(textId);
    }

    /**
     * 设置右边按钮文字
     */
    protected void setRightText(String text) {
        if (mRightText != null)
            mRightText.setText(text);
    }

    /**
     * 设置标题栏文字
     *
     * @param textId 文字id
     */
    public void setTitleText(int textId) {
        if (mCenterText != null)
            mCenterText.setText(textId);
    }

    /**
     * 设置标题栏文字
     *
     * @param text 文字内容
     */
    public void setTitleText(String text) {
        if (mCenterText != null)
            mCenterText.setText(text);
    }

    /**
     * 设置标题字体颜色
     *
     * @param color
     */
    public void setTitleColor(int color) {
        if (mCenterText != null) {
            mCenterText.setTextColor(color);
        }
    }

    /**
     * 设置右边按钮图片
     *
     * @param resId 图片ID
     */
    public void setRightBtnRes(int resId) {
        if (mRightBtn != null)
            mRightBtn.setImageResource(resId);
    }

    /**
     * 设置右边第二个按钮图片
     *
     * @param resId
     */
    public void setSecondRightRes(int resId) {
        if (mSecondRightBtn != null) {
            mSecondRightBtn.setImageResource(resId);
        }
    }

    /**
     * 默认返回按钮响应事件
     *
     * @param view
     */
    public void clickBack(View view) {
        finish();
    }

    /**
     * 默认左边文字按钮响应事件
     *
     * @param view
     */
    public void clickLeftText(View view) {
    }

    /**
     * 关闭页面
     *
     * @param view
     */
    public void closePageEvent(View view) {
    }

    /**
     * 默认右边图片按钮响应事件
     *
     * @param view
     */
    public void clickRightBtn(View view) {
    }

    /**
     * 右边第二个按钮点击事件
     *
     * @param view
     */
    public void onRightSecond(View view) {
    }

    /**
     * 默认右边文字按钮响应事件
     *
     * @param view
     */
    public void clickRightText(View view) {
    }

    /**
     * Check if permissions are available.
     *
     * @param permissions
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    protected boolean hasPermission(String[] permissions) {
        boolean hasPermission = true;
        if (ObjectUtils.isEmpty(permissions)) {
            return true;
        }

        for (String str : permissions) {
            hasPermission = hasPermission && ContextCompat.checkSelfPermission(this, str) == PackageManager.PERMISSION_GRANTED;
        }
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || hasPermission;
    }

    /**
     * Request permissions.
     *
     * @param permissions
     * @param requestCode
     */
    @TargetApi(Build.VERSION_CODES.M)
    protected void requestPermission(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

}
