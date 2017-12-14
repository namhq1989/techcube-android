package com.imed.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */

public class Customer {
    @SerializedName("_id")
    public String id;
    @SerializedName("name")
    public String name;
}
