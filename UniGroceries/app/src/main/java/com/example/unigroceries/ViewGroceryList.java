package com.example.unigroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.unigroceries.adapter.GroceryListAdapter;
import com.example.unigroceries.model.GroceryListModel;
import com.example.unigroceries.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewGroceryList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private String uId;
    private List<GroceryListModel> groceryListModelList;
    private Query query;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grocery_list);

        //Initialising all the variables with their values
        recyclerView = findViewById(R.id.yourGroceryListsView);
        groceryListModelList = new ArrayList<>();
        context = this;
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        //Creating my query by accessing the specific GroceryListModels child of the User object
        query = databaseReference.child(uId).child("groceryListModels").orderByChild("date");

        //Adding a value event listener to the query
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Looping through all children in the returned dataSnapshot
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    //Creating GroceryListModel with child and adding it to the List<GroceryListModel>
                    GroceryListModel groceryListModel = postSnapshot.getValue(GroceryListModel.class);
                    groceryListModelList.add(groceryListModel);
                }

                //Initialising layout manager, adapter and setting them to the recyclerView
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
                recyclerView.setLayoutManager(linearLayoutManager);
                GroceryListAdapter groceryListAdapter = new GroceryListAdapter(context, groceryListModelList);
                recyclerView.setAdapter(groceryListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Debugging error message
                Log.d("Debug Log", "Couldn't access database");
            }
        });

    }

    //Exits and kills the activity if the "back" button is pressed
    public void backPressed(View v){
        finish();
    }


}