package com.imed.ui.scan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.google.zxing.Result;
import com.imed.R;
import com.imed.databinding.ActivityScanQrCodeBinding;
import com.imed.ui.base.BaseActivity;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */

public class ScanQRCodeActivity extends BaseActivity {

    public static final String EXTRA_SCANNED_CODE = "ScanQRCodeActivity.ScannedCode";

    private static final int REQUEST_PERMISSION_CODE = 1;

    private static final String[] permissions = {Manifest.permission.CAMERA};

    private ActivityScanQrCodeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_qr_code);
        binding.scannerView.setResultHandler(this::saveResult);

        setSupportActionBar(binding.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(null);

        if (!checkPermission()) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            onBackPressed();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermission()) startScan();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopScan();
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED;
    }

    private void startScan() {
        binding.scannerView.startCamera();
    }

    private void stopScan() {
        binding.scannerView.stopCamera();
    }

    private void saveResult(Result result) {
        String code = result.getText();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SCANNED_CODE, code);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
