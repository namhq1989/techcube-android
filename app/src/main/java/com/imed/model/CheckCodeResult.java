package com.imed.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */

public class CheckCodeResult {
    @SerializedName("areas")
    public List<Area> areas;
    @SerializedName("currentArea")
    public String currentArea;
    @SerializedName("plan")
    public Plan plan;
    @SerializedName("customer")
    public Customer customer;
    @SerializedName("event")
    public Event event;
}
