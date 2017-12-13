package com.imed.ui.create;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.imed.R;
import com.imed.api.Resource;
import com.imed.databinding.ActivityCreateUserBinding;
import com.imed.di.Injectable;
import com.imed.model.ScanCodeResult;
import com.imed.ui.base.BaseActivity;
import com.imed.ui.scan.ScanQRCodeActivity;
import com.imed.ui.scan.ScanResultActivity;
import com.imed.ui.view.LoadingDialog;
import com.imed.utils.AppUtils;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, November 29
 */

public class CreateUserActivity extends BaseActivity implements Injectable {

    private static final int REQUEST_CODE_SCAN = 1;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityCreateUserBinding binding;
    private CreateUserViewModel createUserViewModel;
    private SendCodeViewModel sendCodeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_user);

        createUserViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateUserViewModel.class);
        createUserViewModel.getCreateUserResultLiveData().observe(this, this::handleCreateUserResult);

        sendCodeViewModel = ViewModelProviders.of(this, viewModelFactory).get(SendCodeViewModel.class);
        sendCodeViewModel.getSendCodeResultLiveData().observe(this, this::handleSendCodeResult);

        binding.btUserReset.setOnClickListener(view -> clearAllInput());
        binding.btUserCreate.setOnClickListener(view -> createUser());
        binding.btStartScan.setOnClickListener(view -> startScan());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK && data != null) {
            String code = data.getStringExtra(ScanQRCodeActivity.EXTRA_SCANNED_CODE);
            sendCode(code);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void clearAllInput() {
        binding.etUserName.setText(null);
        binding.etUserCompany.setText(null);
        binding.etUserPhone.setText(null);
        binding.etUserEmail.setText(null);

        binding.etUserName.requestFocus();
    }

    private void createUser() {
        String name = binding.etUserName.getText().toString().trim();
        String company = binding.etUserCompany.getText().toString().trim();
        String phone = binding.etUserPhone.getText().toString().trim();
        String email = binding.etUserEmail.getText().toString().trim();
        createUserViewModel.create(name, company, phone, email);
    }

    private void startScan() {
        Intent intent = new Intent(this, ScanQRCodeActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    private void sendCode(String code) {
        sendCodeViewModel.sendCode(code);
    }

    private void handleCreateUserResult(Resource<Void> result) {
        binding.setLoading(result.isLoading());
        if (result.isError()) {
            AppUtils.showMessage(this, result.message);
        } else if (result.isSuccessfully()) {
            AppUtils.showMessage(this, result.message);
            clearAllInput();
        }
    }

    private void handleSendCodeResult(Resource<ScanCodeResult> result) {
        if (result.isLoading()) {
            LoadingDialog.getInstance(this).show();
        } else {
            LoadingDialog.getInstance(this).hideAlways();
            ScanResultActivity.start(this, result.isSuccessfully(), result.message, result.data);
        }
    }
}
