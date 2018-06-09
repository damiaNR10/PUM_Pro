package com.example.gradyle.projekt.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static ApiClient getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=349aa6e5a5d917354f80486d6f63804f")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiClient.class);
    }
}
