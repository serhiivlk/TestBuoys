package com.thermsx.localbuoys.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuoyInfo {
    @SerializedName("StationId")
    @Expose
    private String mStationId;
    @SerializedName("LocationId")
    @Expose
    private int mLocationId;
    @SerializedName("BuoyId")
    @Expose
    private String mBuoyId;
    @SerializedName("Name")
    @Expose
    private String mName;
    @SerializedName("SwellHeight")
    @Expose
    private String mSwellHeight;
    @SerializedName("SwellPeriod")
    @Expose
    private String mSwellPeriod;
    @SerializedName("SwellDirection")
    @Expose
    private String mSwellDirection;
    @SerializedName("SwellDirectionDeg")
    @Expose
    private String mSwellDirectionDeg;
    @SerializedName("Coordinate")
    @Expose
    private String mCoordinate;
    @SerializedName("DateTimeLatestReading")
    @Expose
    private String mDateTimeLatestReading;
    @SerializedName("DateTimeLatestReadingForDisplay")
    @Expose
    private String mDateTimeLatestReadingForDisplay;
    @SerializedName("WindSpeed")
    @Expose
    private String mWindSpeed;
    @SerializedName("WindDirection")
    @Expose
    private String mWindDirection;
    @SerializedName("MeanWaveDirection")
    @Expose
    private String mMeanWaveDirection;
    @SerializedName("WindDirectionPercent")
    @Expose
    private Object mWindDirectionPercent;
    @SerializedName("WindGust")
    @Expose
    private String mWindGust;
    @SerializedName("WaveHeight")
    @Expose
    private String mWaveHeight;
    @SerializedName("WavePeriod")
    @Expose
    private String mWavePeriod;
    @SerializedName("H2OTemp")
    @Expose
    private String mH2OTemp;
    @SerializedName("AirTemp")
    @Expose
    private String mAirTemp;
    @SerializedName("MarineForecast")
    @Expose
    private String mMarineForecast;
    @SerializedName("WeatherForecast")
    @Expose
    private Object mWeatherForecast;
    @SerializedName("WeatherForecastLocationName")
    @Expose
    private Object mWeatherForecastLocationName;
    @SerializedName("SwellCondition")
    @Expose
    private String mSwellCondition;
    @SerializedName("MarineForecastToday")
    @Expose
    private String mMarineForecastToday;

    public String getStationId() {
        return mStationId;
    }

    public int getLocationId() {
        return mLocationId;
    }

    public String getBuoyId() {
        return mBuoyId;
    }

    public String getName() {
        return mName;
    }

    public String getSwellHeight() {
        return mSwellHeight;
    }

    public String getSwellPeriod() {
        return mSwellPeriod;
    }

    public String getSwellDirection() {
        return mSwellDirection;
    }

    public String getSwellDirectionDeg() {
        return mSwellDirectionDeg;
    }

    public String getCoordinate() {
        return mCoordinate;
    }

    public String getDateTimeLatestReading() {
        return mDateTimeLatestReading;
    }

    public String getDateTimeLatestReadingForDisplay() {
        return mDateTimeLatestReadingForDisplay;
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    public String getWindDirection() {
        return mWindDirection;
    }

    public String getMeanWaveDirection() {
        return mMeanWaveDirection;
    }

    public Object getWindDirectionPercent() {
        return mWindDirectionPercent;
    }

    public String getWindGust() {
        return mWindGust;
    }

    public String getWaveHeight() {
        return mWaveHeight;
    }

    public String getWavePeriod() {
        return mWavePeriod;
    }

    public String getH2OTemp() {
        return mH2OTemp;
    }

    public String getAirTemp() {
        return mAirTemp;
    }

    public String getMarineForecast() {
        return mMarineForecast;
    }

    public Object getWeatherForecast() {
        return mWeatherForecast;
    }

    public Object getWeatherForecastLocationName() {
        return mWeatherForecastLocationName;
    }

    public String getSwellCondition() {
        return mSwellCondition;
    }

    public String getMarineForecastToday() {
        return mMarineForecastToday;
    }
}
