package com.imed.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinhnguyen.it.vn on 2017, December 13
 */
@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @SerializedName("_id")
    @ColumnInfo(name = "user_id")
    @NonNull
    public final String id;
    @SerializedName("name")
    @ColumnInfo(name = "user_name")
    public final String name;
    @SerializedName("phone")
    @ColumnInfo(name = "user_phone")
    public final String phone;
    @SerializedName("email")
    @ColumnInfo(name = "user_email")
    public final String email;
    @SerializedName("role")
    @ColumnInfo(name = "user_role")
    public final String role;

    public User(@NonNull String id, String name, String phone, String email, String role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }
}
