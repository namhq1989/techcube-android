package com.imed.livedata;

import android.arch.lifecycle.LiveData;

/**
 * Created by vinhnguyen.it.vn on 2017, September 20
 */

public class StaticLiveData<T> extends LiveData<T> {

    public StaticLiveData(T value) {
        postValue(value);
    }
}
