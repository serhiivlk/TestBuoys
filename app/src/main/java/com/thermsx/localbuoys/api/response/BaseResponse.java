package com.thermsx.localbuoys.api.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    public static final int CODE_SUCCESS = 0;

    @SerializedName("ReturnValue")
    private T mReturnValue;
    @SerializedName("ResultCode")
    private int mResultCode;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("ResultCodeName")
    private String mResultCodeName;

    public boolean isSuccess() {
        return mResultCode == CODE_SUCCESS;
    }

    public T getReturnValue() {
        return mReturnValue;
    }

    public int getResultCode() {
        return mResultCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public String getResultCodeName() {
        return mResultCodeName;
    }
}
