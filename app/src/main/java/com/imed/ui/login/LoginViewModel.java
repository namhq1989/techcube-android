package com.imed.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.imed.api.Resource;
import com.imed.livedata.Transformations;
import com.imed.repository.AppRepository;
import com.imed.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, December 13
 */

public class LoginViewModel extends BaseViewModel {

    private final MutableLiveData<LoginInfo> loginInfoLiveData = new MutableLiveData<>();
    private final LiveData<Resource<Void>> loginResultLiveData;

    @Inject
    public LoginViewModel(AppRepository appRepository) {
        loginResultLiveData = Transformations.switchMap(loginInfoLiveData, info -> appRepository.login(info.email, info.password));
    }

    public void login(String email, String password) {
        loginInfoLiveData.setValue(new LoginInfo(email, password));
    }

    public LiveData<Resource<Void>> getLoginResult() {
        return loginResultLiveData;
    }

    private static class LoginInfo {
        private final String email;
        private final String password;

        private LoginInfo(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
