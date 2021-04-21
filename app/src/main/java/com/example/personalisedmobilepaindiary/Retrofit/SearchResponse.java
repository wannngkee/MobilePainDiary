package com.example.personalisedmobilepaindiary.Retrofit;

import com.example.personalisedmobilepaindiary.Retrofit.Main;
import com.google.gson.annotations.SerializedName;

public class SearchResponse {
    @SerializedName("main")
    public Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(float temp, int pressure, int humidity){
        main.setTemp(temp);
        main.setHumidity(humidity);
        main.setPressure(pressure);
    }
}
