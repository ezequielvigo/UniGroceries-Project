package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private TextView titleView, sourceView, portionView, durationView;
    private ImageView recipeImage;
    private String recipeTitle, recipeSource, recipePortions, recipeDuration;
    private IngredientAdapter ingredientAdapter;

    private List<IngredientModel> recipeIngredients;
    private JSONArray ingredientsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        titleView = findViewById(R.id.recipeTitleView);
        sourceView = findViewById(R.id.recipeSource);
        portionView = findViewById(R.id.portionsView);
        durationView = findViewById(R.id.readyIn);
        recipeImage = findViewById(R.id.recipeImageView);

        recipeId = this.getIntent().getIntExtra("id", 0);

        requestRecipeInfo(recipeId);
    }

    public void requestRecipeInfo(int recipeId){
        RecyclerView ingredientRecyclerView = findViewById(R.id.ingredientRecyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        ingredientRecyclerView.setLayoutManager(layoutManager);

        String URL = " https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=c957b6816ba048139fbc25a67d2cff33";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            try {
                                Picasso.get().load((String) response.get("image")).into(recipeImage);
                            }catch(Exception e){
                                recipeImage.setImageResource(R.drawable.nopicture);
                            }

                            titleView.setText((String)response.get("title"));
                            recipeDuration = Integer.toString((Integer)response.get("readyInMinutes"));
                            recipePortions = Integer.toString((Integer)response.get("servings"));

                            sourceView.setText("Recipe by: "+(String)response.get("sourceName"));
                            durationView.setText("Ready in: " +recipeDuration+" minutes");
                            portionView.setText("Makes: "+recipePortions+" servings");

                            ingredientsArray = (JSONArray)response.get("extendedIngredients");
                            recipeIngredients = new ArrayList<>();
                            for(int i = 0; i<ingredientsArray.length(); i++){
                                JSONObject ingredient;
                                ingredient = ingredientsArray.getJSONObject(i);
                                recipeIngredients.add(new IngredientModel(ingredient.optString("original")));
                            }

                            ingredientAdapter = new IngredientAdapter(getApplicationContext(), recipeIngredients);
                            ingredientRecyclerView.setAdapter(ingredientAdapter);
                        }catch (Exception e){

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

    }


    public void backPressed(View v){
        finish();
    }
}