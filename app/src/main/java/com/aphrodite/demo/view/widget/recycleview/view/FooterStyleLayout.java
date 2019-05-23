package com.aphrodite.demo.view.widget.recycleview.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphrodite.demo.R;

/**
 * Created by Aphrodite on 2018/12/26.
 */
public class FooterStyleLayout extends FrameLayout {
    public static final int GONE = 0;
    public static final int LOADING = 1;
    public static final int END = 2;
    public static final int ERROR = 3;

    private LinearLayout mEndView;
    private LinearLayout mLoadingMoreView;
    private ImageView mProgressIV;
    private TextView mPrompt;

    private int mStatus;

    public FooterStyleLayout(@NonNull Context context) {
        this(context, null);
    }

    public FooterStyleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterStyleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_footer, this, true);

        mEndView = findViewById(R.id.list_item_root);
        mLoadingMoreView = findViewById(R.id.footer_loading_more_root);
        mProgressIV = findViewById(R.id.footer_proressbar_iv);
        mPrompt = findViewById(R.id.footer_prompt);

        setStatus(GONE);
    }

    public void startAnimation() {
        if (null == mProgressIV) {
            return;
        }
        ((AnimationDrawable) mProgressIV.getDrawable()).start();
    }

    public void stopAnimation() {
        if (null == mProgressIV) {
            return;
        }
        ((AnimationDrawable) mProgressIV.getDrawable()).stop();
    }

    public void setText(String prompt) {
        if (null == mPrompt) {
            return;
        }
        mPrompt.setText(prompt);
    }

    public void setText(int res) {
        if (null == mPrompt) {
            return;
        }
        mPrompt.setText(getResources().getString(res));
    }

    public void setStatus(int status) {
        this.mStatus = status;
        onChange(status);
    }

    private void onChange(int status) {
        switch (status) {
            case GONE:
                stopAnimation();
                mEndView.setVisibility(VISIBLE);
                mLoadingMoreView.setVisibility(View.GONE);
                break;
            case LOADING:
                startAnimation();
                mEndView.setVisibility(View.GONE);
                mLoadingMoreView.setVisibility(View.VISIBLE);
                break;
            case END:
                stopAnimation();
                mEndView.setVisibility(VISIBLE);
                mLoadingMoreView.setVisibility(View.GONE);
                break;
            case ERROR:
            default:
                mEndView.setVisibility(View.VISIBLE);
                mLoadingMoreView.setVisibility(View.GONE);
                break;
        }
    }

    public boolean canLoadMore() {
        return LOADING != mStatus;
    }

}
