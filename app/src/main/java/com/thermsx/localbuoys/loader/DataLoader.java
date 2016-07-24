package com.thermsx.localbuoys.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.socks.library.KLog;
import com.thermsx.localbuoys.api.ApiFactory;
import com.thermsx.localbuoys.api.LocalBuoyService;
import com.thermsx.localbuoys.api.LocationListResponse;
import com.thermsx.localbuoys.provider.table.BrowseContract;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class DataLoader extends AsyncTaskLoader<String> {

    private Call<LocationListResponse> mCall;

    public DataLoader(Context context) {
        super(context);
        LocalBuoyService service = ApiFactory.getService();
        mCall = service.getLocationList();
    }

    @Override
    public String loadInBackground() {
        if (!mCall.isExecuted())
            try {
                Response<LocationListResponse> response = mCall.execute();
                LocationListResponse body = response.body();
                KLog.d(body.getResultCodeName());

                BrowseContract.saveHierarchy(getContext(), body.getRootItem());
                return body.getResultCodeName();
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        return null;
    }


    @Override
    protected void onReset() {
        super.onReset();
        mCall.cancel();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
