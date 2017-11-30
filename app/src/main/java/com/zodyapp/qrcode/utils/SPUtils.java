package com.zodyapp.qrcode.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zodyapp.qrcode.BuildConfig;
import com.zodyapp.qrcode.R;

/**
 * Created by vinhnguyen.it.vn on 2017, September 20
 */

public class SPUtils {

    private final String KEY_TOKEN = "pref_key_token";
    private final String KEY_EMAIL = "pref_key_email";
    private final String KEY_ID = "pref_key_id";
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

    public String getId() {
        return sp.getString(KEY_ID, null);
    }

    public void setId(String id) {
        sp.edit().putString(KEY_ID, id).apply();
    }

    public String getDeviceToken() {
        return sp.getString(KEY_DEVICE_TOKEN, null);
    }

    public void setDeviceToken(String token) {
        sp.edit().putString(KEY_DEVICE_TOKEN, token).apply();
    }

    public void clear() {
        sp.edit().remove(KEY_TOKEN).remove(KEY_ID).apply();
    }
}
