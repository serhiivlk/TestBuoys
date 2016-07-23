package com.thermsx.localbuoys.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {
    public static final int TYPE_BROWSE = 0;
    public static final int TYPE_ITEM = 2;
    @SerializedName("VisibleOnBuoys")
    public boolean mVisibleOnBuoys;
    @SerializedName("VisibleOnWeatherForecast")
    public boolean mVisibleOnWeatherForecast;
    @SerializedName("VisibleOnMarineForecast")
    public boolean mVisibleOnMarineForecast;
    @SerializedName("VisibleOnTides")
    public boolean mVisibleOnTides;
    @SerializedName("VisibleOnMoonPhases")
    public boolean mVisibleOnMoonPhases;
    @SerializedName("VisibleOnRadar")
    public boolean mVisibleOnRadar;
    @SerializedName("VisibleOnWavewatch")
    public boolean mVisibleOnWavewatch;
    @SerializedName("VisibleOnSeaSurfaceTemp")
    public boolean mVisibleOnSeaSurfaceTemp;
    @SerializedName("LocationId")
    private long mLocationId;
    @SerializedName("ParentId")
    private long mParentId;
    @SerializedName("ItemType")
    private int mType;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Items")
    private List<Item> mItems;

    public boolean isBrowsable() {
        return mType != TYPE_ITEM;
    }

    public long getLocationId() {
        return mLocationId;
    }

    public void setLocationId(long locationId) {
        mLocationId = locationId;
    }

    public long getParentId() {
        return mParentId;
    }

    public void setParentId(long parentId) {
        mParentId = parentId;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public boolean isVisibleOnBuoys() {
        return mVisibleOnBuoys;
    }

    public void setVisibleOnBuoys(boolean visibleOnBuoys) {
        mVisibleOnBuoys = visibleOnBuoys;
    }

    public boolean isVisibleOnWeatherForecast() {
        return mVisibleOnWeatherForecast;
    }

    public void setVisibleOnWeatherForecast(boolean visibleOnWeatherForecast) {
        mVisibleOnWeatherForecast = visibleOnWeatherForecast;
    }

    public boolean isVisibleOnMarineForecast() {
        return mVisibleOnMarineForecast;
    }

    public void setVisibleOnMarineForecast(boolean visibleOnMarineForecast) {
        mVisibleOnMarineForecast = visibleOnMarineForecast;
    }

    public boolean isVisibleOnTides() {
        return mVisibleOnTides;
    }

    public void setVisibleOnTides(boolean visibleOnTides) {
        mVisibleOnTides = visibleOnTides;
    }

    public boolean isVisibleOnMoonPhases() {
        return mVisibleOnMoonPhases;
    }

    public void setVisibleOnMoonPhases(boolean visibleOnMoonPhases) {
        mVisibleOnMoonPhases = visibleOnMoonPhases;
    }

    public boolean isVisibleOnRadar() {
        return mVisibleOnRadar;
    }

    public void setVisibleOnRadar(boolean visibleOnRadar) {
        mVisibleOnRadar = visibleOnRadar;
    }

    public boolean isVisibleOnWavewatch() {
        return mVisibleOnWavewatch;
    }

    public void setVisibleOnWavewatch(boolean visibleOnWavewatch) {
        mVisibleOnWavewatch = visibleOnWavewatch;
    }

    public boolean isVisibleOnSeaSurfaceTemp() {
        return mVisibleOnSeaSurfaceTemp;
    }

    public void setVisibleOnSeaSurfaceTemp(boolean visibleOnSeaSurfaceTemp) {
        mVisibleOnSeaSurfaceTemp = visibleOnSeaSurfaceTemp;
    }
}
