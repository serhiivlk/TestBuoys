package com.thermsx.localbuoys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.thermsx.localbuoys.adapter.ItemListAdapter;
import com.thermsx.localbuoys.api.ApiFactory;
import com.thermsx.localbuoys.api.LocalBuoyService;
import com.thermsx.localbuoys.api.LocationListResponce;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private ItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ItemListAdapter();

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        LocalBuoyService service = ApiFactory.getService();
        Call<LocationListResponce> call = service.getLocationList();
        call.enqueue(new Callback<LocationListResponce>() {
            @Override
            public void onResponse(Call<LocationListResponce> call, Response<LocationListResponce> response) {
                mProgressBar.setVisibility(View.GONE);
                mAdapter.setList(response.body().getItems().get(0).getItems());
            }

            @Override
            public void onFailure(Call<LocationListResponce> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
