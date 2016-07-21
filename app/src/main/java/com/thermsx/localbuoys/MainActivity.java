package com.thermsx.localbuoys;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.thermsx.localbuoys.api.ApiFactory;
import com.thermsx.localbuoys.api.LocalBuoyService;
import com.thermsx.localbuoys.api.LocationListResponse;
import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.ui.fragment.BrowseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BrowseFragment.BrowseFragmentListener {
    private static final String FRAGMENT_TAG = "items_list_cantainer";

    private static final String TAG = "MainActivity";
    private ProgressBar mProgressBar;
    private BrowseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBrowseFragment();

        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        LocalBuoyService service = ApiFactory.getService();
        Call<LocationListResponse> call = service.getLocationList();
        call.enqueue(new Callback<LocationListResponse>() {
            @Override
            public void onResponse(Call<LocationListResponse> call, Response<LocationListResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                mFragment.setList(response.body().getItems().get(0).getItems());
            }

            @Override
            public void onFailure(Call<LocationListResponse> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void initBrowseFragment() {
        mFragment = getBrowseFragment();
        if (mFragment == null) {
            mFragment = new BrowseFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(
//                    R.animator.slide_in_from_right, R.animator.slide_out_to_left,
//                    R.animator.slide_in_from_left, R.animator.slide_out_to_right);
            transaction.replace(R.id.container, mFragment, FRAGMENT_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private BrowseFragment getBrowseFragment() {
        return (BrowseFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

    @Override
    public void onItemSelected(Item item) {
        Log.d(TAG, "onItemSelected: " + item.getName());
        final BrowseFragment fragment = new BrowseFragment();
        fragment.setList(item.getItems());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
