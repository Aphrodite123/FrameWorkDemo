package com.aphrodite.demo.view.widget.recycleview.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphrodite.demo.R;
import com.aphrodite.demo.view.widget.recycleview.inter.RefreshTrigger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Aphrodite on 2018/12/26.
 */
public class HeadStyleLayout extends LinearLayout implements RefreshTrigger {
    private ImageView mHeaderIV;
    private TextView mHeaderText;

    public HeadStyleLayout(@NonNull Context context) {
        this(context, null);
    }

    public HeadStyleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadStyleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_header, this, true);

        mHeaderIV = findViewById(R.id.header_iv);
        mHeaderText = findViewById(R.id.header_prompt);

        Drawable imageDrawable = context.getResources().getDrawable(R.drawable.pulling_animation);
        mHeaderIV.setImageDrawable(imageDrawable);
    }

    @Override
    public void onStart(boolean automatic, int headerHeight, int finalHeight) {

    }

    @Override
    public void onMove(boolean finished, boolean automatic, int moved) {
        if (null == mHeaderIV) {
            return;
        }

        ((AnimationDrawable) mHeaderIV.getDrawable()).start();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        if (null == mHeaderIV) {
            return;
        }

        ((AnimationDrawable) mHeaderIV.getDrawable()).stop();
    }

    @Override
    public void onReset() {
        if (null == mHeaderIV) {
            return;
        }

        ((AnimationDrawable) mHeaderIV.getDrawable()).stop();
    }

    public void setAnimationVisiable(boolean visiable) {
        if (null == mHeaderIV) {
            return;
        }

        if (visiable) {
            mHeaderIV.setVisibility(VISIBLE);
        } else {
            mHeaderIV.setVisibility(GONE);
        }
    }

    public void setTextVisiable(boolean visiable) {
        if (null == mHeaderText) {
            return;
        }

        if (visiable) {
            mHeaderText.setVisibility(VISIBLE);
        } else {
            mHeaderText.setVisibility(GONE);
        }
    }

    public void setText(String prompt) {
        if (null == mHeaderText) {
            return;
        }
        mHeaderText.setText(prompt);
    }

    public void setText(int res) {
        if (null == mHeaderText) {
            return;
        }
        mHeaderText.setText(getResources().getString(res));
    }

}
