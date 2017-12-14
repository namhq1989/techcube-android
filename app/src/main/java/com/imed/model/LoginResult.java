package com.imed.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinhnguyen.it.vn on 2017, December 13
 */

public class LoginResult {
    @SerializedName("user")
    public User user;
    @SerializedName("token")
    public String token;
}
