package com.thermsx.localbuoys.adapter;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.databinding.ListItemBrowseBinding;
import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.provider.table.BrowseContract;

public class ItemListCursorAdapter extends CursorRecyclerViewAdapter<ItemListCursorAdapter.ItemViewHolder> {
    private OnItemClickListener mOnItemClickListener;

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Item item);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ListItemBrowseBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            KLog.d();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, getLayoutPosition(), binding.getItem());
            }
        }
    }

}
