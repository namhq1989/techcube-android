/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.imed.api;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LiveDataCallAdapter<T> implements CallAdapter<ApiResponse<T>, LiveData<ApiResponse<T>>> {

    private final Type responseType;
    private final Retrofit retrofit;

    public LiveDataCallAdapter(Type responseType, Retrofit retrofit) {
        this.responseType = responseType;
        this.retrofit = retrofit;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<T>> adapt(@NonNull Call<ApiResponse<T>> call) {
        return new LiveData<ApiResponse<T>>() {
            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<ApiResponse<T>>() {
                        @Override
                        public void onResponse(@NonNull Call<ApiResponse<T>> call, @NonNull Response<ApiResponse<T>> response) {
                            if (response.isSuccessful()) {
                                postValue(response.body());
                            } else {
                                Converter<ResponseBody, ApiResponse> converter = retrofit.responseBodyConverter(ApiResponse.class, ApiResponse.class.getAnnotations());
                                try {
                                    ResponseBody body = response.errorBody();
                                    //noinspection ConstantConditions
                                    ApiResponse errorResponse = converter.convert(body);
                                    //noinspection unchecked
                                    postValue(errorResponse);
                                } catch (IOException e) {
                                    onFailure(call, e);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ApiResponse<T>> call, @NonNull Throwable throwable) {
                            postValue(new ApiResponse<>(throwable));
                        }
                    });
                }
            }
        };
    }
}

