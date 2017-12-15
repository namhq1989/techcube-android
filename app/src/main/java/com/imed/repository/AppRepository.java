package com.imed.repository;

import android.arch.lifecycle.LiveData;
import android.os.Build;
import android.support.annotation.NonNull;

import com.imed.api.ApiResponse;
import com.imed.api.NetworkOnlyResource;
import com.imed.api.NetworkResource;
import com.imed.api.Resource;
import com.imed.db.EventDao;
import com.imed.db.UserDao;
import com.imed.livedata.StaticLiveData;
import com.imed.livedata.Transformations;
import com.imed.model.CheckCodeResult;
import com.imed.model.EventAndPlan;
import com.imed.model.GetConfigResult;
import com.imed.model.HistoryResult;
import com.imed.model.LoginResult;
import com.imed.model.User;
import com.imed.service.AppService;
import com.imed.utils.AppExecutors;
import com.imed.utils.SPUtils;
import com.imed.utils.ValidationUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */
@Singleton
public class AppRepository {

    private final AppExecutors appExecutors;
    private final ValidationUtils validationUtils;
    private final SPUtils spUtils;
    private final AppService appService;
    private final UserDao userDao;
    private final EventDao eventDao;

    @Inject
    public AppRepository(AppExecutors appExecutors, ValidationUtils validationUtils, SPUtils spUtils, AppService appService, UserDao userDao, EventDao eventDao) {
        this.appExecutors = appExecutors;
        this.validationUtils = validationUtils;
        this.spUtils = spUtils;
        this.appService = appService;
        this.userDao = userDao;
        this.eventDao = eventDao;
    }

    public boolean hasLogin() {
        return spUtils.getToken() != null;
    }

    public LiveData<User> loadUser() {
        return userDao.loadUser();
    }

    public LiveData<Resource<Void>> loadConfig() {
        return new NetworkOnlyResource<Void, GetConfigResult>(appExecutors) {

            @Override
            protected Void transfer(GetConfigResult item) {
                return null;
            }

            @Override
            protected void saveCallResult(@NonNull GetConfigResult item) {
                eventDao.insertNewEvent(item.events);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<GetConfigResult>> createCall() {
                return appService.loadConfig();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> login(String email, String password) {
        String error = validationUtils.validateEmail(email);
        if (error != null) {
            return new StaticLiveData<>(Resource.error(null, error));
        }
        error = validationUtils.validatePassword(password);
        if (error != null) {
            return new StaticLiveData<>(Resource.error(null, error));
        }
        LiveData<Resource<Void>> loginResult = new NetworkOnlyResource<Void, LoginResult>(appExecutors) {

            @Override
            protected Void transfer(LoginResult item) {
                return null;
            }

            @Override
            protected void saveCallResult(@NonNull LoginResult item) {
                spUtils.setToken(item.token);
                userDao.saveNewUser(item.user);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<LoginResult>> createCall() {
                return appService.login(email, password);
            }
        }.asLiveData();
        return Transformations.switchMap(loginResult, result -> {
            if (result.isSuccessfully()) {
                return loadConfig();
            }
            return new StaticLiveData<>(new Resource<>(result.status, result.data, result.message));
        });
    }

    public LiveData<Resource<Void>> createUser(String name, String company, String phone, String email, String eventId, String planId) {
        boolean hasName = name != null && name.trim().length() > 0;
        boolean hasEvent = eventId != null && eventId.trim().length() > 0;

        String error;
        if (hasName) {
            error = validationUtils.validateName(name);
            if (error != null) {
                return new StaticLiveData<>(Resource.error(null, error));
            }

            error = validationUtils.validateCompany(company);
            if (error != null) {
                return new StaticLiveData<>(Resource.error(null, error));
            }
        }
        error = validationUtils.validatePhone(phone);
        if (error != null) {
            return new StaticLiveData<>(Resource.error(null, error));
        }
        error = validationUtils.validateEmail(email);
        if (error != null) {
            return new StaticLiveData<>(Resource.error(null, error));
        }

        if (!hasName) {
            error = validationUtils.validateEvent(eventId);
            if (error != null) {
                return new StaticLiveData<>(Resource.error(null, error));
            }
        }

        if (!hasName || hasEvent) {
            error = validationUtils.validatePlan(planId);
            if (error != null) {
                return new StaticLiveData<>(Resource.error(null, error));
            }
        }

        return new NetworkResource<Void>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                if (hasName) {
                    return appService.createCustomer(name, company, phone, email, eventId, planId);
                }
                return appService.updateCustomer(phone, email, eventId, planId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<CheckCodeResult>> checkCode(String code) {
        return new NetworkResource<CheckCodeResult>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<CheckCodeResult>> createCall() {
                return appService.checkCode(code);
            }
        }.asLiveData();
    }

    public LiveData<Resource<HistoryResult>> checkIn(String code, String event, String area) {
        return new NetworkResource<HistoryResult>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<HistoryResult>> createCall() {
                String agent = System.getProperty("http.agent");
                String deviceName = String.format("%s %s", Build.MANUFACTURER, Build.MODEL);
                return appService.sendCode(code, event, area, agent, deviceName);
            }
        }.asLiveData();
    }

    public LiveData<List<EventAndPlan>> loadEvents() {
        return eventDao.loadEventsWithPlans();
    }

    public void logout() {
        appExecutors.diskIO().execute(() -> {
            spUtils.clear();
            userDao.deleteAll();
            eventDao.deleteAllPlan();
            eventDao.deleteAllEvent();
        });
    }
}
