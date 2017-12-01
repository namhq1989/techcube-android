package com.imed.repository;

import android.arch.lifecycle.LiveData;
import android.os.Build;
import android.support.annotation.NonNull;

import com.imed.api.ApiResponse;
import com.imed.api.NetworkResource;
import com.imed.api.Resource;
import com.imed.livedata.StaticLiveData;
import com.imed.model.ScanCodeResult;
import com.imed.service.AppService;
import com.imed.utils.AppExecutors;
import com.imed.utils.ValidationUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */
@Singleton
public class AppRepository {

    private final AppExecutors appExecutors;
    private final ValidationUtils validationUtils;
    private final AppService appService;

    @Inject
    public AppRepository(AppExecutors appExecutors, ValidationUtils validationUtils, AppService appService) {
        this.appExecutors = appExecutors;
        this.validationUtils = validationUtils;
        this.appService = appService;
    }

    public LiveData<Resource<Void>> createUser(String name, String company, String phone, String email) {
        String error = validationUtils.validateName(name);
        if (error != null) {
            return new StaticLiveData<>(Resource.error(null, error));
        }
        error = validationUtils.validatePhone(phone);
        if (error != null) {
            return new StaticLiveData<>(Resource.error(null, error));
        }
        error = validationUtils.validateEmail(email);
        if (error != null) {
            return new StaticLiveData<>(Resource.error(null, error));
        }
        return new NetworkResource<Void>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return appService.createUser(name, company, phone, email);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ScanCodeResult>> sendCode(String code) {
        return new NetworkResource<ScanCodeResult>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<ScanCodeResult>> createCall() {
                String agent = System.getProperty("http.agent");
                String deviceName = String.format("%s %s", Build.MANUFACTURER, Build.MODEL);
                return appService.sendCode(code, agent, deviceName);
            }
        }.asLiveData();
    }
}
