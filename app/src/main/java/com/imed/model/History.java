package com.imed.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */

public class History implements Parcelable {
    @SerializedName("event")
    public Event event;
    @SerializedName("date")
    public String date;

    protected History(Parcel in) {
        event = in.readParcelable(Event.class.getClassLoader());
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(event, flags);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };
}
