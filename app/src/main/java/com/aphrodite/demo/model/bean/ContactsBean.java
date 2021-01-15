package com.aphrodite.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aphrodite on 2021/1/11.
 */
public class ContactsBean implements Parcelable {
    private String name;
    private String note;
    private String phone;

    public ContactsBean() {
    }

    protected ContactsBean(Parcel in) {
        name = in.readString();
        note = in.readString();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(note);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactsBean> CREATOR = new Creator<ContactsBean>() {
        @Override
        public ContactsBean createFromParcel(Parcel in) {
            return new ContactsBean(in);
        }

        @Override
        public ContactsBean[] newArray(int size) {
            return new ContactsBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
