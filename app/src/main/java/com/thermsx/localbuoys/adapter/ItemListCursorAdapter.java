package com.thermsx.localbuoys.adapter;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.databinding.ListItemBrowseBinding;
import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.provider.table.BrowseContract;

public class ItemListCursorAdapter extends CursorRecyclerViewAdapter<ItemListCursorAdapter.ItemViewHolder> {

    public ItemListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_browse, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, Cursor cursor) {
        Item item = BrowseContract.fromCursor(cursor);
        holder.binding.setItem(item);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ListItemBrowseBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
