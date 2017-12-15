package com.imed.ui.create;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.imed.R;
import com.imed.api.Resource;
import com.imed.databinding.ActivityCreateUserBinding;
import com.imed.di.Injectable;
import com.imed.model.EventAndPlan;
import com.imed.model.Plan;
import com.imed.ui.area.AreaActivity;
import com.imed.ui.base.BaseActivity;
import com.imed.ui.history.HistoryActivity;
import com.imed.ui.login.LoginActivity;
import com.imed.ui.scan.ScanQRCodeActivity;
import com.imed.ui.view.SpinnerAdapter;
import com.imed.utils.AppUtils;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, November 29
 */

public class CreateUserActivity extends BaseActivity implements Injectable {

    private static final int REQUEST_CODE_SCAN = 1;
    private static final int REQUEST_CODE_AREA = 2;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityCreateUserBinding binding;
    private CreateUserViewModel createUserViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_user);

        createUserViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateUserViewModel.class);
        createUserViewModel.getUser().observe(this, binding::setUser);
        createUserViewModel.getCreateUserResultLiveData().observe(this, this::handleCreateUserResult);

        binding.btUserReset.setOnClickListener(view -> clearAllInput());
        binding.btUserCreate.setOnClickListener(view -> createUser());
        binding.btStartScan.setOnClickListener(view -> startScan());
        binding.btLogout.setOnClickListener(view -> {
            createUserViewModel.logout();
            goLogin();
        });

        binding.snChooseEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //noinspection unchecked
                EventAndPlan event = ((SpinnerAdapter<EventAndPlan>) adapterView.getAdapter()).getItemAt(i);
                createUserViewModel.select(event);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.snChoosePlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //noinspection unchecked
                Plan plan = ((SpinnerAdapter<Plan>) adapterView.getAdapter()).getItemAt(i);
                createUserViewModel.select(plan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        createUserViewModel.getEvents().observe(this, events ->
                binding.snChooseEvent.setAdapter(new SpinnerAdapter<EventAndPlan>(this, "Sự kiện", events) {
                    @Override
                    public String transfer(EventAndPlan item) {
                        return item.event.name;
                    }
                }));
        createUserViewModel.getSelectedEvent().observe(this, event ->
                binding.snChoosePlan.setAdapter(new SpinnerAdapter<Plan>(this, "Gói khách hàng", event != null ? event.plans : null) {
                    @Override
                    public String transfer(Plan item) {
                        return item.name;
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK && data != null) {
            String code = data.getStringExtra(ScanQRCodeActivity.EXTRA_SCANNED_CODE);
            goSelectArea(code);
        } else if (requestCode == REQUEST_CODE_AREA && resultCode == RESULT_OK && data != null) {
            String code = data.getStringExtra(AreaActivity.EXTRA_CODE);
            String event = data.getStringExtra(AreaActivity.EXTRA_EVENT);
            String area = data.getStringExtra(AreaActivity.EXTRA_AREA);
            goHistory(code, event, area);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void clearAllInput() {
        binding.etUserName.setText(null);
        binding.etUserCompany.setText(null);
        binding.etUserPhone.setText(null);
        binding.etUserEmail.setText(null);

        binding.snChooseEvent.setSelection(0);
        binding.snChoosePlan.setSelection(0);

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

    private void handleCreateUserResult(Resource<Void> result) {
        binding.setLoading(result.isLoading());
        if (result.isError()) {
            AppUtils.showMessage(this, result.message);
        } else if (result.isSuccessfully()) {
            AppUtils.showMessage(this, result.message);
            clearAllInput();
        }
    }

    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void goSelectArea(String code) {
        Intent intent = new Intent(this, AreaActivity.class);
        intent.putExtra(AreaActivity.EXTRA_CODE, code);
        startActivityForResult(intent, REQUEST_CODE_AREA);
    }

    private void goHistory(String code, String event, String area) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(HistoryActivity.EXTRA_CODE, code);
        intent.putExtra(HistoryActivity.EXTRA_EVENT, event);
        intent.putExtra(HistoryActivity.EXTRA_AREA, area);
        startActivity(intent);
    }
}
