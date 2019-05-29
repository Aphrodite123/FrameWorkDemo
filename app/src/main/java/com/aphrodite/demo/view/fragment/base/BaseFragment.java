package com.aphrodite.demo.view.fragment.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphrodite.demo.R;
import com.aphrodite.framework.utils.ObjectUtils;
import com.aphrodite.framework.utils.UIUtils;
import com.aphrodite.framework.view.widget.dialog.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Aphrodite on 2019/5/29.
 */
public abstract class BaseFragment extends Fragment {
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

    private Unbinder mUnbinder;
    private Activity mActivity;
    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getViewId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mActivity = getActivity();
        initToolbar(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingToolbar();
        initView();
        initData();
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }
    }

    protected abstract int getViewId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    private void settingToolbar() {
        // 4.4以上设备适用，4.4以下使用系统默认颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            if (null != mActivity) {
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

            if (null == mToolbar) {
                return;
            }
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();
            params.topMargin = UIUtils.getStatusBarHeight(getContext());
            mToolbar.setLayoutParams(params);
        }
    }

    private void initToolbar(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        if (null == mToolbar)
            return;

        mToolbar.setContentInsetsAbsolute(0, 0);
        mLeftBtn = view.findViewById(R.id.iv_left_btn);
        mLeftText = view.findViewById(R.id.tv_left_text);
        mClosePageBtn = view.findViewById(R.id.close_page_btn);
        mCenterLL = view.findViewById(R.id.center_ll);
        mCenterText = view.findViewById(R.id.tv_center_text);
        mDateText = view.findViewById(R.id.date_tv);
        mRightBtn = view.findViewById(R.id.iv_right_btn);
        mSecondRightBtn = view.findViewById(R.id.right_second_iv);
        mRightText = view.findViewById(R.id.tv_right_text);
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

    public void showKeyBoard(EditText editText) {
        if (null == editText) {
            return;
        }

        editText.clearFocus();
        editText.requestFocus();
        UIUtils.openSoftKeyboard(editText);
    }

    public void hideKeyBoard() {
        UIUtils.closeSoftKeyboard(getContext());
    }

    public void showLoadingDialog() {
        if (null == mLoadingDialog) {
            mLoadingDialog = new LoadingDialog(getContext(), R.style.dialog_loading, R.drawable.loading_animation);
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
        if (null != mActivity) {
            mActivity.finish();
        }
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

}
