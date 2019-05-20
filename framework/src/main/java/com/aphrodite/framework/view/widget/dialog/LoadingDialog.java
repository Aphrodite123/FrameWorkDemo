package com.aphrodite.framework.view.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Aphrodite on 2017/12/13.
 * 正在加载提示框
 */

public class LoadingDialog extends Dialog {
    private ImageView mImageView;
    private AnimationDrawable mAnimation;
    private Context mContext;
    private int mDrawableRes;

    public LoadingDialog(Context context, int theme, int drawableRes) {
        super(context, theme);
        this.mContext = context;
        this.mDrawableRes = drawableRes;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageView = new ImageView(mContext);
        mImageView.setImageResource(mDrawableRes);
        setContentView(mImageView);
        mAnimation = (AnimationDrawable) mImageView.getDrawable();
        mAnimation.start();
    }
}
