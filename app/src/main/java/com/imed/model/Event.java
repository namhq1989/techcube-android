package com.imed.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */
@Entity(tableName = "event")
public class Event {
    @PrimaryKey
    @SerializedName("_id")
    @ColumnInfo(name = "event_id")
    @NonNull
    public final String id;
    @SerializedName("name")
    @ColumnInfo(name = "event_name")
    public final String name;

    @Ignore
    public List<Plan> plans;

    public Event(@NonNull String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (!id.equals(event.id)) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        return plans != null ? plans.equals(event.plans) : event.plans == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (plans != null ? plans.hashCode() : 0);
        return result;
    }
}
