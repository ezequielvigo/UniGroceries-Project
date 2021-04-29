package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.unigroceries.adapter.RecipeAdapter;
import com.example.unigroceries.model.RecipeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearch extends AppCompatActivity {

    public EditText recipeName;
    private JSONArray responseArray;
    private List<RecipeModel> recipeModelList;
    private RecipeAdapter recipeAdapter;
    private TextView noResult;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        recipeName = findViewById(R.id.editTextRecipeName);
        noResult = findViewById(R.id.noResult);
        noResult.setVisibility(View.GONE);
        progressBar = findViewById(R.id.recipeSearchProgress);
        progressBar.setVisibility(View.GONE);
    }

    public void searchRecipe(View v){

        //Making the progressBar visible to indicate the process loading
        progressBar.setVisibility(View.VISIBLE);

        //Initialising recyclerView and setting it to have a Grid Layout with one column
        RecyclerView recyclerView = findViewById(R.id.recipeRecyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        //Retrieving the text inputted by the user into the editText field
        String search = recipeName.getText().toString();
        ArrayList<RecipeModel> responseRecipeList= new ArrayList<>();

        //Making the request URL by incorporating the user search
        String URL="https://api.spoonacular.com/recipes/search?query=" + search
                + "&number=30&instructionsRequired=true&apiKey=d6045fc138c141a19e45f291a9891b24";

        //Used as the base URL for all images
        String imageURL = "https://spoonacular.com/recipeImages/";

        //Creating and initialising requestQueue and the JSON Object request using the previoulsy created URL
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Getting a JSONArray of all the recipes from the API response
                            responseArray = (JSONArray) response.get("results");

                            //Looping through this array retrieving id, title and image to create a RecipeModel object for each Recipe
                            for(int i = 0; i < responseArray.length(); i++){
                                JSONObject jsonObject = responseArray.getJSONObject(i);
                                Log.d("Imageurl", jsonObject.optString("image"));
                                responseRecipeList.add(new RecipeModel(jsonObject.optInt("id"),
                                        jsonObject.optString("title") ,
                                        imageURL + jsonObject.optString("image")));
                            }

                            //Setting my previously created array to the contents of responseRecipeList containing all recipes
                            recipeModelList = responseRecipeList;

                            //Using that to create a recipeAdapter and setting it in the recyclerView
                            recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeModelList);
                            recyclerView.setAdapter(recipeAdapter);

                            //Making the progressBar invisible again
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                //Listening for errors and printing out said error.
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("The error is:", error.toString());

                        //Showing the textView saying "No result" and making progress bar invisible again
                        progressBar.setVisibility(View.GONE);
                        noResult.setVisibility(View.VISIBLE);
                    }
                }

        );

        //Adding my JSONObject request to my requestQueue
        requestQueue.add(jsonObjectRequest);
    }

    //Back button was pressed so it exits the activity
    public void backPressed(View v){
        finish();
    }


}