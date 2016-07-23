package com.thermsx.localbuoys.ui.activity;

import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.socks.library.KLog;
import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.databinding.ActivityMainBinding;
import com.thermsx.localbuoys.loader.DataLoader;
import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.provider.table.BrowseContract;
import com.thermsx.localbuoys.ui.fragment.BrowseFragment;

public class MainActivity extends ToolbarActivity implements BrowseFragment.BrowseFragmentListener, LoaderManager.LoaderCallbacks<String> {
    private static final String FRAGMENT_TAG = "items_list_container";
    private static final String SAVED_ITEM_ID = "com.thermsx.localbuoys.ITEM_ID";
    private static final String SAVED_IS_LOADING = "com.thermsx.localbuoys.IS_LOADING";

    private ActivityMainBinding mBinding;
    private boolean mIsLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initializeToolbar();
        initializeFromParams(savedInstanceState);

        getLoaderManager().initLoader(R.id.load_data_loader, null, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        long itemId = getItemId();
        if (itemId != BrowseContract.ROOT_ID) {
            outState.putLong(SAVED_ITEM_ID, itemId);
        }
        outState.putBoolean(SAVED_IS_LOADING, mIsLoading);
        super.onSaveInstanceState(outState);
    }

    private BrowseFragment getBrowseFragment() {
        return (BrowseFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

    @Override
    public void onItemSelected(View view, Item item) {
        long id = item.getLocationId();
        KLog.d("id: " + id + "; isBrowsable: " + item.isBrowsable());
        if (item.isBrowsable()) {
            navigateTo(id);
        }
    }

    @Override
    public void setToolbarTitle(CharSequence title) {
        KLog.d(title);
        if (title == null) {
            title = getString(R.string.app_name);
        }
        setTitle(title);
    }

    private boolean isLoading() {
        return mIsLoading;
    }

    private void setLoading(final boolean isLoading) {
        mIsLoading = isLoading;
        mBinding.setIsLoading(mIsLoading);
    }

    protected void initializeFromParams(Bundle savedInstanceState) {
        long itemId = BrowseContract.ROOT_ID;

        if (savedInstanceState != null) {
            itemId = savedInstanceState.getLong(SAVED_ITEM_ID, BrowseContract.ROOT_ID);
            boolean loading = savedInstanceState.getBoolean(SAVED_IS_LOADING, false);
            setLoading(loading);
        }
        navigateTo(itemId);
        updateToolbar();
    }

    private void navigateTo(long itemId) {
        KLog.d("id: " + itemId);

        BrowseFragment fragment = getBrowseFragment();
        if (fragment == null || fragment.getItemId() != itemId) {
            fragment = new BrowseFragment();
            fragment.setItemId(itemId);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                    R.animator.slide_in_from_left, R.animator.slide_out_to_right);
            transaction.replace(R.id.container, fragment, FRAGMENT_TAG);
            if (itemId > -1) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
        }
    }

    private long getItemId() {
        BrowseFragment fragment = getBrowseFragment();
        if (fragment == null) {
            return BrowseContract.ROOT_ID;
        }
        return fragment.getItemId();
    }

    private Context getContext() {
        return this;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        setLoading(true);
        return new DataLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        setLoading(false);
        KLog.d(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
