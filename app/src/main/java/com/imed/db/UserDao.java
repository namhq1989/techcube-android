package com.imed.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.imed.model.User;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */
@Dao
public abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(User user);

    @Query("SELECT * FROM user")
    public abstract LiveData<User> loadUser();

    @Query("DELETE FROM user")
    public abstract void deleteAll();

    @Transaction
    public void saveNewUser(User user) {
        deleteAll();
        insert(user);
    }

}
