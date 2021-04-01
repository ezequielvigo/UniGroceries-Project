package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SearchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
    }

    public void recipeSearch(View v){
        Intent recipeSearchIntent = new Intent(SearchPage.this, RecipeSearch.class);
        startActivity(recipeSearchIntent);
    }

    public void ingredientSearch(View v){
        Intent ingredientSearchIntent = new Intent(SearchPage.this, IngredientSearch.class);
        startActivity(ingredientSearchIntent);
    }

    public void backPressed(View v){
        finish();
    }
}