package com.aphrodite.demo.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aphrodite.demo.R;
import com.aphrodite.demo.model.bean.RecommendTypeBean;
import com.aphrodite.framework.utils.UIUtils;
import com.aphrodite.framework.view.adapter.BaseRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aphrodite on 2019/3/12.
 */
public class RecommendMoreTabAdapter<T> extends BaseRecyclerAdapter<T, RecommendMoreTabAdapter.ViewHolder> {
    public static final int MODE_FIX = 1;
    public static final int MODE_FILL = 2;

    private int itemHeight;
    private int currentPosition = 0;
    private int mode;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public RecommendMoreTabAdapter(Context context, int mode, OnItemClickListener listener) {
        super(context);
        this.itemHeight = UIUtils.dip2px(context, 40);
        this.mode = mode;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_category_title, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendMoreTabAdapter.ViewHolder holder, int position) {
        final int index = position;
        final RecommendTypeBean categoryBean = (RecommendTypeBean) getItem(index);
        if (null == categoryBean) {
            return;
        }

        holder.tvName.setText(categoryBean.getName());
        holder.tvName.setSelected(currentPosition == index);
        holder.ivTag.setVisibility(currentPosition == index ? View.VISIBLE : View.INVISIBLE);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentPosition(index);

                if (null != listener) {
                    listener.onItemClick(index);
                }
            }
        });
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_tag)
        ImageView ivTag;
        @BindView(R.id.rl_root)
        View root;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            int itemWidth;
            if (MODE_FIX == mode)
                itemWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            else
                itemWidth = UIUtils.getScreenSize(mContext)[0] / getItemCount();
            root.setLayoutParams(new ViewGroup.LayoutParams(itemWidth, itemHeight));
        }
    }

}
