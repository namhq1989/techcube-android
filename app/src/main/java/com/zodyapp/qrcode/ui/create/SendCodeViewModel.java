package com.zodyapp.qrcode.ui.create;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.zodyapp.qrcode.api.Resource;
import com.zodyapp.qrcode.livedata.Transformations;
import com.zodyapp.qrcode.model.ScanCodeResult;
import com.zodyapp.qrcode.repository.AppRepository;
import com.zodyapp.qrcode.ui.base.BaseViewModel;

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
