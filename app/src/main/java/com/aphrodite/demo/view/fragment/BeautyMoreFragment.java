package com.aphrodite.demo.view.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;

import com.aphrodite.demo.BuildConfig;
import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.config.HttpConfig;
import com.aphrodite.demo.model.api.RequestApi;
import com.aphrodite.demo.model.bean.RecommendTypeBean;
import com.aphrodite.demo.utils.LogUtils;
import com.aphrodite.demo.view.adapter.RecommendMoreTabAdapter;
import com.aphrodite.demo.view.fragment.base.BaseFragment;
import com.aphrodite.demo.view.widget.recycleview.HorizontalRecycleView;
import com.aphrodite.framework.model.network.api.RetrofitInitial;
import com.aphrodite.framework.utils.NetworkUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aphrodite on 2019/5/29.
 */
public class BeautyMoreFragment extends BaseFragment {
    @BindView(R.id.beauty_more_type)
    HorizontalRecycleView mHorizontalRecycleView;
    @BindView(R.id.beauty_page)
    ViewPager mViewPager;

    private RetrofitInitial mRetrofitInit;
    private RequestApi mRequestApi;

    private RecommendMoreTabAdapter moreTabAdapter;

    private int mPagerOffset = 1;

    private List<RecommendTypeBean> mTypeBeans;

    @Override
    protected int getViewId() {
        return R.layout.fragment_beauty_more;
    }

    @Override
    protected void initView() {
        setTitleText(R.string.recommend_more);

        mHorizontalRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moreTabAdapter = new RecommendMoreTabAdapter(getContext(), RecommendMoreTabAdapter.MODE_FIX, mTabItemClickListener);
        mHorizontalRecycleView.setAdapter(moreTabAdapter);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        mRetrofitInit = FrameApplication.getApplication().getRetrofitInit(false, BuildConfig.SERVER_URL);
        mRequestApi = mRetrofitInit.getRetrofit().create(RequestApi.class);
        queryBeautyMore();
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("Enter onError method." + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void parseTypes(String response) {
        Document document = Jsoup.parse(response);
        Elements elements = document.select("a[href~=^https://www.dbmeinv.com/index.htm]");
        mTypeBeans = new ArrayList<>();
        for (Element element : elements) {
            if (null == element) {
                continue;
            }

            RecommendTypeBean typeBean = new RecommendTypeBean();
            typeBean.setName(element.childNode(0).toString());
            typeBean.setUrl(element.attr("href").toString());

            mTypeBeans.add(typeBean);
        }
    }

    private RecommendMoreTabAdapter.OnItemClickListener mTabItemClickListener = new RecommendMoreTabAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            mViewPager.setCurrentItem(position);
        }
    };

}
