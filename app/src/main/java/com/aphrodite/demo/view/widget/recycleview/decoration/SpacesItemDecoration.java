package com.aphrodite.demo.view.widget.recycleview.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by clevo on 2015/7/27.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //根据params.getSpanIndex()来判断左右边确定分割线第一列设置左边距为space，右边距为space/2（第二列反之）
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        if (0 == parent.getChildAdapterPosition(view)) {
            return;
        }

        if (params.getSpanIndex() % 2 == 0) {
            outRect.left = space;
            outRect.right = space / 2;
        } else {
            outRect.left = space / 2;
            outRect.right = space;
        }

        if (1 == parent.getChildAdapterPosition(view)) {
            outRect.bottom = 0;
        } else {
            outRect.bottom = space;
        }
    }
}
