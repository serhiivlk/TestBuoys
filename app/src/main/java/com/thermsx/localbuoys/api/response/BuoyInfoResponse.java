package com.thermsx.localbuoys.api.response;

import com.google.gson.annotations.SerializedName;
import com.thermsx.localbuoys.model.BuoyInfo;

public class BuoyInfoResponse {
    @SerializedName("ReturnValue")
    private BuoyInfo mBuoyInfo;
    @SerializedName("ResultCode")
    private int mResultCode;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("ResultCodeName")
    private String mResultCodeName;

    public BuoyInfo getBuoyInfo() {
        return mBuoyInfo;
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
