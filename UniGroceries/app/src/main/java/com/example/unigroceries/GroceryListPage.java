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

    public void createGroceryList(View v){
        Intent intent = new Intent(GroceryListPage.this, CreateGroceryList.class);
        startActivity(intent);
    }

    public void viewGroceryLists(View v){
        Intent intent = new Intent(GroceryListPage.this, ViewGroceryList.class);
        startActivity(intent);
    }

    public void backPressed(View v){
        finish();
    }
}