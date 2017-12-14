package com.imed.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.imed.db.AppDatabase;
import com.imed.db.EventDao;
import com.imed.db.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, "app.db").build();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(AppDatabase db) {
        return db.userDao();
    }

    @Provides
    @Singleton
    EventDao provideEventDao(AppDatabase db) {
        return db.eventDao();
    }
}
