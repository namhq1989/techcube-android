package com.zodyapp.qrcode.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;

import com.zodyapp.qrcode.di.Injectable;

import dagger.android.AndroidInjection;

/**
 * Created by vinhnguyen.it.vn on 2017, September 19
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (this instanceof Injectable) {
            AndroidInjection.inject(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        finishToParentActivity();
    }

    public void finishToParentActivity() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (upIntent == null) {
            finish();
        } else if (NavUtils.shouldUpRecreateTask(this, upIntent) || isTaskRoot()) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            finish();
        }
    }
}
