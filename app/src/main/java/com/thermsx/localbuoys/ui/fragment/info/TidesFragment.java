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
import com.thermsx.localbuoys.api.response.TidalGeneralInfoResponse;
import com.thermsx.localbuoys.databinding.FragmentTidesInfoBinding;
import com.thermsx.localbuoys.model.TidesInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TidesFragment extends InfoFragment {
    private FragmentTidesInfoBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tides_info, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBuoyService service = ApiFactory.getService();
        Call<TidalGeneralInfoResponse> call = service.getTidalGeneralInfo(getLocationId());
        call.enqueue(new Callback<TidalGeneralInfoResponse>() {
            @Override
            public void onResponse(Call<TidalGeneralInfoResponse> call, Response<TidalGeneralInfoResponse> response) {
                KLog.d();
                TidesInfo info = response.body().getTidesInfo();
                mBinding.setTideInfo(info);
            }

            @Override
            public void onFailure(Call<TidalGeneralInfoResponse> call, Throwable t) {
                KLog.d();
            }
        });
    }
}
