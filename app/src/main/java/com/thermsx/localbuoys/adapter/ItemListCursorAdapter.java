package com.thermsx.localbuoys.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.provider.table.BrowseTable;

public class ItemListCursorAdapter extends CursorRecyclerViewAdapter<ItemListCursorAdapter.ItemViewHolder> {

    public ItemListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, Cursor cursor) {
        Item item = BrowseTable.fromCursor(cursor);
        holder.text.setText(item.getName());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ItemViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
