package com.thermsx.localbuoys.ui.fragment.info;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.api.ApiFactory;
import com.thermsx.localbuoys.api.LocalBuoyService;
import com.thermsx.localbuoys.api.response.MoonPhasesResponse;
import com.thermsx.localbuoys.databinding.FragmentMoonPhasesInfoBinding;
import com.thermsx.localbuoys.model.MoonPhasesInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoonPhasesFragment extends InfoFragment {
    private static final DateFormat FORMAT = new SimpleDateFormat("MM/DD/yyyy");

    private FragmentMoonPhasesInfoBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_moon_phases_info, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String date = FORMAT.format(new Date());
        LocalBuoyService service = ApiFactory.getService();
        Call<MoonPhasesResponse> call = service.getMoonPhases(getLocationId(), date);
        call.enqueue(new Callback<MoonPhasesResponse>() {
            @Override
            public void onResponse(Call<MoonPhasesResponse> call, Response<MoonPhasesResponse> response) {
                KLog.d();
                MoonPhasesInfo returnValue = response.body().getReturnValue();
                mBinding.setMoonInfo(returnValue);

                List<MoonPhasesInfo.MoonInfo> moonInfo = returnValue.getMoonInfo();
                if (!moonInfo.isEmpty()) {
                    fillMoonInfo(moonInfo);
                }
            }

            @Override
            public void onFailure(Call<MoonPhasesResponse> call, Throwable t) {
                KLog.e(t);
            }
        });
    }

    private void fillMoonInfo(List<MoonPhasesInfo.MoonInfo> moonInfos) {
        mBinding.gridInfo.addView(initTitle("Moon info"));
        for (int i = 0; i < moonInfos.size(); i++) {
            MoonPhasesInfo.MoonInfo moonInfo = moonInfos.get(i);
            fillRow(moonInfo);
        }
    }

    private void fillRow(MoonPhasesInfo.MoonInfo moonInfo) {
        mBinding.gridInfo
                .addView(initRowTextView(moonInfo.getName(), Gravity.RIGHT));
        mBinding.gridInfo.addView(initRowTextView(
                moonInfo.getTime() + " " + moonInfo.getNote(),
                Gravity.LEFT
        ));
    }

    private TextView initTitle(String text) {
        TextView textView = new TextView(getActivity());
        textView.setText(text);
        setTextAppearance(textView, android.R.style.TextAppearance_Medium);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.columnSpec = GridLayout.spec(0, 2);
        params.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setLayoutParams(params);
        return textView;
    }

    private TextView initRowTextView(String text, int gravity) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        TextView textView = new TextView(getActivity());
        if (gravity == Gravity.RIGHT) {
            int margin = (int) getResources().getDimension(R.dimen.info_mangin);
            params.setMargins(0, 0, margin, 0);
        }
        params.setGravity(gravity);
        textView.setText(text);
        textView.setLayoutParams(params);
        return textView;
    }

    @SuppressWarnings("deprecation")
    private void setTextAppearance(TextView textView, int resId) {
        if (Build.VERSION.SDK_INT < 23) {
            textView.setTextAppearance(getActivity(), resId);
        } else {
            textView.setTextAppearance(resId);
        }
    }
}
