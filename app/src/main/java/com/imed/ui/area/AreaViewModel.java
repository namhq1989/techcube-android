package com.imed.ui.area;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.imed.api.Resource;
import com.imed.livedata.Transformations;
import com.imed.model.CheckCodeResult;
import com.imed.repository.AppRepository;
import com.imed.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */

public class AreaViewModel extends BaseViewModel {

    private final MutableLiveData<String> codeLiveData = new MutableLiveData<>();
    private final LiveData<Resource<CheckCodeResult>> checkCodeResultLiveData;

    @Inject
    public AreaViewModel(AppRepository appRepository) {
        checkCodeResultLiveData = Transformations.switchMap(codeLiveData, appRepository::checkCode);
    }

    public void checkCode(String code) {
        if (code != null) {
            codeLiveData.setValue(code);
        }
    }

    public LiveData<Resource<CheckCodeResult>> getCheckCodeResult() {
        return checkCodeResultLiveData;
    }
}
