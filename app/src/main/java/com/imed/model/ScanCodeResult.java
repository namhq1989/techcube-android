package com.imed.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */

public class ScanCodeResult {
    @SerializedName("customer")
    public Customer customer;
    @SerializedName("histories")
    public List<History> histories;
}
