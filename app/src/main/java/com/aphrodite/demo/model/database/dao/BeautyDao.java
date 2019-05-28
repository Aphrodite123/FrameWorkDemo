package com.aphrodite.demo.model.database.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.aphrodite.demo.model.bean.BeautyBean;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Aphrodite on 2019/5/28.
 */
@RealmClass
public class BeautyDao implements Parcelable, RealmModel {
    @PrimaryKey
    private String id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String url;
    private Boolean used;
    private String who;
    private int width;
    private int height;

    public BeautyDao() {
    }

    protected BeautyDao(Parcel in) {
        id = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        source = in.readString();
        url = in.readString();
        byte tmpUsed = in.readByte();
        used = tmpUsed == 0 ? null : tmpUsed == 1;
        who = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(source);
        dest.writeString(url);
        dest.writeByte((byte) (used == null ? 0 : used ? 1 : 2));
        dest.writeString(who);
        dest.writeInt(width);
        dest.writeInt(height);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeautyDao> CREATOR = new Creator<BeautyDao>() {
        @Override
        public BeautyDao createFromParcel(Parcel in) {
            return new BeautyDao(in);
        }

        @Override
        public BeautyDao[] newArray(int size) {
            return new BeautyDao[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
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

    public void copy(BeautyBean bean) {
        if (null == bean) {
            return;
        }

        setId(bean.get_id());
        setCreatedAt(bean.getCreatedAt());
        setDesc(bean.getDesc());
        setPublishedAt(bean.getPublishedAt());
        setSource(bean.getSource());
        setUrl(bean.getUrl());
        setUsed(bean.getUsed());
        setWho(bean.getWho());
        setWidth(bean.getWidth());
        setHeight(bean.getHeight());
    }
}
