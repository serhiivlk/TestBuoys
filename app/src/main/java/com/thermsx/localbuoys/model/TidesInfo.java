package com.thermsx.localbuoys.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TidesInfo {
    @SerializedName("LocationId")
    @Expose
    private int mLocationId;
    @SerializedName("TideId")
    @Expose
    private Object mTideId;
    @SerializedName("TideApiId")
    @Expose
    private Object mTideApiId;
    @SerializedName("TideName")
    @Expose
    private String mTideName;
    @SerializedName("TideLatitude")
    @Expose
    private String mTideLatitude;
    @SerializedName("TideLongitude")
    @Expose
    private String mTideLongitude;
    @SerializedName("TideCoordinate")
    @Expose
    private String mTideCoordinate;
    @SerializedName("CurrentDate")
    @Expose
    private String mCurrentDate;
    @SerializedName("CurrentDateJson")
    @Expose
    private String mCurrentDateJson;
    @SerializedName("CurrentDateText")
    @Expose
    private String mCurrentDateText;

    public int getLocationId() {
        return mLocationId;
    }

    public Object getTideId() {
        return mTideId;
    }

    public Object getTideApiId() {
        return mTideApiId;
    }

    public String getTideName() {
        return mTideName;
    }

    public String getTideLatitude() {
        return mTideLatitude;
    }

    public String getTideLongitude() {
        return mTideLongitude;
    }

    public String getTideCoordinate() {
        return mTideCoordinate;
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
}
