package com.aphrodite.demo.view.widget.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhangjingming on 2017/6/7.
 * 水平滑动 RecyclerView
 */

public class HorizontalRecycleView extends RecyclerView {
    int lastX, lastY;

    public HorizontalRecycleView(Context context) {
        this(context, null);
    }

    public HorizontalRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(layoutManager);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = 0;
                lastY = 0;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - lastX) >= Math.abs(y - lastY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
