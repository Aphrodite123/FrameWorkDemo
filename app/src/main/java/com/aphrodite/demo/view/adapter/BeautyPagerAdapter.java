package com.aphrodite.demo.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.aphrodite.demo.R;
import com.aphrodite.demo.model.bean.BeautyBean;
import com.aphrodite.demo.model.bean.RecommendContentBean;
import com.aphrodite.framework.utils.ObjectUtils;
import com.aphrodite.framework.view.adapter.BasePagerAdapter;
import com.aphrodite.framework.view.widget.photoview.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import androidx.annotation.NonNull;

/**
 * Created by Aphrodite on 2019/5/27.
 */
public class BeautyPagerAdapter<T> extends BasePagerAdapter {
    private PhotoView mPhotoView;

    private Context mContext;

    public BeautyPagerAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if (null != mInflater) {
            view = mInflater.inflate(R.layout.layout_beauty_details_item, null);
        }
        container.addView(view);

        initView(view, position);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView(container.getChildAt(position));
    }

    private void initView(View view, int position) {
        if (null != view) {
            mPhotoView = view.findViewById(R.id.item_photoview);
        }

        if (ObjectUtils.isOutOfBounds(mItems, position)) {
            return;
        }

        T t = (T) mItems.get(position);
        if (t instanceof BeautyBean) {
            BeautyBean bean = (BeautyBean) t;

            Glide.with(mContext)
                    .asBitmap()
                    .load(bean.getUrl())
                    .placeholder(R.drawable.icon_beauty_default)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .dontAnimate()
                    .into(mPhotoView);
        } else if (t instanceof RecommendContentBean) {
            RecommendContentBean contentBean = (RecommendContentBean) t;
            Glide.with(mContext)
                    .asBitmap()
                    .load(contentBean.getUrl())
                    .placeholder(R.drawable.icon_beauty_default)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .dontAnimate()
                    .into(mPhotoView);
        }
    }

}
