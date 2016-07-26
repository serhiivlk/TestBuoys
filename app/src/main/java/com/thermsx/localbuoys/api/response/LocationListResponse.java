package com.thermsx.localbuoys.api.response;

import com.thermsx.localbuoys.model.LocationNode;

import java.util.List;

public class LocationListResponse extends BaseResponse<List<LocationNode>> {
    public LocationNode getRootItem() {
        List<LocationNode> value = getReturnValue();
        if (value != null && value.size() > 0) {
            return value.get(0);
        }
        return null;
    }

}
