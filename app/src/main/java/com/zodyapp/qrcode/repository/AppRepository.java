package com.zodyapp.qrcode.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.zodyapp.qrcode.api.ApiResponse;
import com.zodyapp.qrcode.api.NetworkResource;
import com.zodyapp.qrcode.api.Resource;
import com.zodyapp.qrcode.livedata.StaticLiveData;
import com.zodyapp.qrcode.model.ScanCodeResult;
import com.zodyapp.qrcode.service.AppService;
import com.zodyapp.qrcode.utils.AppExecutors;
import com.zodyapp.qrcode.utils.ValidationUtils;

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
                return appService.sendCode(code, agent);
            }
        }.asLiveData();
    }
}
