package com.example.unigroceries.network;

import com.example.unigroceries.model.RecipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {


    @GET("/recipes/search?query={search}&number=30&instructionsRequired=true&apiKey=d6045fc138c141a19e45f291a9891b24")
    Call<List<RecipeModel>> getRecipeList(@Path("search") String search);
}
