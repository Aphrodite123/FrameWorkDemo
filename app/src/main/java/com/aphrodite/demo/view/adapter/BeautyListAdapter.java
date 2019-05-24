package com.aphrodite.demo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aphrodite.demo.R;
import com.aphrodite.demo.model.bean.BeautyBean;
import com.aphrodite.demo.view.widget.imageview.ScaleImageView;
import com.aphrodite.framework.view.adapter.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aphrodite on 2019/5/22.
 */
public class BeautyListAdapter<T> extends BaseRecyclerAdapter<T, BeautyListAdapter.ViewHolder> {
    private Context mContext;

    public BeautyListAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_beauty_list_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BeautyListAdapter.ViewHolder holder, int position) {
        BeautyBean bean = (BeautyBean) getItem(position);
        if (null == bean) {
            return;
        }

        holder.mItemIV.setSize(bean.getWidth(), bean.getHeight());

        Glide.with(mContext)
                .load(bean.getUrl())
                .asBitmap()
                .placeholder(R.drawable.icon_beauty_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(holder.mItemIV);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.beauty_item_iv)
        ScaleImageView mItemIV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}