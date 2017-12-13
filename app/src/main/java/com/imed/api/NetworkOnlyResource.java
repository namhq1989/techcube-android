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
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.imed.utils.AppExecutors;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetworkOnlyResource<ResultType, RequestType> {

    private final AppExecutors appExecutors;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkOnlyResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        fetchFromNetwork();
    }

    private void fetchFromNetwork() {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    RequestType requestValue = processResponse(response);
                    saveCallResult(requestValue);
                    ResultType resultValue = transfer(requestValue);
                    appExecutors.mainThread().execute(() ->
                            result.setValue(Resource.success(resultValue, response.message))
                    );
                });
            } else {
                onFetchFailed();
                result.setValue(Resource.error(null, response.message));
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.data;
    }

    @WorkerThread
    protected abstract ResultType transfer(RequestType item);

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
