package com.thermsx.localbuoys.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thermsx.localbuoys.model.Item;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    private static final String TAG = "ItemListAdapter";
    private List<Item> mList;

    private OnItemClickListener mOnClickListener;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mList != null) {
            Item item = mList.get(position);
            holder.text.setText(item.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public void setList(List<Item> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;

        public ItemViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) {

                Item item = mList.get(getAdapterPosition());
                Log.d(TAG, "onClick: " + item.getName());
                mOnClickListener.onItemClick(item);
            }
        }
    }
}
