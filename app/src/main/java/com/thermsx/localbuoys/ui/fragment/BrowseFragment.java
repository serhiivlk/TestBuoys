package com.thermsx.localbuoys.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.adapter.ItemListCursorAdapter;
import com.thermsx.localbuoys.databinding.FragmentBrowseBinding;
import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.provider.table.BrowseContract;

public class BrowseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, ItemListCursorAdapter.OnItemClickListener {
    private static final String ARG_ITEM_ID = "item_id";

    private ItemListCursorAdapter mAdapter;

    private BrowseFragmentListener mBrowseFragmentListener;

    private FragmentBrowseBinding mBinding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mBrowseFragmentListener = (BrowseFragmentListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException("TVBrowseFragment can only be attached to an activity that " +
                    "implements MediaFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ItemListCursorAdapter(getContext(), null);
        mAdapter.setOnItemClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBinding.recycler.setHasFixedSize(true);
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recycler.setAdapter(mAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(R.id.browse_loader, null, this);
    }

    public long getItemId() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getLong(ARG_ITEM_ID);
        }
        return BrowseContract.ROOT_ID;
    }

    public void setItemId(long id) {
        Bundle args = new Bundle(1);
        args.putLong(ARG_ITEM_ID, id);
        setArguments(args);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        KLog.d();
        return new CursorLoader(
                getContext(),
                BrowseContract.buildUriWithParentId(getItemId()),
                null, null, null, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        KLog.d();
        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        KLog.d();
    }

    @Override
    public void onItemClick(View view, int position, Item item) {
        if (mBrowseFragmentListener != null) {
            mBrowseFragmentListener.onItemSelected(view, item);
        }
    }

    public interface BrowseFragmentListener {
        void onItemSelected(View view, Item item);
    }
}
