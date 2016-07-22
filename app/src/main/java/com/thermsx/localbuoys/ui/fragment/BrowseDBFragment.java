package com.thermsx.localbuoys.ui.fragment;

import android.content.ContentUris;
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
import com.thermsx.localbuoys.databinding.FragmentBrowseDbBinding;
import com.thermsx.localbuoys.provider.table.BrowseTable;

public class BrowseDBFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String EXTRA_ITEM_ID = "com.thermsx.localbuoys.item_id";
    private long mItemId;

    private ItemListCursorAdapter mAdapter;

    private FragmentBrowseDbBinding mBinding;

    public static BrowseDBFragment newInstance(long id) {
        KLog.d("id: " + id);
        Bundle args = new Bundle();
        args.putLong(EXTRA_ITEM_ID, id);
        BrowseDBFragment fragment = new BrowseDBFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ItemListCursorAdapter(getContext(), null);

        mItemId = getArguments().getLong(EXTRA_ITEM_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse_db, container, false);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        KLog.d();
        return new CursorLoader(
                getContext(),
                ContentUris.withAppendedId(BrowseTable.CONTENT_URI_BY_PARENT_ID, mItemId),
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

    public interface BrowseFragmentListener {
        void onItemSelected(View view, long id);
    }
}
