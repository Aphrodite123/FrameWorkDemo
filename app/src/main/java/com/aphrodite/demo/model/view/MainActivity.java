package com.aphrodite.demo.model.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aphrodite.demo.R;
import com.aphrodite.demo.application.FrameApplication;
import com.aphrodite.demo.model.api.RequestApi;
import com.aphrodite.demo.model.bean.GankResult;
import com.aphrodite.framework.utils.NetworkUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mBtn;

    private RequestApi mRequestApi;

    private int mDefaultCount = 20;
    private int mCurPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        mBtn = findViewById(R.id.welcome_btn);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryBeauty();
            }
        });
    }

    private void initData() {
        mRequestApi = FrameApplication.getRetrofitInit().getRetrofit().create(RequestApi.class);
    }

    private void queryBeauty() {
        if (!NetworkUtils.isNetworkAvailable(this) || null == mRequestApi) {
            return;
        }

        mRequestApi.fetchGankMZ(mDefaultCount, mCurPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GankResult gankResult) {
                        if (null == gankResult) {
                            return;
                        }
                    }
                });
    }
}
