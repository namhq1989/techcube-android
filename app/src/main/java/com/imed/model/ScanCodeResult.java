package com.imed.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */

public class ScanCodeResult implements Parcelable {
    @SerializedName("customer")
    public Customer customer;
    @SerializedName("histories")
    public List<History> histories;

    protected ScanCodeResult(Parcel in) {
        customer = in.readParcelable(Customer.class.getClassLoader());
        histories = in.createTypedArrayList(History.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(customer, flags);
        dest.writeTypedList(histories);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScanCodeResult> CREATOR = new Creator<ScanCodeResult>() {
        @Override
        public ScanCodeResult createFromParcel(Parcel in) {
            return new ScanCodeResult(in);
        }

        @Override
        public ScanCodeResult[] newArray(int size) {
            return new ScanCodeResult[size];
        }
    };
}
