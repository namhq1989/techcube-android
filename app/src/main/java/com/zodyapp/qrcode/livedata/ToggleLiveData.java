package com.zodyapp.qrcode.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

/**
 * Created by vinhnguyen.it.vn on 2017, October 13
 */

public class ToggleLiveData {

    private final MediatorLiveData<Void> liveData = new MediatorLiveData<>();

    public void addSource(LiveData<?>... sources) {
        for (LiveData<?> source : sources) {
            liveData.addSource(source, value -> liveData.setValue(null));
        }
    }

    public LiveData<?> getLiveData() {
        return liveData;
    }

    public void toggle() {
        liveData.setValue(null);
    }


}
