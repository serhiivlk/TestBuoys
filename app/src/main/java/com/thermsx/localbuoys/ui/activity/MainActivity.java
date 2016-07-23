package com.thermsx.localbuoys.ui.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.socks.library.KLog;
import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.api.ApiFactory;
import com.thermsx.localbuoys.api.LocalBuoyService;
import com.thermsx.localbuoys.api.LocationListResponse;
import com.thermsx.localbuoys.databinding.ActivityMainBinding;
import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.provider.table.BrowseContract;
import com.thermsx.localbuoys.ui.fragment.BrowseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends ToolbarActivity implements BrowseFragment.BrowseFragmentListener {
    private static final String TAG = "MainActivity";

    private static final String FRAGMENT_TAG = "items_list_container";
    private static final String SAVED_ITEM_ID = "com.thermsx.localbuoys.ITEM_ID";

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initializeToolbar();
        initializeFromParams(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        long itemId = getItemId();
        if (itemId != BrowseContract.ROOT_ID) {
            outState.putLong(SAVED_ITEM_ID, itemId);
        }
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

    private void loadData() {
        mBinding.setIsLoading(true);
        LocalBuoyService service = ApiFactory.getService();
        Call<LocationListResponse> call = service.getLocationList();
        call.enqueue(new Callback<LocationListResponse>() {
            @Override
            public void onResponse(Call<LocationListResponse> call, Response<LocationListResponse> response) {
                mBinding.setIsLoading(false);
                LocationListResponse body = response.body();
                KLog.d(body.getResultCodeName());

                BrowseContract.saveHierarchy(getContext(), body.getRootItem());
            }

            @Override
            public void onFailure(Call<LocationListResponse> call, Throwable t) {
                mBinding.setIsLoading(false);
                KLog.e(t);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void initializeFromParams(Bundle savedInstanceState) {
        long itemId = BrowseContract.ROOT_ID;

        if (savedInstanceState != null) {
            itemId = savedInstanceState.getLong(SAVED_ITEM_ID);
        } else {
            loadData();
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


}
