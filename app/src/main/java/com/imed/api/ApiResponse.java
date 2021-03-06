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

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Common class used by API responses.
 *
 * @param <T>
 */
public class ApiResponse<T> {

    @SerializedName("success")
    public final boolean success;
    @SerializedName("statusCode")
    public final int statusCode;
    @SerializedName("code")
    public final int code;
    @Nullable
    @SerializedName("data")
    public final T data;
    @Nullable
    @SerializedName("message")
    public final String message;

    public ApiResponse(Throwable error) {
        success = false;
        statusCode = 500;
        code = 0;
        data = null;
        message = error.getMessage();
    }

    public boolean isSuccessful() {
        return success;
    }
}
