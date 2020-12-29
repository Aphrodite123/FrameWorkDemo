package com.aphrodite.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.aphrodite.demo.model.database.dao.BeautyDao;

/**
 * 描述：Gank.io 返回的妹子
 *
 * @author CoderPig on 2018/02/14 10:53.
 */

public class BeautyBean implements Parcelable {
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String url;
    private Boolean used;
    private String who;
    private int width;
    private int height;
    private float scale;

    public BeautyBean() {
    }

    protected BeautyBean(Parcel in) {
        _id = in.readString();
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
        scale = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(source);
        dest.writeString(url);
        dest.writeByte((byte) (used == null ? 0 : used ? 1 : 2));
        dest.writeString(who);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeFloat(scale);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeautyBean> CREATOR = new Creator<BeautyBean>() {
        @Override
        public BeautyBean createFromParcel(Parcel in) {
            return new BeautyBean(in);
        }

        @Override
        public BeautyBean[] newArray(int size) {
            return new BeautyBean[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void copy(BeautyDao dao) {
        if (null == dao) {
            return;
        }

        set_id(dao.getId());
        setCreatedAt(dao.getCreatedAt());
        setDesc(dao.getDesc());
        setPublishedAt(dao.getPublishedAt());
        setSource(dao.getSource());
        setUrl(dao.getUrl());
        setUsed(dao.getUsed());
        setWho(dao.getWho());
        setWidth(dao.getWidth());
        setHeight(dao.getHeight());
        setScale(dao.getScale());
    }
}
