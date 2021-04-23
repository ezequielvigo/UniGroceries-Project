package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.unigroceries.adapter.IngredientAdapter;
import com.example.unigroceries.adapter.RecipeAdapter;
import com.example.unigroceries.model.IngredientModel;
import com.example.unigroceries.model.RecipeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SingleRecipe extends AppCompatActivity {

    private int recipeId;
    private TextView titleView, sourceView, portionView, durationView, fullRecipeView;
    private ImageView recipeImage;
    private String recipeTitle, recipeSource, recipePortions, recipeDuration;
    private IngredientAdapter ingredientAdapter;

    private List<IngredientModel> recipeIngredients;
    private JSONArray ingredientsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        //Setting all the views to their respective fields using IDs
        titleView = findViewById(R.id.recipeTitleView);
        sourceView = findViewById(R.id.recipeSource);
        portionView = findViewById(R.id.portionsView);
        durationView = findViewById(R.id.readyIn);
        fullRecipeView = findViewById(R.id.fullRecipeView);
        recipeImage = findViewById(R.id.recipeImageView);

        //Retrieving the correct recipe ID from the Intent
        recipeId = this.getIntent().getIntExtra("id", 0);

        //Request the information about the recipe
        requestRecipeInfo(recipeId);
    }

    public void requestRecipeInfo(int recipeId){
        //Initialises and sets the recyclerView and Layout Manager
        RecyclerView ingredientRecyclerView = findViewById(R.id.ingredientRecyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        ingredientRecyclerView.setLayoutManager(layoutManager);

        //Generates a URL consisting of the API recipe request, our recipeId and the API jey
        String URL = " https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=c957b6816ba048139fbc25a67d2cff33";

        //Creates a new requestQueue and a JsonObjectRequest
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            //Due to possibility of failure, wrapping the Picasso image loader in a try /catch
                            try {
                                Picasso.get().load((String) response.get("image")).into(recipeImage);
                            }catch(Exception e){
                                recipeImage.setImageResource(R.drawable.nopicture);
                            }

                            //Sets the text of all Views to their respective values from the RecipeModel retrieved
                            titleView.setText((String)response.get("title"));
                            recipeDuration = Integer.toString((Integer)response.get("readyInMinutes"));
                            recipePortions = Integer.toString((Integer)response.get("servings"));

                            sourceView.setText("Recipe by: "+(String)response.get("sourceName"));
                            durationView.setText("Ready in: " +recipeDuration+" minutes");
                            portionView.setText("Makes: "+recipePortions+" servings");

                            //If the user clicks on the full recipe section, it opens a browser tab navigating to that URL
                            String url = (String)response.get("sourceUrl");
                            fullRecipeView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(url));
                                    startActivity(intent);
                                }
                            });

                            //Retrieves a list of ingredients in a JSONArray from our response
                            ingredientsArray = (JSONArray)response.get("extendedIngredients");
                            recipeIngredients = new ArrayList<>();
                            //Loops through creating an ingredientModel for every Ingredient and adding it to the list
                            for(int i = 0; i<ingredientsArray.length(); i++){
                                JSONObject ingredient;
                                ingredient = ingredientsArray.getJSONObject(i);
                                recipeIngredients.add(new IngredientModel(ingredient.optString("original")));
                            }

                            //Creates and sets the adapter for the recyclerView using our ingredient list
                            ingredientAdapter = new IngredientAdapter(getApplicationContext(), recipeIngredients);
                            ingredientRecyclerView.setAdapter(ingredientAdapter);
                        }catch (Exception e){

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //For debugging purposes, displays error
                        Log.i("The error is:", error.toString());
                    }
                }

        );
        //Adds the request to the request queue
        requestQueue.add(jsonObjectRequest);

    }

    //If the user presses back if ends the activity
    public void backPressed(View v){
        finish();
    }
}