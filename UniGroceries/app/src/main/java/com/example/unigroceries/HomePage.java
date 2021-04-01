package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void searchPage(View v){
        Intent searchIntent = new Intent(HomePage.this, SearchPage.class);
        startActivity(searchIntent);
    }

    public void groceryListPage(View v)
    {
        Intent intent = new Intent(HomePage.this, GroceryListPage.class);
        startActivity(intent);
    }
}