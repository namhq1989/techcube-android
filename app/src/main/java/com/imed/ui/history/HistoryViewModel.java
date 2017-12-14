package com.imed.ui.history;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.imed.api.Resource;
import com.imed.livedata.Transformations;
import com.imed.model.HistoryResult;
import com.imed.repository.AppRepository;
import com.imed.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */

public class HistoryViewModel extends BaseViewModel {

    private MutableLiveData<Info> infoLiveData = new MutableLiveData<>();
    private LiveData<Resource<HistoryResult>> resultLiveData;

    @Inject
    public HistoryViewModel(AppRepository appRepository) {
        resultLiveData = Transformations.switchMap(infoLiveData, info -> appRepository.checkIn(info.code, info.event, info.area));
    }

    public void checkin(String code, String event, String area) {
        infoLiveData.setValue(new Info(code, event, area));
    }

    public LiveData<Resource<HistoryResult>> getResult() {
        return resultLiveData;
    }

    private static class Info {
        public final String code;
        public final String event;
        public final String area;

        private Info(String code, String event, String area) {
            this.code = code;
            this.event = event;
            this.area = area;
        }
    }
}
