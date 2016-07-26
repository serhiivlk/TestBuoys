package com.thermsx.localbuoys.util;

import com.socks.library.KLog;
import com.thermsx.localbuoys.api.response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackAdapter<T extends BaseResponse> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        KLog.d(response.code() + " " + response.message());
        T body = response.body();
        if (body == null) {
            onError(call, "Body is null");
            return;
        }
        if (!body.isSuccess()) {
            onError(call, body.getErrorMessage());
            return;
        }
        onSuccess(call, response.body());
    }

    public void onSuccess(Call<T> call, T response) {
        KLog.d();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        KLog.e(t);
    }

    public void onError(Call<T> call, String error) {
        KLog.e(error);
    }
}
