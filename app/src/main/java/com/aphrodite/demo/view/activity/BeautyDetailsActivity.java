package com.aphrodite.demo.view.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.config.AppConfig;
import com.aphrodite.demo.config.IntentAction;
import com.aphrodite.demo.model.bean.BeautyBean;
import com.aphrodite.demo.model.bean.RecommendContentBean;
import com.aphrodite.demo.model.database.dao.BeautyDao;
import com.aphrodite.demo.model.database.dao.RecommendContentDao;
import com.aphrodite.demo.utils.LogUtils;
import com.aphrodite.demo.view.activity.base.BaseActivity;
import com.aphrodite.demo.view.adapter.BeautyPagerAdapter;
import com.aphrodite.demo.view.widget.viewpager.TouchViewPager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Aphrodite on 2019/5/27.
 */
public class BeautyDetailsActivity extends BaseActivity {
    @BindView(R.id.beauty_details_index)
    TextView mBeautyIndex;
    @BindView(R.id.beauty_details_total)
    TextView mBeautyTotal;
    @BindView(R.id.beauty_details_vp)
    TouchViewPager mViewPager;

    private Realm mRealm;

    private int mSourceType;
    private String mId;
    private String mUrl;
    private int mCid = -1;
    private int mCurPage;

    private BeautyPagerAdapter mPagerAdapter;

    @Override
    protected int getViewId() {
        return R.layout.activity_beauty_details;
    }

    @Override
    protected void initView() {
        mPagerAdapter = new BeautyPagerAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    protected void initListener() {
        mViewPager.addOnPageChangeListener(mPageChangeListener);
    }

    @Override
    protected void initData() {
        try {
            mRealm = FrameApplication.getApplication().getGlobalRealm();
        } catch (FileNotFoundException e) {
            LogUtils.e("Enter loadLocalData method.FileNotFoundException: " + e);
        }

        Intent intent = getIntent();
        if (null != intent) {
            mSourceType = intent.getIntExtra(IntentAction.BeautyDetailsAction.TYPE, mSourceType);
            mId = intent.getStringExtra(IntentAction.BeautyDetailsAction.ID);
            mUrl = intent.getStringExtra(IntentAction.BeautyDetailsAction.URL);
            mCid = intent.getIntExtra(IntentAction.BeautyDetailsAction.CID, mCid);
        }

        loadLocalData();
    }

    private void loadLocalData() {
        if (null == mRealm) {
            return;
        }

        switch (mSourceType) {
            case AppConfig.SourceType.BEAUTY:
                RealmResults<BeautyDao> daos = mRealm.where(BeautyDao.class).findAll();
                if (null != daos) {
                    List<BeautyBean> beans = new ArrayList<>();
                    for (int i = 0; i < daos.size(); i++) {
                        BeautyDao dao = daos.get(i);
                        if (null == dao) {
                            continue;
                        }

                        BeautyBean bean = new BeautyBean();
                        bean.copy(dao);

                        beans.add(bean);

                        if (mId.equals(bean.get_id())) {
                            mCurPage = i;
                        }
                    }
                    mPagerAdapter.setItems(beans);
                    mViewPager.setCurrentItem(mCurPage);
                    updateIndex();
                }
                break;
            case AppConfig.SourceType.RECOMMEND:
                RealmResults<RecommendContentDao> contentDaos = mRealm
                        .where(RecommendContentDao.class)
                        .equalTo("cid", mCid)
                        .findAll();
                if (null != contentDaos) {
                    List<RecommendContentBean> beans = new ArrayList<>();
                    for (int i = 0; i < contentDaos.size(); i++) {
                        RecommendContentDao dao = contentDaos.get(i);
                        if (null == dao) {
                            continue;
                        }

                        RecommendContentBean bean = new RecommendContentBean();
                        bean.copy(dao);

                        beans.add(bean);

                        if (mId.equals(bean.getId())) {
                            mCurPage = i;
                        }
                    }
                    mPagerAdapter.setItems(beans);
                    mViewPager.setCurrentItem(mCurPage);
                    updateIndex();
                }
                break;
            default:
                break;
        }
    }

    private void updateIndex() {
        mBeautyIndex.setText(String.valueOf(mCurPage + 1));
        StringBuffer buffer = new StringBuffer();
        if (null != mPagerAdapter) {
            buffer.append(AppConfig.SLASH).append(mPagerAdapter.getCount());
        }
        mBeautyTotal.setText(buffer.toString());
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurPage = position;
            updateIndex();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
