package com.imed.ui.history;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.imed.R;
import com.imed.api.Resource;
import com.imed.databinding.ActivityHistoryBinding;
import com.imed.di.Injectable;
import com.imed.model.HistoryResult;
import com.imed.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */

public class HistoryActivity extends BaseActivity implements Injectable {

    public static final String EXTRA_CODE = "AreaActivity.Code";
    public static final String EXTRA_EVENT = "ScanQRCodeActivity.Event";
    public static final String EXTRA_AREA = "ScanQRCodeActivity.Area";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.set(15, 15, 15, 15);
                } else {
                    outRect.bottom = 1;
                }
            }
        });

        HistoryViewModel historyViewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryViewModel.class);
        historyViewModel.getResult().observe(this, this::handleResult);

        String code = getIntent().getStringExtra(EXTRA_CODE);
        String event = getIntent().getStringExtra(EXTRA_EVENT);
        String area = getIntent().getStringExtra(EXTRA_AREA);
        historyViewModel.checkin(code, event, area);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void handleResult(Resource<HistoryResult> result) {
        if (!result.isLoading()) {
            //noinspection ConstantConditions
            binding.recyclerView.setAdapter(new HistoryAdapter(result.message, result.isSuccessfully(), result.data.histories));
        }
    }
}
