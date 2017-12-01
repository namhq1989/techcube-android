package com.imed.service;

import android.arch.lifecycle.LiveData;

import com.imed.api.ApiResponse;
import com.imed.model.ScanCodeResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by vinhnguyen.it.vn on 2017, November 29
 */

public interface AppService {

    @FormUrlEncoded
    @POST("customers")
    LiveData<ApiResponse<Void>> createUser(@Field("name") String name, @Field("company") String company, @Field("phone") String phone, @Field("email") String email);

    @FormUrlEncoded
    @POST("checkin")
    LiveData<ApiResponse<ScanCodeResult>> sendCode(@Field("code") String code, @Field("device[info]") String deviceInfo, @Field("device[name]") String deviceName);
}
