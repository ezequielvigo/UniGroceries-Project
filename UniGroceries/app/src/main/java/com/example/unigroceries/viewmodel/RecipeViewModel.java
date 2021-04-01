package com.example.unigroceries.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.unigroceries.Recipe;
import com.example.unigroceries.model.RecipeModel;
import com.example.unigroceries.network.APIService;
import com.example.unigroceries.network.RetroInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeViewModel extends ViewModel {

    private List<RecipeModel> recipeList;
    private JSONArray responseArray;

    public RecipeViewModel(){


    }

    /*
    public MutableLiveData<List<RecipeModel>> getRecipeListObserver() {
        return recipeList;
    }
     */

    public void makeApiCall(String search){
        /*
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<List<RecipeModel>> call = apiService.getRecipeList(search);
        call.enqueue(new Callback<List<RecipeModel>>() {
            @Override
            public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {
                responseArray = (JSONArray) response.get("results");

                for(int i = 0; i < responseArray.length(); i++){
                    JSONObject jsonObject = responseArray.getJSONObject(i);
                    recipesArrayList.add(new Recipe(jsonObject.optInt("id"), jsonObject.optString("title"), jsonObject.optString("image")));
                }
                recipeList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
                recipeList.postValue(null);
            }
        });


        //Making the request URL by incorporating the user search
        String URL="https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=d6045fc138c141a19e45f291a9891b24";

        RequestQueue requestQueue = Volley.newRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            responseArray = (JSONArray) response.get("results");


                            for(int i = 0; i < responseArray.length(); i++){
                                JSONObject jsonObject = responseArray.getJSONObject(i);
                                recipeList.add(new RecipeModel(jsonObject.optInt("id"), jsonObject.optString("title"), jsonObject.optString("image")));
                            }
                            Log.i("The search res is:", String.valueOf(responseArray));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("The error is:", error.toString());
                    }
                }

        );
        requestQueue.add(jsonObjectRequest);

         */

    }


}
