package com.imed.ui.area;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.imed.R;
import com.imed.databinding.ItemListAreaBinding;
import com.imed.databinding.ItemListAreaHeaderBinding;
import com.imed.model.Area;
import com.imed.model.CheckCodeResult;
import com.imed.model.Customer;
import com.imed.model.Event;
import com.imed.model.Plan;
import com.imed.ui.base.DataBindingViewHolder;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 1;

    private final CheckCodeResult result;

    private String currentAreaId;

    public AreaAdapter(CheckCodeResult result) {
        this.result = result;
        currentAreaId = result.currentArea;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflater, parent);
        }
        return new AreaViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).onBind(result.customer, result.event, result.plan);
        } else if (holder instanceof AreaViewHolder) {
            Area area = result.areas.get(position - 1);
            ((AreaViewHolder) holder).onBind(area, area.id.equals(currentAreaId));
            ((AreaViewHolder) holder).itemView.setOnClickListener(view -> {
                currentAreaId = area.id;
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return result.areas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        return super.getItemViewType(position);
    }

    public String getCurrentAreaId() {
        return currentAreaId;
    }

    public String getEventId() {
        return result.event.id;
    }

    public static class ViewHolder<Binding extends ViewDataBinding> extends DataBindingViewHolder<Binding> {

        public ViewHolder(LayoutInflater inflater, ViewGroup parent, int layout) {
            super(inflater, parent, layout);
        }
    }

    private static class HeaderViewHolder extends ViewHolder<ItemListAreaHeaderBinding> {

        public HeaderViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.item_list_area_header);
        }

        void onBind(Customer customer, Event event, Plan plan) {
            binding.setCustomer(customer);
            binding.setEvent(event);
            binding.setPlan(plan);
            binding.executePendingBindings();
        }
    }

    private static class AreaViewHolder extends ViewHolder<ItemListAreaBinding> {

        public AreaViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.item_list_area);
        }

        void onBind(Area area, boolean checked) {
            binding.setArea(area);
            binding.textView1.setChecked(checked);
            binding.executePendingBindings();
        }
    }
}
