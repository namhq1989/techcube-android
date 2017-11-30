package com.zodyapp.qrcode.ui.base;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.recyclerview.extensions.ListAdapterConfig;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by vinhnguyen.it.vn on 2017, September 22
 */

public abstract class LoadMoreAdapter<T, VH extends RecyclerView.ViewHolder> extends PagedListAdapter<T, RecyclerView.ViewHolder> {

    private boolean showLoadMore;

    protected LoadMoreAdapter(@NonNull DiffCallback<T> diffCallback) {
        super(diffCallback);
    }

    protected LoadMoreAdapter(@NonNull ListAdapterConfig<T> config) {
        super(config);
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateVH(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //noinspection unchecked
        onBindVH((VH) holder, position);
    }

    public abstract VH onCreateVH(ViewGroup parent, int viewType);

    public abstract void onBindVH(VH holder, int position);

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        return count == 0 ? 0 : count + 1;
    }
}
