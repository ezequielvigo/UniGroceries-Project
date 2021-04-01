package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.unigroceries.adapter.RecipeAdapter;
import com.example.unigroceries.model.IngredientModel;
import com.example.unigroceries.model.RecipeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IngredientSearchResults extends AppCompatActivity {

    private List<IngredientModel> ingredientModelList;
    private List<RecipeModel> recipeModelList;
    private RecipeAdapter recipeAdapter;
    private JSONArray responseArray;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_search_results);

        //Retrieving the ingredientModel ArrayList through the use of Intents and Bundles
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        ingredientModelList = (ArrayList<IngredientModel>)bundle.getSerializable("ingredientList");
        searchResults();

        //Initialising my progress bar and making it visible
        progressBar = findViewById(R.id.ingredientSearchProgress);
        progressBar.setVisibility(View.VISIBLE);
    }

    //Called to retrieve search results from API
    public void searchResults(){
            RecyclerView recyclerView = findViewById(R.id.ingredientSearchResultView);
            LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(layoutManager);
            ArrayList<RecipeModel> responseRecipeList = new ArrayList<>();

           //The base URL used for ingredient searches
            String URL = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=";


            //Appending all the ingredients retrieved from the previous activity to match the format of the API search
            URL += ingredientModelList.get(0).getName() + ",+";
            for (int i = 1; i < ingredientModelList.size()-1; i++){
                URL+=ingredientModelList.get(i).getName();
                URL+=(",");
            }
            URL+=(ingredientModelList.get(ingredientModelList.size()-1).getName());
            URL+=("&number=30&instructionsRequired=true&apiKey=d6045fc138c141a19e45f291a9891b24");


        //Creating a new requestQueue for the API request
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //This type of search directly returns a jsonArray instead of the previous jsonObject
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //Setting my response array to the retrieved JSONArray
                            responseArray = response;

                            //Looping through the response array and retrieving title, id and image fields to create new RecipeModels
                            for(int i = 0; i < responseArray.length(); i++){
                                JSONObject jsonObject = responseArray.getJSONObject(i);
                                responseRecipeList.add(new RecipeModel(jsonObject.optInt("id"), jsonObject.optString("title") , jsonObject.optString("image")));
                            }

                            //Setting the recipeModelList to what is retrieved from the response then using it to create an adapter and setting it in the recyclerView
                            recipeModelList = responseRecipeList;
                            recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeModelList);
                            recyclerView.setAdapter(recipeAdapter);
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                //Used to listen and specify any errors that might occur
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("The error is:", error.toString());
                    }
                }

        );

        //Commencing the request  by adding it to the requestQueue
        requestQueue.add(jsonArrayRequest);
    }

    //Exits the application
    public void backPressed(View v){
        finish();
    }
}