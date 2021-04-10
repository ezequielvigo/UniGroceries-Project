package com.example.unigroceries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.unigroceries.adapter.GroceryListAdapter;
import com.example.unigroceries.model.GroceryListModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewGroceryList extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grocery_list);


        recyclerView = findViewById(R.id.yourGroceryListsView);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(linearLayoutManager);

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(calendar.getTime());

        ArrayList<String> groceryList = new ArrayList<>();

        groceryList.add("Chicken");
        groceryList.add("Cheese");

        GroceryListModel groceryListModel = new GroceryListModel(date, groceryList);

        List<GroceryListModel> groceryListModelList = new ArrayList<>();
        groceryListModelList.add(groceryListModel);

        GroceryListAdapter groceryListAdapter = new GroceryListAdapter(this, groceryListModelList);

        recyclerView.setAdapter(groceryListAdapter);

    }

    public void backPressed(View v){
        finish();
    }


}