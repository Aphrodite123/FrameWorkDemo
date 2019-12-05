package com.aphrodite.demo.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;

import com.aphrodite.demo.BuildConfig;
import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.config.AppConfig;
import com.aphrodite.demo.config.HttpConfig;
import com.aphrodite.demo.model.api.RequestApi;
import com.aphrodite.demo.model.bean.RecommendTypeBean;
import com.aphrodite.demo.model.database.dao.RecommendTypeDao;
import com.aphrodite.demo.utils.LogUtils;
import com.aphrodite.demo.view.adapter.RecommendMoreContentAdapter;
import com.aphrodite.demo.view.adapter.RecommendMoreTabAdapter;
import com.aphrodite.demo.view.fragment.base.BaseFragment;
import com.aphrodite.demo.view.widget.recycleview.HorizontalRecycleView;
import com.aphrodite.framework.model.network.api.RetrofitInitial;
import com.aphrodite.framework.utils.NetworkUtils;
import com.aphrodite.framework.utils.ObjectUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Aphrodite on 2019/5/29.
 */
public class BeautyMoreFragment extends BaseFragment {
    @BindView(R.id.beauty_more_type)
    HorizontalRecycleView mHorizontalRecycleView;
    @BindView(R.id.beauty_page)
    ViewPager mViewPager;

    private Realm mRealm;

    private RetrofitInitial mRetrofitInit;
    private RequestApi mRequestApi;

    private RecommendMoreTabAdapter moreTabAdapter;

    private int mPagerOffset = 1;

    private List<RecommendTypeBean> mTypeBeans;

    private RecommendMoreContentAdapter moreContentAdapter;
    private List<BaseFragment> mFragmentList;

    @Override
    protected int getViewId() {
        return R.layout.fragment_beauty_more;
    }

    @Override
    protected void initView() {
        setTitleText(R.string.recommend_more);
        setTitleColor(Color.BLACK);

        mHorizontalRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moreTabAdapter = new RecommendMoreTabAdapter(getContext(), RecommendMoreTabAdapter.MODE_FIX, mTabItemClickListener);
        mHorizontalRecycleView.setAdapter(moreTabAdapter);
        moreContentAdapter = new RecommendMoreContentAdapter(getFragmentManager());
        mViewPager.setAdapter(moreContentAdapter);
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
            LogUtils.e("Enter initData method.FileNotFoundException: " + e);
        }

        mRetrofitInit = FrameApplication.getApplication().getRetrofitInit(false, BuildConfig.SERVER_URL, null);
        mRequestApi = mRetrofitInit.getRetrofit().create(RequestApi.class);
        mFragmentList = new ArrayList<>();

        showLoadingDialog();
        queryBeautyMore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mRealm) {
            mRealm.close();
        }
    }

    private void queryBeautyMore() {
        if (!NetworkUtils.isNetworkAvailable(getContext()) || null == mRequestApi) {
            dismissLoadingDialog();
            return;
        }

        mRequestApi.queryMore(HttpConfig.URL_MORE_BEAUTY, mPagerOffset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String results) {
                        parseTypes(results);
                        moreTabAdapter.setItems(mTypeBeans);
                        updateContentPages();
                        saveData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("Enter onError method." + e);
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadingDialog();
                    }
                });
    }

    private void parseTypes(String response) {
        Document document = Jsoup.parse(response);
        Elements elements = document.select("ul[class=nav nav-pills]");
        if (ObjectUtils.isEmpty(elements)) {
            return;
        }

        Elements liElements = elements.select("li");
        if (ObjectUtils.isEmpty(liElements)) {
            return;
        }

        mTypeBeans = new ArrayList<>();
        Elements hrefElements = null;
        for (Element element : liElements) {
            if (ObjectUtils.isEmpty(element)) {
                continue;
            }

            hrefElements = element.select("a[href~=^https://www.buxiuse.com]");
            if (ObjectUtils.isEmpty(hrefElements)) {
                continue;
            }

            RecommendTypeBean typeBean = new RecommendTypeBean();
            typeBean.setName(hrefElements.get(0).text());
            typeBean.setUrl(hrefElements.attr("href").toString());

            mTypeBeans.add(typeBean);
        }
    }

    private void updateContentPages() {
        if (ObjectUtils.isEmpty(mTypeBeans)) {
            return;
        }

        mFragmentList.clear();

        for (RecommendTypeBean typeBean : mTypeBeans) {
            if (ObjectUtils.isEmpty(typeBean)) {
                continue;
            }

            RecommendListFragment categoryFragment = new RecommendListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConfig.IntentKey.RECOMMEND_PAGE_URL, typeBean.getUrl());
            categoryFragment.setArguments(bundle);
            mFragmentList.add(categoryFragment);
        }

        moreContentAdapter.setItems(mFragmentList);
    }

    private void saveData() {
        if (ObjectUtils.isEmpty(mTypeBeans) || null == mRealm) {
            return;
        }

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RecommendTypeDao> daos = mRealm.where(RecommendTypeDao.class).findAll();
                daos.deleteAllFromRealm();


                for (RecommendTypeBean bean : mTypeBeans) {
                    if (null == bean) {
                        continue;
                    }

                    RecommendTypeDao dao = new RecommendTypeDao();
                    dao.copy(bean);

                    realm.copyToRealm(dao);
                }
            }
        });
    }

    private RecommendMoreTabAdapter.OnItemClickListener mTabItemClickListener = new RecommendMoreTabAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            mViewPager.setCurrentItem(position);
        }
    };

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            LinearLayoutManager manager = ((LinearLayoutManager) mHorizontalRecycleView.getLayoutManager());
            int firstPosition = manager.findFirstCompletelyVisibleItemPosition();
            int lastPosition = manager.findLastCompletelyVisibleItemPosition();

            if (position < firstPosition) {
                mHorizontalRecycleView.smoothScrollToPosition(position);
            } else if (position > lastPosition) {
                mHorizontalRecycleView.smoothScrollToPosition(position);
            }

            moreTabAdapter.setCurrentPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
