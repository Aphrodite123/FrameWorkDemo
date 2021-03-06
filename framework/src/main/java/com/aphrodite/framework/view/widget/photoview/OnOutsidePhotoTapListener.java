package com.aphrodite.framework.view.widget.photoview;

import android.widget.ImageView;

/**
 * Callback when the user tapped outside of the photo
 */
public interface OnOutsidePhotoTapListener {

    /**
     * The outside of the photo has been tapped
     *
     * @param imageView imageView
     */
    void onOutsidePhotoTap(ImageView imageView);
}
