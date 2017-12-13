package com.imed.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.imed.BuildConfig;

/**
 * Created by vinhnguyen.it.vn on 2017, September 20
 */

public class SPUtils {

    private final String KEY_TOKEN = "pref_key_token";
    private final String KEY_EMAIL = "pref_key_email";
    private final String KEY_ROLE = "pref_key_role";
    private final String KEY_DEVICE_TOKEN = "pref_device_token";

    private final SharedPreferences sp;

    public SPUtils(Context context) {
        this.sp = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    public String getToken() {
        return sp.getString(KEY_TOKEN, null);
    }

    public void setToken(String token) {
        sp.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getEmail() {
        return sp.getString(KEY_EMAIL, null);
    }

    public void setEmail(String email) {
        sp.edit().putString(KEY_EMAIL, email).apply();
    }

    public String getRole() {
        return sp.getString(KEY_ROLE, null);
    }

    public void setRole(String role) {
        sp.edit().putString(KEY_ROLE, role).apply();
    }

    public String getDeviceToken() {
        return sp.getString(KEY_DEVICE_TOKEN, null);
    }

    public void setDeviceToken(String token) {
        sp.edit().putString(KEY_DEVICE_TOKEN, token).apply();
    }

    public void clear() {
        sp.edit().remove(KEY_TOKEN).remove(KEY_ROLE).apply();
    }
}
