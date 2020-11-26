package com.aphrodite.demo.view.widget.recycleview.inter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by aspsine on 16/3/12.
 */
public abstract class IViewHolder extends RecyclerView.ViewHolder {

    public IViewHolder(View itemView) {
        super(itemView);
    }

    @Deprecated
    public final int getIPosition() {
        return getPosition() - 2;
    }

    public final int getILayoutPosition() {
        return getLayoutPosition() - 2;
    }

    public final int getIAdapterPosition() {
        if (getAdapterPosition() == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }
        return getAdapterPosition() - 2;
    }

    public final int getIOldPosition() {
        return getOldPosition() - 2;
    }

    public final long getIItemId() {
        return getItemId();
    }

    public final int getIItemViewType() {
        return getItemViewType();
    }
}
