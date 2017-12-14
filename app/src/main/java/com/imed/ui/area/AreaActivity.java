package com.imed.ui.area;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.imed.R;
import com.imed.api.Resource;
import com.imed.databinding.ActivityAreaBinding;
import com.imed.di.Injectable;
import com.imed.model.CheckCodeResult;
import com.imed.ui.base.BaseActivity;
import com.imed.utils.AppUtils;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */

public class AreaActivity extends BaseActivity implements Injectable {

    public static final String EXTRA_CODE = "AreaActivity.Code";
    public static final String EXTRA_AREA = "ScanQRCodeActivity.Area";
    public static final String EXTRA_EVENT = "ScanQRCodeActivity.Event";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityAreaBinding binding;

    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        code = getIntent().getStringExtra(EXTRA_CODE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_area);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.bottom = 15;
                } else {
                    outRect.bottom = 1;
                }
            }
        });

        binding.btAreaCancel.setOnClickListener(view -> finish());
        binding.btAreaOk.setOnClickListener(view -> saveResult());

        AreaViewModel areaViewModel = ViewModelProviders.of(this, viewModelFactory).get(AreaViewModel.class);
        areaViewModel.getCheckCodeResult().observe(this, this::handleCheckCodeResult);

        areaViewModel.checkCode(code);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleCheckCodeResult(Resource<CheckCodeResult> result) {
        if (result.isSuccessfully()) {
            binding.recyclerView.setAdapter(new AreaAdapter(result.data));
        } else if (result.isError()) {
            AppUtils.showMessage(this, result.message, this::finish);
        }
    }

    private void saveResult() {
        AreaAdapter adapter = (AreaAdapter) binding.recyclerView.getAdapter();
        if (adapter != null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_CODE, code);
            intent.putExtra(EXTRA_AREA, adapter.getCurrentAreaId());
            intent.putExtra(EXTRA_EVENT, adapter.getEventId());
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }
}
