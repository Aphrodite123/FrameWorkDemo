package com.aphrodite.demo.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;

import com.aphrodite.demo.BuildConfig;
import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.config.AppConfig;
import com.aphrodite.demo.model.api.RequestApi;
import com.aphrodite.demo.model.bean.RecommendContentBean;
import com.aphrodite.demo.model.bean.RecommendTypeBean;
import com.aphrodite.demo.model.database.dao.RecommendContentDao;
import com.aphrodite.demo.model.database.dao.RecommendTypeDao;
import com.aphrodite.demo.utils.LogUtils;
import com.aphrodite.demo.view.adapter.BeautyListAdapter;
import com.aphrodite.demo.view.fragment.base.BaseFragment;
import com.aphrodite.demo.view.widget.recycleview.PullToRefreshRecyclerView;
import com.aphrodite.demo.view.widget.recycleview.decoration.SpacesItemDecoration;
import com.aphrodite.demo.view.widget.recycleview.view.FooterStyleLayout;
import com.aphrodite.framework.model.network.api.RetrofitInitial;
import com.aphrodite.framework.utils.NetworkUtils;
import com.aphrodite.framework.utils.ObjectUtils;
import com.aphrodite.framework.utils.UrlUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Aphrodite on 2019/5/31.
 */
public class RecommendListFragment extends BaseFragment {
    @BindView(R.id.recommend_list_irv)
    PullToRefreshRecyclerView mRefreshRecyclerView;

    private FooterStyleLayout mFooterStyleLayout;

    private Realm mRealm;

    private RetrofitInitial mRetrofitInit;
    private RequestApi mRequestApi;
    private String mUrl;

    private int mPagerOffset = 1;

    private List<RecommendContentBean> mContentBeans;

    private BeautyListAdapter mBeautyListAdapter;

    @Override
    protected int getViewId() {
        return R.layout.fragment_recommend_list;
    }

    @Override
    protected void initView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRefreshRecyclerView.setLayoutManager(layoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        mRefreshRecyclerView.addItemDecoration(decoration);

        mBeautyListAdapter = new BeautyListAdapter(getContext());
        mRefreshRecyclerView.setContainerAdapter(mBeautyListAdapter);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        try {
            mRealm = FrameApplication.getApplication().getGlobalRealm();
        } catch (FileNotFoundException e) {
            LogUtils.e("Enter initData method.FileNotFoundException: " + e);
        }

        Bundle bundle = getArguments();
        if (!ObjectUtils.isEmpty(bundle)) {
            mUrl = bundle.getString(AppConfig.IntentKey.RECOMMEND_PAGE_URL);
        }

        mRetrofitInit = FrameApplication.getApplication().getRetrofitInit(false, BuildConfig.SERVER_URL);
        mRequestApi = mRetrofitInit.getRetrofit().create(RequestApi.class);

        queryContent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mRealm) {
            mRealm.close();
        }
    }

    private void queryContent() {
        if (!NetworkUtils.isNetworkAvailable(getContext()) || null == mRequestApi) {
            dismissLoadingDialog();
            return;
        }

        mContentBeans = new ArrayList<>();

        ObservableTransformer transformer = new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

        mRequestApi.queryMore(mUrl, mPagerOffset)
                .compose(transformer)
                .map(new Function<String, List<RecommendContentBean>>() {
                    @Override
                    public List<RecommendContentBean> apply(String result) throws Exception {
                        return parseTypes(result);
                    }
                })
                .flatMap(new Function<List<RecommendContentBean>, ObservableSource<RecommendContentBean>>() {
                    @Override
                    public ObservableSource<RecommendContentBean> apply(List<RecommendContentBean> recommendContentBeans) throws Exception {
                        return Observable.fromIterable(recommendContentBeans);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<RecommendContentBean, RecommendContentBean>() {
                    @Override
                    public RecommendContentBean apply(RecommendContentBean recommendContentBean) throws Exception {
                        if (null == recommendContentBean) {
                            return null;
                        }

                        String url = recommendContentBean.getUrl().trim();
                        if (TextUtils.isEmpty(url) || !UrlUtils.checkUrl(url)) {
                            return null;
                        }

                        Bitmap bitmap = null;

                        try {
                            bitmap = Glide.with(getContext())
                                    .load(url)
                                    .asBitmap()
                                    .crossFade()
                                    .skipMemoryCache(true)
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                            if (null != bitmap) {
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();
                                recommendContentBean.setWidth(width);
                                recommendContentBean.setHeight(height);
                            }
                        } catch (ExecutionException e) {
                            LogUtils.e("Enter queryBeauty method." + e);
                            return recommendContentBean;
                        } finally {
                            bitmap.recycle();
                            bitmap = null;
                        }
                        return recommendContentBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendContentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RecommendContentBean bean) {
                        if (!mContentBeans.contains(bean)) {
                            mContentBeans.add(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("Enter onError method." + e);
                    }

                    @Override
                    public void onComplete() {
                        mBeautyListAdapter.setItems(mContentBeans);
                        saveData();
                    }
                });
    }

    private List<RecommendContentBean> parseTypes(String response) {
        Document document = Jsoup.parse(response);
        Elements elements = document.select("div[class=img_single] > a");
        List<RecommendContentBean> contentBeans = new ArrayList<>();
        for (Element element : elements) {
            if (null == element) {
                continue;
            }

            RecommendContentBean contentBean = new RecommendContentBean();
            contentBean.setId(element.attr("href").substring(element.attr("href").lastIndexOf("/") + 1));
            contentBean.setTitle(element.select("img").attr("title"));
            contentBean.setUrl(element.select("img").attr("src"));
            String substring = mUrl.substring(mUrl.lastIndexOf("=") + 1);
            int cid = 0;
            if (!TextUtils.isEmpty(substring)) {
                cid = Integer.parseInt(substring);
            }
            contentBean.setCid(cid);

            contentBeans.add(contentBean);
        }

        return contentBeans;
    }

    private void saveData() {
        if (ObjectUtils.isEmpty(mContentBeans) || null == mRealm) {
            return;
        }
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (RecommendContentBean bean : mContentBeans) {
                    if (null == bean) {
                        continue;
                    }

                    RecommendContentDao dao = new RecommendContentDao();
                    dao.copy(bean);

                    realm.copyToRealmOrUpdate(dao);
                }
            }
        });
    }

}