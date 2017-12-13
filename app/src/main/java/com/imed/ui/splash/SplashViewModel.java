package com.imed.ui.splash;

import android.arch.lifecycle.MutableLiveData;

import com.imed.repository.AppRepository;
import com.imed.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, December 13
 */

public class SplashViewModel extends BaseViewModel {

    public static final String ACTION_GO_LOGIN = "SplashViewModel.GoLogin";
    public static final String ACTION_GO_MAIN = "SplashViewModel.GoMain";

    private final MutableLiveData<String> actionLiveData = new MutableLiveData<>();

    @Inject
    public SplashViewModel(AppRepository appRepository) {
        actionLiveData.setValue(appRepository.hasLogin() ? ACTION_GO_MAIN : ACTION_GO_LOGIN);
    }

    public MutableLiveData<String> getAction() {
        return actionLiveData;
    }

}
