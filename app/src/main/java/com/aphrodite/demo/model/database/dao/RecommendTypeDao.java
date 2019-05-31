package com.aphrodite.demo.model.database.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.aphrodite.demo.model.bean.RecommendTypeBean;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by Aphrodite on 2019/5/31.
 */
@RealmClass
public class RecommendTypeDao implements Parcelable, RealmModel {
    private String name;
    private String url;

    public RecommendTypeDao() {
    }

    protected RecommendTypeDao(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecommendTypeDao> CREATOR = new Creator<RecommendTypeDao>() {
        @Override
        public RecommendTypeDao createFromParcel(Parcel in) {
            return new RecommendTypeDao(in);
        }

        @Override
        public RecommendTypeDao[] newArray(int size) {
            return new RecommendTypeDao[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void copy(RecommendTypeBean bean) {
        if (null == bean) {
            return;
        }
        setName(bean.getName());
        setUrl(bean.getUrl());
    }

}
