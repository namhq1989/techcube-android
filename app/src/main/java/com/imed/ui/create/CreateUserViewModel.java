package com.imed.ui.create;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.imed.api.Resource;
import com.imed.livedata.Transformations;
import com.imed.repository.AppRepository;
import com.imed.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, November 29
 */

public class CreateUserViewModel extends BaseViewModel {

    private final MutableLiveData<UserInfo> userInfoMutableLiveData = new MutableLiveData<>();
    private final LiveData<Resource<Void>> createUserResultLiveData;

    @Inject
    public CreateUserViewModel(AppRepository appRepository) {
        createUserResultLiveData = Transformations.switchMap(userInfoMutableLiveData, info -> appRepository.createUser(info.name, info.company, info.phone, info.email));
    }

    public void create(String name, String company, String phone, String email) {
        userInfoMutableLiveData.setValue(new UserInfo(name, company, phone, email));
    }

    public LiveData<Resource<Void>> getCreateUserResultLiveData() {
        return createUserResultLiveData;
    }

    private static final class UserInfo {
        private final String name;
        private final String company;
        private final String phone;
        private final String email;

        private UserInfo(String name, String company, String phone, String email) {
            this.name = name;
            this.company = company;
            this.phone = phone;
            this.email = email;
        }
    }
}
