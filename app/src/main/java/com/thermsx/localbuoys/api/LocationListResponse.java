package com.thermsx.localbuoys.api;

import com.google.gson.annotations.SerializedName;
import com.thermsx.localbuoys.model.Item;

import java.util.List;

public class LocationListResponse {
    @SerializedName("ReturnValue")
    private List<Item> mItems;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("ResultCodeName")
    private String mResultCodeName;

    public Item getRootItem() {
        if (mItems != null && mItems.size() > 0) {
            return mItems.get(0);
        }
        return null;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public String getResultCodeName() {
        return mResultCodeName;
    }
}
