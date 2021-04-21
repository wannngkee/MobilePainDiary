package com.example.personalisedmobilepaindiary.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("weather")
    Call<SearchResponse> weatherSearch(@Query("q") String city,
                                       @Query("units") String units,
                                       @Query("appid") String API_KEY);


}
