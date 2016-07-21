package com.thermsx.localbuoys.ui.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.adapter.ItemListAdapter;
import com.thermsx.localbuoys.databinding.ItemListBinding;
import com.thermsx.localbuoys.model.Item;

import java.util.List;

public class BrowseFragment extends Fragment implements ItemListAdapter.OnItemClickListener {
    private ItemListBinding mBinding;

    private ItemListAdapter mAdapter;

    private BrowseFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (BrowseFragmentListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ItemListAdapter();
        mAdapter.setOnClickListener(this);
    }

    public void setList(@NonNull List<Item> list) {
        mAdapter.setList(list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBinding.recycler.setHasFixedSize(true);
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recycler.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(Item item) {
        if (item.getType() != 2) {
            if (mListener != null) {
                mListener.onItemSelected(item);
            }
        } else {
            Toast.makeText(getContext(), "Item", Toast.LENGTH_SHORT).show();
        }
    }

    public interface BrowseFragmentListener {
        void onItemSelected(Item item);
    }
}
