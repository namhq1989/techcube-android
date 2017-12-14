package com.imed.ui.create;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.imed.api.Resource;
import com.imed.livedata.Transformations;
import com.imed.model.EventAndPlan;
import com.imed.repository.AppRepository;
import com.imed.ui.base.BaseViewModel;
import com.imed.utils.Objects;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, November 29
 */

public class CreateUserViewModel extends BaseViewModel {

    private final LiveData<List<EventAndPlan>> eventsLiveData;
    private final MutableLiveData<EventAndPlan> selectedEventLiveData = new MutableLiveData<>();

    private final MutableLiveData<UserInfo> userInfoMutableLiveData = new MutableLiveData<>();
    private final LiveData<Resource<Void>> createUserResultLiveData;

    private final AppRepository appRepository;

    @Inject
    public CreateUserViewModel(AppRepository appRepository) {
        this.appRepository = appRepository;

        eventsLiveData = appRepository.loadEvents();
        createUserResultLiveData = Transformations.switchMap(userInfoMutableLiveData, info -> appRepository.createUser(info.name, info.company, info.phone, info.email));
    }

    public void create(String name, String company, String phone, String email) {
        userInfoMutableLiveData.setValue(new UserInfo(name, company, phone, email));
    }

    public void select(EventAndPlan event) {
        selectedEventLiveData.setValue(event);
    }

    public void logout() {
        appRepository.logout();
    }

    public LiveData<Resource<Void>> getCreateUserResultLiveData() {
        return createUserResultLiveData;
    }

    public LiveData<List<EventAndPlan>> getEvents() {
        return eventsLiveData;
    }

    public MutableLiveData<EventAndPlan> getSelectedEvent() {
        return selectedEventLiveData;
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
