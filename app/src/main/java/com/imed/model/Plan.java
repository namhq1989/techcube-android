package com.imed.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */
@Entity(tableName = "event_plan")
public class Plan {
    @PrimaryKey
    @SerializedName("_id")
    @ColumnInfo(name = "plan_id")
    @NonNull
    public final String id;
    @SerializedName("name")
    @ColumnInfo(name = "plan_name")
    public final String name;
    @SerializedName("fee")
    @ColumnInfo(name = "plan_fee")
    public final int fee;

    @ForeignKey(entity = Event.class, parentColumns = "event_id", childColumns = "event_id")
    @ColumnInfo(name = "event_id")
    public String eventId;

    public Plan(@NonNull String id, String name, int fee) {
        this.id = id;
        this.name = name;
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plan)) return false;

        Plan plan = (Plan) o;

        if (fee != plan.fee) return false;
        if (!id.equals(plan.id)) return false;
        if (name != null ? !name.equals(plan.name) : plan.name != null) return false;
        return eventId != null ? eventId.equals(plan.eventId) : plan.eventId == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + fee;
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        return result;
    }
}
