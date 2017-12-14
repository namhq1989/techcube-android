package com.imed.ui.history;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.imed.R;
import com.imed.databinding.ItemListHistoryBinding;
import com.imed.databinding.ItemListHistoryStatusBinding;
import com.imed.model.History;
import com.imed.ui.base.DataBindingViewHolder;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, December 15
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private static final int TYPE_STATUS = 1;

    private final String message;
    private final boolean success;
    private final List<History> histories;

    public HistoryAdapter(String message, boolean success, List<History> histories) {
        this.message = message;
        this.success = success;
        this.histories = histories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_STATUS:
                return new StatusViewHolder(inflater, parent);
        }
        return new HistoryViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof StatusViewHolder) {
            ((StatusViewHolder) holder).onBind(success, message);
        } else if (holder instanceof HistoryViewHolder) {
            ((HistoryViewHolder) holder).onBind(histories.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return histories != null ? histories.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_STATUS;
        }
        return super.getItemViewType(position);
    }

    public static class ViewHolder<Binding extends ViewDataBinding> extends DataBindingViewHolder<Binding> {

        public ViewHolder(LayoutInflater inflater, ViewGroup parent, int layout) {
            super(inflater, parent, layout);
        }
    }

    private static class StatusViewHolder extends ViewHolder<ItemListHistoryStatusBinding> {

        public StatusViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.item_list_history_status);
        }

        void onBind(boolean success, String message) {
            binding.setSuccess(success);
            binding.setMessage(message);
            binding.executePendingBindings();
        }
    }

    private static class HistoryViewHolder extends ViewHolder<ItemListHistoryBinding> {

        public HistoryViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.item_list_history);
        }

        void onBind(History history) {
            binding.setHistory(history);
            binding.executePendingBindings();
        }
    }
}
