package com.thermsx.localbuoys.api;

import com.google.gson.annotations.SerializedName;
import com.thermsx.localbuoys.model.LocationNode;

import java.util.List;

public class LocationListResponse {
    @SerializedName("ReturnValue")
    private List<LocationNode> mLocationNodes;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("ResultCodeName")
    private String mResultCodeName;

    public LocationNode getRootItem() {
        if (mLocationNodes != null && mLocationNodes.size() > 0) {
            return mLocationNodes.get(0);
        }
        return null;
    }

    public List<LocationNode> getLocationNodes() {
        return mLocationNodes;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public String getResultCodeName() {
        return mResultCodeName;
    }
}
