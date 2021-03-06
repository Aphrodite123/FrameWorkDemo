package com.aphrodite.demo.view.widget.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.aphrodite.demo.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Aphrodite on 2019/5/31.
 */
public class TouchViewPager extends ViewPager {
    public TouchViewPager(@NonNull Context context) {
        super(context);
    }

    public TouchViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            LogUtils.e("Enter onInterceptTouchEvent method." + e);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            LogUtils.e("Enter onTouchEvent method." + e);
        }
        return false;
    }
}
