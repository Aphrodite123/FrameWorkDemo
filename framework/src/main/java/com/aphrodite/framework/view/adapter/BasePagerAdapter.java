package com.aphrodite.framework.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.aphrodite.framework.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by Aphrodite on 2019/5/27.
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected final List<T> mItems = new ArrayList<>();

    public BasePagerAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    public List<T> getItems() {
        return this.mItems;
    }

    public void addItems(List<T> items) {
        if (ObjectUtils.isEmpty(items)) {
            return;
        }

        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        if (null == item)
            return;

        mItems.add(item);
        notifyDataSetChanged();
    }

    public void addItems(int location, List<T> items) {
        if (ObjectUtils.isEmpty(items)) {
            return;
        }

        this.mItems.addAll(location, items);
        notifyDataSetChanged();
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

    public T getItem(int position) {
        return ObjectUtils.isOutOfBounds(this.mItems, position) ? null : this.mItems.get(position);
    }

}
