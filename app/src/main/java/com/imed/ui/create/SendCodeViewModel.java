package com.imed.ui.create;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.imed.api.Resource;
import com.imed.livedata.Transformations;
import com.imed.model.ScanCodeResult;
import com.imed.repository.AppRepository;
import com.imed.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */

public class SendCodeViewModel extends BaseViewModel {

    private final MutableLiveData<String> codeLiveData = new MutableLiveData<>();
    private final LiveData<Resource<ScanCodeResult>> sendCodeResultLiveData;

    @Inject
    public SendCodeViewModel(AppRepository appRepository) {
        sendCodeResultLiveData = Transformations.switchMap(codeLiveData, appRepository::sendCode);
    }

    public void sendCode(String code) {
        codeLiveData.setValue(code);
    }

    public LiveData<Resource<ScanCodeResult>> getSendCodeResultLiveData() {
        return sendCodeResultLiveData;
    }
}
