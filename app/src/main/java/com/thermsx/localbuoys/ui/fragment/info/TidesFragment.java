package com.thermsx.localbuoys.ui.fragment.info;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
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
import com.thermsx.localbuoys.databinding.FragmentTidesInfoBinding;
import com.thermsx.localbuoys.model.TideData;
import com.thermsx.localbuoys.model.TidesInfo;
import com.thermsx.localbuoys.model.TidesReturnValue;
import com.thermsx.localbuoys.util.CallbackAdapter;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import retrofit2.Call;

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
        Call<BaseResponse<TidesInfo>> call = mService.getTidesGeneralInfo(getLocationId());
        call.enqueue(new CallbackAdapter<BaseResponse<TidesInfo>>() {
            @Override
            public void onSuccess(Call<BaseResponse<TidesInfo>> call, BaseResponse<TidesInfo> response) {
                super.onSuccess(call, response);
                KLog.d();
                TidesInfo info = response.getReturnValue();
                mBinding.setTideInfo(info);
            }
        });
    }

    public void initData() {
        Call<BaseResponse<TidesReturnValue>> call = mService.getTidesData(getLocationId());
        call.enqueue(new CallbackAdapter<BaseResponse<TidesReturnValue>>() {
            @Override
            public void onSuccess(Call<BaseResponse<TidesReturnValue>> call, BaseResponse<TidesReturnValue> response) {
                super.onSuccess(call, response);
                TidesReturnValue returnValue = response.getReturnValue();
                List<TideData> datas = returnValue.getTideDatas();
                List<PointValue> values = new ArrayList<>();
                for (TideData data : datas) {
                    values.add(new PointValue((float) data.getDay(), data.getValue()));
                }
                Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
                List<Line> lines = new ArrayList<>();
                lines.add(line);

                LineChartData data = new LineChartData();
                data.setLines(lines);

                mBinding.chart.setInteractive(false);
                mBinding.chart.setLineChartData(data);
            }
        });
    }

}
