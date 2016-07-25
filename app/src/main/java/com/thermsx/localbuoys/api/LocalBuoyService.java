package com.thermsx.localbuoys.api;

import com.thermsx.localbuoys.api.response.BuoyInfoResponse;
import com.thermsx.localbuoys.api.response.LocationListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocalBuoyService {
    @GET("GetLocationList")
    Call<LocationListResponse> getLocationList();

    @GET("GetBouyInfo")
    Call<BuoyInfoResponse> getBouyInfo(@Query("locationId") long locationId);
}
