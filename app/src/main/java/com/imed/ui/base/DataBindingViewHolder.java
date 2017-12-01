package com.imed.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by vinhnguyen.it.vn on 2017, September 25
 */

public class DataBindingViewHolder<Binding extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public Binding binding;

    public DataBindingViewHolder(ViewGroup parent, int layout) {
        this(LayoutInflater.from(parent.getContext()), parent, layout);
    }

    public DataBindingViewHolder(LayoutInflater inflater, ViewGroup parent, int layout) {
        this(DataBindingUtil.inflate(inflater, layout, parent, false));
    }


    public DataBindingViewHolder(Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
