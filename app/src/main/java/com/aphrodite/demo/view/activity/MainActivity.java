package com.aphrodite.demo.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.model.api.RequestApi;
import com.aphrodite.demo.model.bean.BeautyBean;
import com.aphrodite.demo.model.database.dao.BeautyDao;
import com.aphrodite.demo.model.network.BeautyResponse;
import com.aphrodite.demo.utils.LogUtils;
import com.aphrodite.demo.view.activity.base.BaseActivity;
import com.aphrodite.demo.view.adapter.BeautyListAdapter;
import com.aphrodite.demo.view.widget.recycleview.PullToRefreshRecyclerView;
import com.aphrodite.demo.view.widget.recycleview.decoration.SpacesItemDecoration;
import com.aphrodite.demo.view.widget.recycleview.inter.OnLoadMoreListener;
import com.aphrodite.demo.view.widget.recycleview.inter.OnRefreshListener;
import com.aphrodite.demo.view.widget.recycleview.view.FooterStyleLayout;
import com.aphrodite.framework.utils.NetworkUtils;
import com.aphrodite.framework.utils.UrlUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

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

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.beauty_list_irv)
    PullToRefreshRecyclerView mRefreshRecyclerView;

    private FooterStyleLayout mFooterStyleLayout;
    private BeautyListAdapter mBeautyListAdapter;

    private Realm mRealm;

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
        setTitleText(R.string.beauty_title);

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
        try {
            mRealm = FrameApplication.getApplication().getGlobalRealm();
        } catch (FileNotFoundException e) {
            LogUtils.e("Enter loadLocalData method.FileNotFoundException: " + e);
        }
        mRequestApi = FrameApplication.getRetrofitInit().getRetrofit().create(RequestApi.class);

        showLoadingDialog();
        queryBeauty();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mRealm) {
            mRealm.close();
        }
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

                        String url = beautyBean.getUrl().trim();
                        if (TextUtils.isEmpty(url) || !UrlUtils.checkUrl(url)) {
                            return null;
                        }

                        Bitmap bitmap = null;

                        try {
                            bitmap = Glide.with(MainActivity.this)
                                    .load(url)
                                    .asBitmap()
                                    .crossFade()
                                    .skipMemoryCache(true)
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                            if (null != bitmap) {
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();
                                beautyBean.setWidth(width);
                                beautyBean.setHeight(height);
                            }
                        } catch (ExecutionException e) {
                            LogUtils.e("Enter queryBeauty method." + e);
                            return beautyBean;
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
                            if (null != mRealm) {
                                deleteBeauty();
                            }
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

                        saveBeauty(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRefreshRecyclerView.setRefreshing(false);
                        mFooterStyleLayout.setStatus(FooterStyleLayout.END);

                        dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
                        if (mPageNo <= 1) {
                            mBeautyListAdapter.setItems(mBeautyBeans);
                        } else {
                            mBeautyListAdapter.addItems(mBeautyBeans);
                        }
                        mRefreshRecyclerView.setRefreshing(false);
                        mFooterStyleLayout.setStatus(FooterStyleLayout.END);

                        dismissLoadingDialog();
                    }
                });
    }

    private void saveBeauty(final BeautyBean bean) {
        if (null == bean || null == mRealm) {
            return;
        }

        if (null == mRealm) {
            return;
        }

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                BeautyDao beautyDao = new BeautyDao();
                beautyDao.copy(bean);

                realm.copyToRealmOrUpdate(beautyDao);
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
