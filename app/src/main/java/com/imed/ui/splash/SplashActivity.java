package com.imed.ui.splash;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.imed.R;
import com.imed.di.Injectable;
import com.imed.ui.base.BaseActivity;
import com.imed.ui.create.CreateUserActivity;
import com.imed.ui.login.LoginActivity;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, December 01
 */

public class SplashActivity extends BaseActivity implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_splash);

        SplashViewModel splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
        splashViewModel.getAction().observe(this, this::handleAction);
    }

    private void handleAction(String action) {
        switch (action) {
            case SplashViewModel.ACTION_GO_LOGIN:
                goLogin();
                break;
            case SplashViewModel.ACTION_GO_MAIN:
                goMain();
                break;
        }
    }

    private void goMain() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
