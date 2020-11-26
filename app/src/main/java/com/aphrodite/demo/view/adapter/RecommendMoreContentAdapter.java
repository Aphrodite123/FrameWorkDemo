package com.aphrodite.demo.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.aphrodite.demo.view.fragment.base.BaseFragment;
import com.aphrodite.framework.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Aphrodite on 2019/5/31.
 */
public class RecommendMoreContentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mFragments;

    public RecommendMoreContentAdapter(FragmentManager fm) {
        super(fm);
        this.mFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return ObjectUtils.isEmpty(mFragments) ? null : mFragments.get(position);
    }

    @Override
    public int getCount() {
        return ObjectUtils.isEmpty(mFragments) ? 0 : mFragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    public void setItems(List<BaseFragment> fragments) {
        if (null != mFragments) {
            mFragments.clear();
        }

        if (!ObjectUtils.isEmpty(fragments)) {
            mFragments.addAll(fragments);
        }

        notifyDataSetChanged();
    }
}
