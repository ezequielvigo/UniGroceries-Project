package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GroceryListPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list_page);
    }

    //If the user clicks on the button to create a new grocery list, this redirects them to the grocery list creation page
    public void createGroceryList(View v){
        Intent intent = new Intent(GroceryListPage.this, CreateGroceryList.class);
        startActivity(intent);
    }

    //If the user clicks the View Grocery Lists button, this redirects them to the grocery list viewing page
    public void viewGroceryLists(View v){
        Intent intent = new Intent(GroceryListPage.this, ViewGroceryList.class);
        startActivity(intent);
    }

    //If the user presses back the activity is finished
    public void backPressed(View v){
        finish();
    }
}