package com.thermsx.localbuoys.api.response;

import com.google.gson.annotations.SerializedName;
import com.thermsx.localbuoys.model.TidesInfo;

public class TidalGeneralInfoResponse {
    @SerializedName("ReturnValue")
    private TidesInfo mTidesInfo;
    @SerializedName("ResultCode")
    private int mResultCode;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("ResultCodeName")
    private String mResultCodeName;

    public TidesInfo getTidesInfo() {
        return mTidesInfo;
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
