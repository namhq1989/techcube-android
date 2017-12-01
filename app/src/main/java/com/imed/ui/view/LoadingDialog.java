package com.imed.ui.view;

import android.app.ProgressDialog;
import android.content.Context;

import com.imed.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by VINHNGUYEN.IT.VN on 9/19/16
 */
public class LoadingDialog {

    private static LoadingDialog instance;

    public static LoadingDialog getInstance(Context context) {
        if (instance == null) {
            instance = new LoadingDialog(context);
        } else if (instance.context != context) {
            instance.hide();
            instance = new LoadingDialog(context);
        }
        return instance;
    }

    private final Context context;
    private final ProgressDialog progressDialog;
    private final AtomicInteger count = new AtomicInteger();

    private LoadingDialog(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(R.string.loading));
    }

    public void show() {
        if (count.getAndIncrement() == 0) {
            try {
                progressDialog.show();
            } catch (Exception ignored) {

            }
        }
    }

    public void hide() {
        if (count.decrementAndGet() == 0) {
            try {
                progressDialog.dismiss();
            } catch (Exception ignored) {

            }
        } else if (count.get() < 0) {
            count.set(0);
        }
    }

    public void hideAlways() {
        try {
            progressDialog.dismiss();
        } catch (Exception ignored) {

        }
        count.set(0);
    }
}
