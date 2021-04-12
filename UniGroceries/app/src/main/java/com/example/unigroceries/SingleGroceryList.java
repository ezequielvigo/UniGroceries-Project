package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.unigroceries.adapter.IngredientAdapter;
import com.example.unigroceries.model.GroceryListModel;
import com.example.unigroceries.model.IngredientModel;

import java.util.ArrayList;
import java.util.List;

public class SingleGroceryList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IngredientAdapter ingredientAdapter;
    private List<IngredientModel> ingredientModelList;
    private TextView failedToRetrieve, groceryListTitle, groceryListDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_grocery_list);

        //Setting all the view items to their respective IDs
        recyclerView = findViewById(R.id.singleGroceryListRecyclerView);
        failedToRetrieve = findViewById(R.id.failedToRetrieve);
        groceryListDate = findViewById(R.id.singleListDate);
        groceryListTitle = findViewById(R.id.singleListTitle);

        //Failure message is initially invisible
        failedToRetrieve.setVisibility(View.INVISIBLE);

        //Retrieving the grocery list from the intent
        Intent intent = this.getIntent();
        final GroceryListModel groceryList = (GroceryListModel) intent.getSerializableExtra("GroceryList");

        //Checking if the grocery list that was passed was empty
        if(groceryList != null) {
            //Setting the views to their respective values from the groceryList
            groceryListDate.setText(groceryList.getDate());
            groceryListTitle.setText(groceryList.getTitle());
            //If it isn't empty, it retrieves List<IngredientModel> from the ingredients contained
            ingredientModelList = ingredientsFromList(groceryList);
            LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
            //Uses the List<IngredientModel> to create the adapter and set it to the recyclerView
            ingredientAdapter = new IngredientAdapter(this, ingredientModelList);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(ingredientAdapter);
        }else{
            //If the groceryList is null, it displays the failure message
            failedToRetrieve.setVisibility(View.VISIBLE);
        }
    }

    //Finishes the activity, returns to previous activity
    public void backPressed(View v){
        finish();
    }

    //This method loops through all ingredients in GroceryListModel, creating IngredientModels out of them and adding them to a List<IngredientModel>
    public List<IngredientModel> ingredientsFromList(GroceryListModel groceryListModel){
        //Creates new list, retrieves ArrayList<String> of ingredients from GroceryListModel
        List<IngredientModel> ingredientModels = new ArrayList<>();
        ArrayList<String> ingredientList = groceryListModel.getGroceryList();

        //Loops through, creating IngredientModel from each ingredient and adding it to the ingredientModels list
        for(int i = 0; i<ingredientList.size(); i++ ){
            IngredientModel ingredient = new IngredientModel(ingredientList.get(i));
            ingredientModels.add(ingredient);
        }

        return ingredientModels;
    }
}