package com.imed.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */

public class GetConfigResult {
    @SerializedName("events")
    public List<Event> events;
}
