package com.aphrodite.demo.model.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.model.api.RequestApi;
import com.aphrodite.demo.model.bean.BeautyBean;
import com.aphrodite.demo.model.network.BeautyResponse;
import com.aphrodite.demo.view.activity.base.BaseActivity;
import com.aphrodite.framework.utils.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.welcome_btn)
    Button mBtn;

    private RequestApi mRequestApi;

    private int mDefaultCount = 20;
    private int mCurPage = 1;

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
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryBeauty();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mRequestApi = FrameApplication.getRetrofitInit().getRetrofit().create(RequestApi.class);
    }

    private void queryBeauty() {
        if (!NetworkUtils.isNetworkAvailable(this) || null == mRequestApi) {
            return;
        }

        mRequestApi.queryBeauty(mDefaultCount, mCurPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BeautyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BeautyResponse response) {
                        if (null == response) {
                            return;
                        }

                        List<BeautyBean> beautyBeans = response.getResults();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
