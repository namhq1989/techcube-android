package com.imed.ui.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.imed.R;
import com.imed.api.Resource;
import com.imed.databinding.ActivityLoginBinding;
import com.imed.di.Injectable;
import com.imed.ui.base.BaseActivity;
import com.imed.ui.create.CreateUserActivity;
import com.imed.utils.AppUtils;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, December 13
 */

public class LoginActivity extends BaseActivity implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.btLoginSubmit.setOnClickListener(view -> login());

        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        loginViewModel.getLoginResult().observe(this, this::handleLoginResult);
    }

    private void login() {
        String email = binding.etLoginEmail.getText().toString().trim();
        String password = binding.etLoginPassword.getText().toString();
        loginViewModel.login(email, password);
    }

    private void handleLoginResult(Resource<Void> result) {
        binding.setLoading(result.isLoading());
        if (result.isError()) {
            AppUtils.showMessage(this, result.message);
        } else if (result.isSuccessfully()) {
            goMain();
        }
    }

    private void goMain() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
