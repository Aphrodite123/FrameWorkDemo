package com.aphrodite.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aphrodite on 2019/5/30.
 */
public class RecommendTypeBean implements Parcelable {
    private String name;
    private String url;

    public RecommendTypeBean() {
    }

    protected RecommendTypeBean(Parcel in) {
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

    public static final Creator<RecommendTypeBean> CREATOR = new Creator<RecommendTypeBean>() {
        @Override
        public RecommendTypeBean createFromParcel(Parcel in) {
            return new RecommendTypeBean(in);
        }

        @Override
        public RecommendTypeBean[] newArray(int size) {
            return new RecommendTypeBean[size];
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
}
