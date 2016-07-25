package com.thermsx.localbuoys.ui.fragment.info;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.api.ApiFactory;
import com.thermsx.localbuoys.api.LocalBuoyService;
import com.thermsx.localbuoys.api.response.TidesDataResponse;
import com.thermsx.localbuoys.api.response.TidesGeneralInfoResponse;
import com.thermsx.localbuoys.databinding.FragmentTidesInfoBinding;
import com.thermsx.localbuoys.model.TidesInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TidesFragment extends InfoFragment {
    private FragmentTidesInfoBinding mBinding;
    private LocalBuoyService mService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mService = ApiFactory.getService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tides_info, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initGeneralInfo();
        initData();
    }

    public void initGeneralInfo() {
        Call<TidesGeneralInfoResponse> call = mService.getTidesGeneralInfo(getLocationId());
        call.enqueue(new Callback<TidesGeneralInfoResponse>() {
            @Override
            public void onResponse(Call<TidesGeneralInfoResponse> call, Response<TidesGeneralInfoResponse> response) {
                KLog.d();
                TidesInfo info = response.body().getTidesInfo();
                mBinding.setTideInfo(info);
            }

            @Override
            public void onFailure(Call<TidesGeneralInfoResponse> call, Throwable t) {
                KLog.d();
            }
        });
    }

    public void initData() {
        Call<TidesDataResponse> call = mService.getTidesData(getLocationId());
        call.enqueue(new Callback<TidesDataResponse>() {
            @Override
            public void onResponse(Call<TidesDataResponse> call, Response<TidesDataResponse> response) {

            }

            @Override
            public void onFailure(Call<TidesDataResponse> call, Throwable t) {

            }
        });
    }

}
