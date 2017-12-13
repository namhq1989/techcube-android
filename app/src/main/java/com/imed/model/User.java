package com.imed.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinhnguyen.it.vn on 2017, December 13
 */
public class User {
    @SerializedName("_id")
    @NonNull
    public final String id;
    @SerializedName("name")
    public final String name;
    @SerializedName("phone")
    public final String phone;
    @SerializedName("email")
    public final String email;
    @SerializedName("role")
    public final String role;

    public User(@NonNull String id, String name, String phone, String email, String role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }
}
