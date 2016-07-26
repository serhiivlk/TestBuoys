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
import com.thermsx.localbuoys.api.response.BaseResponse;
import com.thermsx.localbuoys.databinding.FragmentBuoysInfoBinding;
import com.thermsx.localbuoys.model.BuoyInfo;
import com.thermsx.localbuoys.util.CallbackAdapter;

import retrofit2.Call;

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
        Call<BaseResponse<BuoyInfo>> call = service.getBouyInfo(getLocationId());
        call.enqueue(new CallbackAdapter<BaseResponse<BuoyInfo>>() {
            @Override
            public void onSuccess(Call<BaseResponse<BuoyInfo>> call, BaseResponse<BuoyInfo> response) {
                super.onSuccess(call, response);
                KLog.d();
                mBinding.setBuoyInfo(response.getReturnValue());
            }
        });

    }
}
