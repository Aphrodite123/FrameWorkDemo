package com.aphrodite.demo.view.fragment;

import android.graphics.Color;

import com.aphrodite.demo.R;
import com.aphrodite.demo.utils.ContactsUtils;
import com.aphrodite.demo.utils.LogUtils;
import com.aphrodite.demo.view.fragment.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by Aphrodite on 2021/1/11.
 */
public class UserFragment extends BaseFragment {
    @Override
    protected int getViewId() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void initView() {
        setTitleText(R.string.user_page);
        setTitleColor(Color.BLACK);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.obtain_contacts)
    public void obtainContacts() {
        LogUtils.i("Query contacts. " + ContactsUtils.getAllContacts(getContext()));
    }

}
