package com.aphrodite.framework.view.widget.photoview;

import android.view.MotionEvent;

/**
 * A callback to be invoked when the ImageView is flung with a single
 * touch
 */
public interface OnSingleFlingListener {
    /**
     * A callback to receive where the user flings on a ImageView. You will receive a callback if
     * the user flings anywhere on the view.
     *
     * @param e1        MotionEvent the user first touch.
     * @param e2        MotionEvent the user last touch.
     * @param velocityX Distance of user's horizontal fling.
     * @param velocityY Distance of user's vertical fling.
     * @return true or false
     */
    boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
}
