package com.thermsx.localbuoys.api;

import com.thermsx.localbuoys.api.response.BaseResponse;
import com.thermsx.localbuoys.api.response.LocationListResponse;
import com.thermsx.localbuoys.model.BuoyInfo;
import com.thermsx.localbuoys.model.MoonPhasesInfo;
import com.thermsx.localbuoys.model.TidesInfo;
import com.thermsx.localbuoys.model.TidesReturnValue;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocalBuoyService {
    @GET("GetLocationList")
    Call<LocationListResponse> getLocationList();

    @GET("GetBouyInfo")
    Call<BaseResponse<BuoyInfo>> getBouyInfo(@Query("locationId") long locationId);

    @GET("GetTidalGeneralInfo")
    Call<BaseResponse<TidesInfo>> getTidesGeneralInfo(@Query("locationId") long locationId);

    @GET("GetTidalTidesData")
    Call<BaseResponse<TidesReturnValue>> getTidesData(@Query("locationId") long locationId);

    /**
     * @param locationId
     * @param onDate     format "MM/dd/yyyy"
     * @return
     */
    @GET("GetMoonPhases")
    Call<BaseResponse<MoonPhasesInfo>> getMoonPhases(
            @Query("locationId") long locationId,
            @Query("onDate") String onDate
    );
}
