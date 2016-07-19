package com.thermsx.localbuoys.api;

import com.thermsx.localbuoys.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private static final OkHttpClient.Builder OK_HTTP_BUILDER = new OkHttpClient.Builder();
    private static final HttpLoggingInterceptor LOGGING_INTERCEPTOR =
            new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);

    private static final Retrofit.Builder RETROFIT_BUILDER = new Retrofit.Builder();

    static {
        LOGGING_INTERCEPTOR.setLevel(HttpLoggingInterceptor.Level.BODY);
        OK_HTTP_BUILDER.addInterceptor(LOGGING_INTERCEPTOR);

        RETROFIT_BUILDER.addConverterFactory(GsonConverterFactory.create());
        RETROFIT_BUILDER.baseUrl(BuildConfig.API_URL);
    }

    public static LocalBuoyService getService() {
        Retrofit retrofit = RETROFIT_BUILDER
                .client(OK_HTTP_BUILDER.build())
                .build();
        return retrofit.create(LocalBuoyService.class);
    }
}
