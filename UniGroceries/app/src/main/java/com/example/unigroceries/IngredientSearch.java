package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import com.example.unigroceries.adapter.IngredientAdapter;
import com.example.unigroceries.adapter.IngredientSearchAdapter;
import com.example.unigroceries.model.IngredientModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IngredientSearch extends AppCompatActivity {

    private List<IngredientModel> ingredientSearch;
    private EditText searchEditText;
    private RecyclerView ingredientSearchRecyclerView;
    private IngredientSearchAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_search);

        //Initialising our List and our EditText
        ingredientSearch = new ArrayList<IngredientModel>();
        searchEditText = findViewById(R.id.ingredientSearchEt);

        //Initialising recyclerView, layoutManager and ingredientAdapter
        ingredientSearchRecyclerView = findViewById(R.id.ingredientSearchRecyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        ingredientAdapter = new IngredientSearchAdapter(this, ingredientSearch);

        //Setting our layoutManager and Adapter on our recyclerView
        ingredientSearchRecyclerView.setLayoutManager(layoutManager);
        ingredientSearchRecyclerView.setAdapter(ingredientAdapter);

    }

    //If the user presses back, it ends the activity
    public void backPressed(View v){
        finish();
    }

    //If the user submits a new item to the list, it is added to the list and the ingredientAdapter is notified
    public void addItem(View v){
            ingredientSearch.add(new IngredientModel(searchEditText.getText().toString()));
            ingredientAdapter.notifyDataSetChanged();
    }

    //If the user submits their search, this redirects them to the search results page
    public void searchIngredients(View v) {
        //Sets a new ingredientModelArrayList to be our List retrieved from user input
        ArrayList<IngredientModel> ingredientModelArrayList = (ArrayList<IngredientModel>) ingredientSearch;

        //Creates a new intent and bundle and adds this ingredientList as a serializable extra then starts Activity
        Intent searchIngredientIntent = new Intent(IngredientSearch.this, IngredientSearchResults.class);
        Bundle args = new Bundle();
        args.putSerializable("ingredientList", (Serializable)ingredientModelArrayList);
        searchIngredientIntent.putExtra("bundle", args);
        startActivity(searchIngredientIntent);
    }
}