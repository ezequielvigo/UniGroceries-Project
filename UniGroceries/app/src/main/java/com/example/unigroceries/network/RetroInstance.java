package com.example.unigroceries.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    public static String BASE_URL = "https://api.spoonacular.com";
           // /recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=d6045fc138c141a19e45f291a9891b24";

    private static Retrofit retroFit;

    public static Retrofit getRetroClient(){

        if(retroFit == null){
            retroFit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retroFit;
    }
}
