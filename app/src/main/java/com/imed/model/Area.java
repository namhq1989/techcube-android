package com.imed.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */
@Entity(tableName = "area")
public class Area {
    @PrimaryKey
    @SerializedName("_id")
    @ColumnInfo(name = "area_id")
    @NonNull
    public final String id;
    @SerializedName("name")
    @ColumnInfo(name = "area_name")
    public final String name;
    @SerializedName("startAt")
    @ColumnInfo(name = "area_start_at")
    public final Date startAt;
    @SerializedName("endAt")
    @ColumnInfo(name = "area_end_at")
    public final Date endAt;
    @SerializedName("numOfCheckin")
    @ColumnInfo(name = "area_number_of_checkin")
    public final int numOfCheckin;

    public Area(@NonNull String id, String name, Date startAt, Date endAt, int numOfCheckin) {
        this.id = id;
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.numOfCheckin = numOfCheckin;
    }
}
