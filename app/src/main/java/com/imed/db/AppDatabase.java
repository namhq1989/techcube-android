package com.imed.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.imed.model.Event;
import com.imed.model.Plan;
import com.imed.model.User;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */
@Database(entities = {User.class, Event.class, Plan.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract EventDao eventDao();
}
