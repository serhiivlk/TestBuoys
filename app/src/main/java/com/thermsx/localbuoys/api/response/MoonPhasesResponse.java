package com.thermsx.localbuoys.api.response;

import com.google.gson.annotations.SerializedName;
import com.thermsx.localbuoys.model.MoonPhasesInfo;

public class MoonPhasesResponse {
    @SerializedName("ReturnValue")
    private MoonPhasesInfo mReturnValue;
    @SerializedName("ResultCode")
    private int mResultCode;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("ResultCodeName")
    private String mResultCodeName;

    public MoonPhasesInfo getReturnValue() {
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
