package com.imed.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */

public class History {
    @SerializedName("event")
    public Event event;
    @SerializedName("area")
    public Area area;
    @SerializedName("date")
    public Date date;
}
