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
import com.thermsx.localbuoys.api.response.BuoyInfoResponse;
import com.thermsx.localbuoys.databinding.FragmentBuoysInfoBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuoysFragment extends InfoFragment {
    private FragmentBuoysInfoBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_buoys_info, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBuoyService service = ApiFactory.getService();
        Call<BuoyInfoResponse> call = service.getBouyInfo(getLocationId());
        call.enqueue(new Callback<BuoyInfoResponse>() {
            @Override
            public void onResponse(Call<BuoyInfoResponse> call, Response<BuoyInfoResponse> response) {
                KLog.d();
                mBinding.setBuoyInfo(response.body().getBuoyInfo());
            }

            @Override
            public void onFailure(Call<BuoyInfoResponse> call, Throwable t) {
                KLog.d();
            }
        });

    }
}
