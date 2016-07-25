package com.thermsx.localbuoys.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TideData {

    @SerializedName("DayName")
    @Expose
    private String mDayName;
    @SerializedName("Day")
    @Expose
    private int mDay;
    @SerializedName("FullDateTime")
    @Expose
    private String mFullDateTime;
    @SerializedName("DateJson")
    @Expose
    private String mDateJson;
    @SerializedName("DateTimeJson")
    @Expose
    private String mDateTimeJson;
    @SerializedName("DateTime")
    @Expose
    private String mDateTime;
    @SerializedName("Value")
    @Expose
    private double mValue;

    public String getDayName() {
        return mDayName;
    }

    public int getDay() {
        return mDay;
    }

    public String getFullDateTime() {
        return mFullDateTime;
    }

    public String getDateJson() {
        return mDateJson;
    }

    public String getDateTimeJson() {
        return mDateTimeJson;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public double getValue() {
        return mValue;
    }
}
