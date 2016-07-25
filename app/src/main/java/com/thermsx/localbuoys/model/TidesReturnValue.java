package com.thermsx.localbuoys.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TidesReturnValue {

    @SerializedName("Header")
    @Expose
    private Object mHeader;
    @SerializedName("CurrentDate")
    @Expose
    private String mCurrentDate;
    @SerializedName("CurrentDateJson")
    @Expose
    private String mCurrentDateJson;
    @SerializedName("CurrentDateText")
    @Expose
    private String mCurrentDateText;
    @SerializedName("TideDatas")
    private List<TideData> mTideDatas;

    public Object getHeader() {
        return mHeader;
    }

    public String getCurrentDate() {
        return mCurrentDate;
    }

    public String getCurrentDateJson() {
        return mCurrentDateJson;
    }

    public String getCurrentDateText() {
        return mCurrentDateText;
    }

    public List<TideData> getTideDatas() {
        return mTideDatas;
    }
}
