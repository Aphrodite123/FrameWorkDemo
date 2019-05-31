package com.aphrodite.demo.model.database.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.aphrodite.demo.model.bean.RecommendContentBean;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Aphrodite on 2019/5/31.
 */
@RealmClass
public class RecommendContentDao implements Parcelable, RealmModel {
    private String title;
    private String url;
    @PrimaryKey
    private String id;
    private int width;
    private int height;
    private int cid;

    public RecommendContentDao() {
    }

    protected RecommendContentDao(Parcel in) {
        title = in.readString();
        url = in.readString();
        id = in.readString();
        width = in.readInt();
        height = in.readInt();
        cid = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(id);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(cid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecommendContentDao> CREATOR = new Creator<RecommendContentDao>() {
        @Override
        public RecommendContentDao createFromParcel(Parcel in) {
            return new RecommendContentDao(in);
        }

        @Override
        public RecommendContentDao[] newArray(int size) {
            return new RecommendContentDao[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void copy(RecommendContentBean bean) {
        if (null == bean) {
            return;
        }
        setTitle(bean.getTitle());
        setUrl(bean.getUrl());
        setId(bean.getId());
        setWidth(bean.getWidth());
        setHeight(bean.getHeight());
        setCid(bean.getCid());
    }
}
