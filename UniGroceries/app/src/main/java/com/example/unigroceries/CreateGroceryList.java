package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.unigroceries.adapter.IngredientSearchAdapter;
import com.example.unigroceries.model.IngredientModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateGroceryList extends AppCompatActivity {

    private EditText ingredientEditText;
    private RecyclerView recyclerView;
    private IngredientSearchAdapter ingredientSearchAdapter;
    private List<IngredientModel> ingredientModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_grocery_list);
        ingredientEditText = findViewById(R.id.groceryIngredientEt);
        recyclerView = findViewById(R.id.groceryIngredientRecyclerView);
        ingredientModelList = new ArrayList<IngredientModel>();

        ingredientSearchAdapter = new IngredientSearchAdapter(this, ingredientModelList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ingredientSearchAdapter);

    }

    public void viewRecipes(View v){
        Intent intent = new Intent(CreateGroceryList.this, IngredientSearchResults.class);
        Bundle args = new Bundle();
        args.putSerializable("ingredientList", (Serializable)ingredientModelList);
        intent .putExtra("bundle", args);
        startActivity(intent);
    }

    public void addItem(View v){
       IngredientModel ingredientModel = new IngredientModel(ingredientEditText.getText().toString());
       ingredientModelList.add(ingredientModel);
       ingredientSearchAdapter.notifyDataSetChanged();
    }

    public void backPressed(View v){
        finish();
    }
}