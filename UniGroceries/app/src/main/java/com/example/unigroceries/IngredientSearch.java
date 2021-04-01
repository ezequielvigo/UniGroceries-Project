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
        ingredientSearch = new ArrayList<IngredientModel>();
        searchEditText = findViewById(R.id.ingredientSearchEt);
        ingredientSearchRecyclerView = findViewById(R.id.ingredientSearchRecyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        ingredientAdapter = new IngredientSearchAdapter(this, ingredientSearch);
        ingredientSearchRecyclerView.setLayoutManager(layoutManager);
        ingredientSearchRecyclerView.setAdapter(ingredientAdapter);

    }


    public void backPressed(View v){
        finish();
    }

    public void addItem(View v){
            ingredientSearch.add(new IngredientModel(searchEditText.getText().toString()));
            ingredientAdapter.notifyDataSetChanged();
    }

    public void searchIngredients(View v) {
        ArrayList<IngredientModel> ingredientModelArrayList = (ArrayList<IngredientModel>) ingredientSearch;
        Intent searchIngredientIntent = new Intent(IngredientSearch.this, IngredientSearchResults.class);
        Bundle args = new Bundle();
        args.putSerializable("ingredientList", (Serializable)ingredientModelArrayList);
        searchIngredientIntent.putExtra("bundle", args);
        startActivity(searchIngredientIntent);
    }
}