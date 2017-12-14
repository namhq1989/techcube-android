package com.imed.service;

import android.arch.lifecycle.LiveData;

import com.imed.api.ApiResponse;
import com.imed.model.CheckCodeResult;
import com.imed.model.GetConfigResult;
import com.imed.model.HistoryResult;
import com.imed.model.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("checkin/{code}")
    LiveData<ApiResponse<CheckCodeResult>> checkCode(@Path("code") String code);

    @FormUrlEncoded
    @POST("checkin")
    LiveData<ApiResponse<HistoryResult>> sendCode(@Field("code") String code, @Field("eventId") String event, @Field("areaId") String area,
                                                  @Field("device[info]") String deviceInfo, @Field("device[name]") String deviceName);
}
