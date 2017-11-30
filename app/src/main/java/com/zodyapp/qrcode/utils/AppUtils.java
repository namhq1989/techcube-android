package com.zodyapp.qrcode.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import com.zodyapp.qrcode.R;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by vinhnguyen.it.vn on 2017, June 21
 */

public class AppUtils {

    public static final DateTimeFormatter timeParser = ISODateTimeFormat.dateTime();
    public static final DateTimeFormatter timeFormatter = ISODateTimeFormat.dateTime().withZoneUTC();
    public static final DateTimeFormatter dateFormatter = ISODateTimeFormat.date();

    public static void showMessage(Context context, String message) {
        showMessage(context, message, null);
    }

    public static void showMessage(Context context, String message, Runnable runnable) {
        new AlertDialog.Builder(context).setMessage(message)
                .setOnDismissListener(dialogInterface -> {
                    if (runnable != null) runnable.run();
                })
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public static void goNext(ViewPager viewPager) {
        if (viewPager == null || viewPager.getAdapter() == null) return;
        int currentItem = viewPager.getCurrentItem();
        int total = viewPager.getAdapter().getCount();
        int next = currentItem == total - 1 ? 0 : currentItem + 1;
        viewPager.setCurrentItem(next);
    }

    public static void goPrevious(ViewPager viewPager) {
        if (viewPager == null || viewPager.getAdapter() == null) return;
        int currentItem = viewPager.getCurrentItem();
        int total = viewPager.getAdapter().getCount();
        int previous = currentItem == 0 ? total - 1 : currentItem - 1;
        viewPager.setCurrentItem(previous);
    }

    public static Uri createTemp(Context context) {
        String name = String.format("%s.png", UUID.randomUUID().toString().trim());
        File file = new File(context.getFilesDir(), name);
        return FileProvider.getUriForFile(context, context.getString(R.string.provider_authorities), file);
    }

    public static Intent createTakePhotoIntent(Context context, Uri tempUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
        if (list != null && !list.isEmpty()) {
            for (ResolveInfo info : list) {
                context.grantUriPermission(info.activityInfo.packageName, tempUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            return intent;
        }
        return null;
    }

    public static Intent createPickPhotoIntent(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (context.getPackageManager().resolveActivity(intent, 0) != null) {
            return intent;
        }
        return null;
    }
}
