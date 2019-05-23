package com.aphrodite.demo.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;

import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.model.api.RequestApi;
import com.aphrodite.demo.model.bean.BeautyBean;
import com.aphrodite.demo.model.network.BeautyResponse;
import com.aphrodite.demo.view.activity.base.BaseActivity;
import com.aphrodite.demo.view.adapter.BeautyListAdapter;
import com.aphrodite.demo.view.widget.recycleview.PullToRefreshRecyclerView;
import com.aphrodite.demo.view.widget.recycleview.decoration.SpacesItemDecoration;
import com.aphrodite.demo.view.widget.recycleview.inter.OnLoadMoreListener;
import com.aphrodite.demo.view.widget.recycleview.inter.OnRefreshListener;
import com.aphrodite.demo.view.widget.recycleview.view.FooterStyleLayout;
import com.aphrodite.framework.utils.NetworkUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.beauty_list_irv)
    PullToRefreshRecyclerView mRefreshRecyclerView;

    private FooterStyleLayout mFooterStyleLayout;
    private BeautyListAdapter mBeautyListAdapter;

    private RequestApi mRequestApi;

    private int mDefaultCount = 20;
    private int mPageNo = 1;

    List<BeautyBean> mBeautyBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRefreshRecyclerView.setLayoutManager(layoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        mRefreshRecyclerView.addItemDecoration(decoration);

        mFooterStyleLayout = (FooterStyleLayout) mRefreshRecyclerView.getLoadMoreFooterView();

        mBeautyListAdapter = new BeautyListAdapter(this);
        mRefreshRecyclerView.setContainerAdapter(mBeautyListAdapter);
    }

    @Override
    protected void initListener() {
        mRefreshRecyclerView.setOnRefreshListener(mRefreshListener);
        mRefreshRecyclerView.setOnLoadMoreListener(mLoadMoreListener);
    }

    @Override
    protected void initData() {
        showLoadingDialog();

        mRequestApi = FrameApplication.getRetrofitInit().getRetrofit().create(RequestApi.class);
        queryBeauty();
    }

    private void queryBeauty() {
        if (!NetworkUtils.isNetworkAvailable(this) || null == mRequestApi) {
            dismissLoadingDialog();
            return;
        }

        mBeautyBeans = new ArrayList<>();

        ObservableTransformer transformer = new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

        mRequestApi.queryBeauty(mDefaultCount, mPageNo)
                .compose(transformer)
                .map(new Function<BeautyResponse, List<BeautyBean>>() {
                    @Override
                    public List<BeautyBean> apply(BeautyResponse beautyResponse) throws Exception {
                        if (null == beautyResponse) {
                            return null;
                        }
                        return beautyResponse.getResults();
                    }
                })
                .flatMap(new Function<List<BeautyBean>, ObservableSource<BeautyBean>>() {
                    @Override
                    public ObservableSource<BeautyBean> apply(List<BeautyBean> beautyBeans) throws Exception {
                        return Observable.fromIterable(beautyBeans);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<BeautyBean, BeautyBean>() {
                    @Override
                    public BeautyBean apply(BeautyBean beautyBean) throws Exception {
                        if (null == beautyBean) {
                            return null;
                        }

                        String url = beautyBean.getUrl();
                        if (TextUtils.isEmpty(url)) {
                            return null;
                        }

                        Bitmap bitmap = null;

                        try {
                            bitmap = Glide.with(MainActivity.this)
                                    .load(url)
                                    .asBitmap()
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                            if (null != bitmap) {
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();
                                beautyBean.setWidth(width);
                                beautyBean.setHeight(height);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            bitmap.recycle();
                            bitmap = null;
                        }
                        return beautyBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BeautyBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mPageNo <= 1) {
                            mBeautyBeans.clear();
                        }
                    }

                    @Override
                    public void onNext(BeautyBean bean) {
                        if (null == bean) {
                            mFooterStyleLayout.setStatus(FooterStyleLayout.END);
                            return;
                        }

                        if (!mBeautyBeans.contains(bean)) {
                            mBeautyBeans.add(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRefreshRecyclerView.setRefreshing(false);
                        mFooterStyleLayout.setStatus(FooterStyleLayout.END);

                        dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
                        mBeautyListAdapter.addItems(mBeautyBeans);

                        mRefreshRecyclerView.setRefreshing(false);
                        mFooterStyleLayout.setStatus(FooterStyleLayout.END);

                        dismissLoadingDialog();
                    }
                });
    }

    private OnRefreshListener mRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPageNo = 1;
            queryBeauty();
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
                mPageNo++;
                queryBeauty();
            }
        }
    };

}
