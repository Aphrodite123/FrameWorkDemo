package com.aphrodite.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.aphrodite.demo.model.database.dao.RecommendContentDao;

/**
 * Created by Aphrodite on 2019/5/31.
 */
public class RecommendContentBean implements Parcelable {
    private String title;
    private String url;
    private String id;
    private int width;
    private int height;
    private int cid;

    public RecommendContentBean() {
    }

    protected RecommendContentBean(Parcel in) {
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

    public static final Creator<RecommendContentBean> CREATOR = new Creator<RecommendContentBean>() {
        @Override
        public RecommendContentBean createFromParcel(Parcel in) {
            return new RecommendContentBean(in);
        }

        @Override
        public RecommendContentBean[] newArray(int size) {
            return new RecommendContentBean[size];
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

    public void copy(RecommendContentDao dao) {
        if (null == dao) {
            return;
        }
        setTitle(dao.getTitle());
        setUrl(dao.getUrl());
        setId(dao.getId());
        setWidth(dao.getWidth());
        setHeight(dao.getHeight());
        setCid(dao.getCid());
    }

}
