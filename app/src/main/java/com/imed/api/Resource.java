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

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class Resource<T> {

    private static final int STATUS_LOADING = 1;
    private static final int STATUS_SUCCESS = 2;
    private static final int STATUS_ERROR = 3;

    public final int status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Resource(int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@Nullable T data, @Nullable String message) {
        return new Resource<>(STATUS_SUCCESS, data, message);
    }

    public static <T> Resource<T> error(@Nullable T data, String msg) {
        return new Resource<>(STATUS_ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(STATUS_LOADING, data, null);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) return false;
        if (message != null ? !message.equals(resource.message) : resource.message != null)
            return false;
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public boolean isLoading() {
        return status == STATUS_LOADING;
    }

    public boolean isSuccessfully() {
        return status == STATUS_SUCCESS;
    }

    public boolean isError() {
        return status == STATUS_ERROR;
    }
}
