package com.imed.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.imed.model.Event;
import com.imed.model.EventAndPlan;
import com.imed.model.Plan;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */
@Dao
public abstract class EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertEvent(List<Event> events);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertPlan(List<Plan> plans);

    @Query("SELECT * FROM event")
    public abstract LiveData<List<EventAndPlan>> loadEventsWithPlans();

    @Query("DELETE FROM event")
    public abstract void deleteAllEvent();

    @Query("DELETE FROM event_plan")
    public abstract void deleteAllPlan();

    @Transaction
    public void insertNewEvent(List<Event> events) {
        deleteAllEvent();
        deleteAllPlan();
        insertEvent(events);
        for (Event event : events) {
            for (Plan plan : event.plans) {
                plan.eventId = event.id;
            }
            insertPlan(event.plans);
        }
    }
}
