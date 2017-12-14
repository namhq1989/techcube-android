package com.imed.ui.splash;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.imed.api.Resource;
import com.imed.repository.AppRepository;
import com.imed.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, December 13
 */

public class SplashViewModel extends BaseViewModel {

    public static final String ACTION_GO_LOGIN = "SplashViewModel.GoLogin";
    public static final String ACTION_GO_MAIN = "SplashViewModel.GoMain";

    private final MediatorLiveData<Resource<String>> actionLiveData = new MediatorLiveData<>();

    @Inject
    public SplashViewModel(AppRepository appRepository) {
        if (!appRepository.hasLogin()) {
            actionLiveData.setValue(Resource.success(ACTION_GO_LOGIN, null));
        } else {
            LiveData<Resource<Void>> loadConfigResultLiveData = appRepository.loadConfig();
            actionLiveData.addSource(loadConfigResultLiveData, result -> {
                //noinspection ConstantConditions
                if (result.isSuccessfully()) {
                    actionLiveData.setValue(Resource.success(ACTION_GO_MAIN, null));
                } else {
                    actionLiveData.setValue(new Resource<>(result.status, null, result.message));
                }
            });
        }

    }

    public MutableLiveData<Resource<String>> getAction() {
        return actionLiveData;
    }

}
