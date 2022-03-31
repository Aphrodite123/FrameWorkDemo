package com.aphrodite.demo.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aphrodite.demo.BuildConfig;
import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.config.AppConfig;
import com.aphrodite.demo.model.api.RequestApi;
import com.aphrodite.demo.model.bean.RecommendContentBean;
import com.aphrodite.demo.model.database.dao.BeautyDao;
import com.aphrodite.demo.model.database.dao.RecommendContentDao;
import com.aphrodite.demo.utils.LogUtils;
import com.aphrodite.demo.view.adapter.BeautyListAdapter;
import com.aphrodite.demo.view.fragment.base.BaseFragment;
import com.aphrodite.demo.view.widget.recycleview.PullToRefreshRecyclerView;
import com.aphrodite.demo.view.widget.recycleview.decoration.SpacesItemDecoration;
import com.aphrodite.demo.view.widget.recycleview.inter.OnLoadMoreListener;
import com.aphrodite.demo.view.widget.recycleview.inter.OnRefreshListener;
import com.aphrodite.demo.view.widget.recycleview.view.FooterStyleLayout;
import com.aphrodite.framework.model.network.api.RetrofitInitial;
import com.aphrodite.framework.utils.NetworkUtils;
import com.aphrodite.framework.utils.ObjectUtils;
import com.aphrodite.framework.utils.UrlUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

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

        mFooterStyleLayout = (FooterStyleLayout) mRefreshRecyclerView.getLoadMoreFooterView();

        mBeautyListAdapter = new BeautyListAdapter(getContext());
        mRefreshRecyclerView.setContainerAdapter(mBeautyListAdapter);
    }

    @Override
    protected void initListener() {
        mRefreshRecyclerView.setOnRefreshListener(mRefreshListener);
        mRefreshRecyclerView.setOnLoadMoreListener(mLoadMoreListener);

        mRefreshRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getContext()).resumeRequests();
                } else {
                    Glide.with(getContext()).pauseRequests();
                }
            }
        });

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

        mRetrofitInit = FrameApplication.getApplication().getRetrofitInit(false, BuildConfig.SERVER_URL, null);
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
                        Glide.with(getContext())
                                .asBitmap()
                                .load(url)
                                .skipMemoryCache(true)                      //禁止Glide内存缓存
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)  //不缓存资源
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        recommendContentBean.setWidth(resource.getWidth());
                                        recommendContentBean.setHeight(resource.getHeight());
                                    }
                                });
                        return recommendContentBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendContentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mPagerOffset <= 1) {
                            deleteBeauty();
                        }
                    }

                    @Override
                    public void onNext(RecommendContentBean bean) {
                        if (null == bean) {
                            mFooterStyleLayout.setStatus(FooterStyleLayout.END);
                            return;
                        }

                        if (!mContentBeans.contains(bean)) {
                            mContentBeans.add(bean);
                        }

                        saveData(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (null != mRefreshRecyclerView) {
                            mRefreshRecyclerView.setRefreshing(false);
                        }

                        if (null != mFooterStyleLayout) {
                            mFooterStyleLayout.setStatus(FooterStyleLayout.END);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mPagerOffset <= 1) {
                            mBeautyListAdapter.setItems(mContentBeans);
                        } else {
                            mBeautyListAdapter.addItems(mContentBeans);
                        }

                        if (null != mRefreshRecyclerView) {
                            mRefreshRecyclerView.setRefreshing(false);
                        }

                        if (null != mFooterStyleLayout) {
                            mFooterStyleLayout.setStatus(FooterStyleLayout.END);
                        }
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

            String substring = null;
            int cid = 0;

            if (!TextUtils.isEmpty(mUrl) && mUrl.contains("?cid=")) {
                substring = mUrl.substring(mUrl.lastIndexOf("=") + 1);
            }

            if (!TextUtils.isEmpty(substring)) {
                cid = Integer.parseInt(substring);
            }
            contentBean.setCid(cid);

            contentBeans.add(contentBean);
        }

        return contentBeans;
    }

    private void saveData(final RecommendContentBean bean) {
        if (null == mRealm || null == bean) {
            return;
        }
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RecommendContentDao dao = new RecommendContentDao();
                dao.copy(bean);

                realm.copyToRealmOrUpdate(dao);
            }
        });
    }

    private void deleteBeauty() {
        if (null == mRealm) {
            return;
        }

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<BeautyDao> daos = mRealm.where(BeautyDao.class).findAll();
                daos.deleteAllFromRealm();
            }
        });
    }

    private OnRefreshListener mRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPagerOffset = 1;
            queryContent();
            mFooterStyleLayout.setStatus(FooterStyleLayout.GONE);
        }
    };

    private OnLoadMoreListener mLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            if (null == mFooterStyleLayout) {
                return;
            }

            if (mFooterStyleLayout.canLoadMore() && mBeautyListAdapter.getItemCount() > 0) {
                mFooterStyleLayout.setStatus(FooterStyleLayout.LOADING);
                mPagerOffset++;
                queryContent();
            }
        }
    };

}
