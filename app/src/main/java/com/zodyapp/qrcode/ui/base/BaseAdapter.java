package com.zodyapp.qrcode.ui.base;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.recyclerview.extensions.ListAdapterConfig;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, October 11
 */

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends PagedListAdapter<T, VH> {

    private OnItemClickListener<T> onItemClickListener;
    private T selectedItem;

    public BaseAdapter(@NonNull DiffCallback<T> diffCallback) {
        super(diffCallback);
    }

    public BaseAdapter(@NonNull ListAdapterConfig<T> config) {
        super(config);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.itemView.setOnClickListener(v -> {
            int holderPosition = holder.getAdapterPosition();
            if (holderPosition >= 0 && holderPosition < getItemCount()) {
                T item = getItem(holder.getAdapterPosition());
                if (item != null && onItemClickListener != null) {
                    onItemClickListener.onItemClick(item, holderPosition);
                }
            }
        });

        holder.itemView.setSelected(isItemSelected(position));
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

    public boolean isItemSelected(int position) {
        T item = getItem(position);
        return item != null && item == selectedItem;
    }
}
