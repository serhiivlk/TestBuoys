package com.thermsx.localbuoys.api;

import com.google.gson.annotations.SerializedName;
import com.thermsx.localbuoys.model.Item;

import java.util.List;

public class LocationListResponse {
    @SerializedName("ReturnValue")
    private List<Item> mReturnValue;
    @SerializedName("Items")
    private List<Item> mItems;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("ResultCodeName")
    private String mResultCodeName;

    public List<Item> getItems() {
        return mItems == null ? mReturnValue : mItems;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public String getResultCodeName() {
        return mResultCodeName;
    }
}
