package com.zodyapp.qrcode.ui.scan;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;

import com.zodyapp.qrcode.R;
import com.zodyapp.qrcode.databinding.ActivityScanResultBinding;
import com.zodyapp.qrcode.model.History;
import com.zodyapp.qrcode.model.ScanCodeResult;
import com.zodyapp.qrcode.ui.base.BaseActivity;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by vinhnguyen.it.vn on 2017, November 30
 */

public class ScanResultActivity extends BaseActivity {

    private static final String EXTRA_MESSAGE = "ScanResultDialog.Message";
    private static final String EXTRA_SUCCESS = "ScanResultDialog.Success";
    private static final String EXTRA_RESULT = "ScanResultDialog.Result";

    public static void start(Context context, boolean success, String message, ScanCodeResult result) {
        Intent starter = new Intent(context, ScanResultActivity.class);
        starter.putExtra(EXTRA_SUCCESS, success);
        starter.putExtra(EXTRA_MESSAGE, message);
        starter.putExtra(EXTRA_RESULT, result);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityScanResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_result);

        binding.tvContent.setMovementMethod(new ScrollingMovementMethod());

        binding.setSuccess(getIntent().getBooleanExtra(EXTRA_SUCCESS, false));
        binding.setMessage(getIntent().getStringExtra(EXTRA_MESSAGE));

        ScanCodeResult result = getIntent().getParcelableExtra(EXTRA_RESULT);
        if (result != null && result.histories != null && result.histories.size() > 0) {

            DateTimeFormatter parser = ISODateTimeFormat.dateTime();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss, dd/MM/yyyy");

            StringBuilder builder = new StringBuilder();
            for (History history : result.histories) {
                String formattedTime = formatter.print(parser.parseDateTime(history.date));
                builder.append(getString(R.string.scan_result_success_content, history.event.name, formattedTime));
                builder.append("\n\n");
            }
            binding.setContent(builder.toString());
        }

        binding.btBack.setOnClickListener(v -> onBackPressed());
    }
}
