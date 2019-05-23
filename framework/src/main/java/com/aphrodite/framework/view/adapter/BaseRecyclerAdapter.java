package com.aphrodite.framework.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.aphrodite.framework.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aphrodite on 2019/5/22.
 * RecyclerView 适配器
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected final List<T> mItems = new ArrayList<>();

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public boolean isEmpty() {
        return this.mItems.isEmpty();
    }

    public List<T> getItems() {
        return this.mItems;
    }

    public void addItems(List<T> items) {
        if (ObjectUtils.isEmpty(items))
            return;

        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(int location, List<T> items) {
        if (ObjectUtils.isEmpty(items))
            return;

        this.mItems.addAll(location, items);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        if (null == item)
            return;

        mItems.add(item);
    }

    public void setItems(List<T> items) {
        if (null == items)
            return;

        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void removeItems(List<T> items) {
        if (null == items)
            return;

        this.mItems.removeAll(items);
        notifyDataSetChanged();
    }

    public void removeItem(T item) {
        if (null == item)
            return;

        this.mItems.remove(item);
        notifyDataSetChanged();
    }

    public void clearItems() {
        this.mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public T getItem(int position) {
        return ObjectUtils.isOutOfBounds(this.mItems, position) ? null : this.mItems.get(position);
    }

    public void setItem(int position, T item) {
        if (null == mItems || mItems.size() < 1 || position >= mItems.size()) {
            return;
        }
        mItems.set(position, item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
