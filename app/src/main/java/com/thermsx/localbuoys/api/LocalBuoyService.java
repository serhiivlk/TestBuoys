package com.thermsx.localbuoys.api;

import com.thermsx.localbuoys.api.response.BuoyInfoResponse;
import com.thermsx.localbuoys.api.response.LocationListResponse;
import com.thermsx.localbuoys.api.response.MoonPhasesResponse;
import com.thermsx.localbuoys.api.response.TidesDataResponse;
import com.thermsx.localbuoys.api.response.TidesGeneralInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocalBuoyService {
    @GET("GetLocationList")
    Call<LocationListResponse> getLocationList();

    @GET("GetBouyInfo")
    Call<BuoyInfoResponse> getBouyInfo(@Query("locationId") long locationId);

    @GET("GetTidalGeneralInfo")
    Call<TidesGeneralInfoResponse> getTidesGeneralInfo(@Query("locationId") long locationId);

    @GET("GetTidalTidesData")
    Call<TidesDataResponse> getTidesData(@Query("locationId") long locationId);

    /**
     * @param locationId
     * @param onDate     format "MM/dd/yyyy"
     * @return
     */
    @GET("GetMoonPhases")
    Call<MoonPhasesResponse> getMoonPhases(
            @Query("locationId") long locationId,
            @Query("onDate") String onDate
    );
}
