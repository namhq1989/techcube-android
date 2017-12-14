package com.imed.service;

import android.arch.lifecycle.LiveData;

import com.imed.api.ApiResponse;
import com.imed.model.GetConfigResult;
import com.imed.model.LoginResult;
import com.imed.model.ScanCodeResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by vinhnguyen.it.vn on 2017, November 29
 */

public interface AppService {

    @GET("data")
    LiveData<ApiResponse<GetConfigResult>> loadConfig();

    @FormUrlEncoded
    @POST("login")
    LiveData<ApiResponse<LoginResult>> login(@Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("customers")
    LiveData<ApiResponse<Void>> createCustomer(@Field("name") String name, @Field("company") String company,
                                               @Field("phone") String phone, @Field("email") String email,
                                               @Field("eventId") String eventId, @Field("planId") String planId);

    @FormUrlEncoded
    @POST("customers/upgradePlan")
    LiveData<ApiResponse<Void>> updateCustomer(@Field("phone") String phone, @Field("email") String email, @Field("eventId") String eventId, @Field("planId") String planId);

    @FormUrlEncoded
    @POST("checkin")
    LiveData<ApiResponse<ScanCodeResult>> sendCode(@Field("code") String code, @Field("device[info]") String deviceInfo, @Field("device[name]") String deviceName);
}
