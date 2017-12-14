package com.imed.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.imed.R;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */

public abstract class SpinnerAdapter<T> extends ArrayAdapter<String> {


    private final String hint;
    private final List<T> items;

    public SpinnerAdapter(@NonNull Context context, String hint, List<T> items) {
        super(context, R.layout.support_simple_spinner_dropdown_item);
        this.hint = hint;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        textView.setTextColor(ContextCompat.getColor(getContext(), position == 0 ? R.color.blackWithAlpha : R.color.black));
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setTextColor(ContextCompat.getColor(getContext(), position == 0 ? R.color.blackWithAlpha : R.color.black));
        return textView;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() + 1 : 1;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        if (position == 0) {
            return hint;
        }
        return transfer(getItemAt(position));
    }

    @Override
    public boolean isEnabled(int position) {
        return position > 0;
    }

    public T getItemAt(int position) {
        if (position == 0) return null;
        return items.get(position - 1);
    }

    public abstract String transfer(T item);
}
