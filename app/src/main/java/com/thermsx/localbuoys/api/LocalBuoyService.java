package com.thermsx.localbuoys.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocalBuoyService {
    @GET("GetLocationList")
    Call<LocationListResponse> getLocationList();
}
