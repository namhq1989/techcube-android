package com.imed.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.imed.model.Area;
import com.imed.model.History;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.NumberFormat;

/**
 * Created by vinhnguyen.it.vn on 2017, June 12
 */

public class DataBindingAdapter {

    @BindingAdapter("android:src")
    public static void setImageResource(ImageButton view, int resource) {
        view.setImageResource(resource);
    }

    @BindingAdapter("image")
    public static void setImageURI(SimpleDraweeView view, @Nullable String uriString) {
        if (uriString == null) {
            view.setImageURI((String) null);
        } else {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params != null && params.width > 0 && params.height > 0) {
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uriString))
                        .setResizeOptions(new ResizeOptions(params.width, params.height))
                        .setRotationOptions(RotationOptions.autoRotate())
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(view.getController())
                        .setImageRequest(request)
                        .build();
                view.setController(controller);
            } else {
                view.setImageURI(uriString);
            }
        }
    }

    @BindingAdapter({"image", "default"})
    public static void setImageURI(SimpleDraweeView view, @Nullable String uriString, @Nullable Drawable defaultResource) {
        if (uriString != null && uriString.length() > 0) {
            view.setImageURI(uriString);
        } else {
            view.setImageDrawable(defaultResource);
        }
    }

    @BindingAdapter("number")
    public static void setNumberFormatted(TextView textView, double value) {
        textView.setText(NumberFormat.getInstance().format(value));
    }

    @BindingAdapter("stringResourceName")
    public static void setText(TextView textView, String resourceName) {
        int id = textView.getResources().getIdentifier(resourceName, "string", textView.getContext().getPackageName());
        if (id > 0) textView.setText(id);
    }

    @BindingAdapter("drawableResourceName")
    public static void setImageResource(ImageView imageView, String resourceName) {
        int id = imageView.getResources().getIdentifier(resourceName, "drawable", imageView.getContext().getPackageName());
        if (id > 0) imageView.setImageResource(id);
    }

    @BindingAdapter("android:text")
    public static void appendText(EditText editText, String text) {
        editText.setText(null);
        if (text != null) editText.append(text);
    }

    @BindingAdapter("invisible")
    public static void invisible(View view, boolean invisible) {
        view.setVisibility(invisible ? View.INVISIBLE : View.VISIBLE);
    }

    @BindingAdapter("gone")
    public static void gone(View view, boolean gone) {
        view.setVisibility(gone ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter("selected")
    public static void setSelected(View view, boolean selected) {
        view.setSelected(selected);
    }


    @BindingAdapter("refreshing")
    public static void setRefreshing(SwipeRefreshLayout refreshLayout, boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    @BindingAdapter("font")
    public static void setFont(TextView textView, String fontName) {
        final Context context = textView.getContext();
        int id = context.getResources().getIdentifier(fontName, "font", context.getPackageName());
        Typeface typeface = ResourcesCompat.getFont(textView.getContext(), id);
        textView.setTypeface(typeface);
    }


    @BindingAdapter("areaTime")
    public static void setTimeOfArea(TextView textView, Area area) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy, HH:mm");
        textView.setText(String.format("%1s - %2s", formatter.print(area.startAt.getTime()), formatter.print(area.endAt.getTime())));
    }

    @BindingAdapter("historyTime")
    public static void setTimeOfHistory(TextView textView, History history) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy, HH:mm");
        textView.setText(formatter.print(history.date.getTime()));
    }
}
