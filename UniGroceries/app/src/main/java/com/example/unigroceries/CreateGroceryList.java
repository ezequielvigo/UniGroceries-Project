package com.example.unigroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.unigroceries.adapter.IngredientSearchAdapter;
import com.example.unigroceries.model.GroceryListModel;
import com.example.unigroceries.model.IngredientModel;
import com.example.unigroceries.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateGroceryList extends AppCompatActivity {

    private EditText ingredientEditText, groceryListTitleEt;
    private RecyclerView recyclerView;
    private IngredientSearchAdapter ingredientSearchAdapter;
    private List<IngredientModel> ingredientModelList;
    private DatabaseReference databaseReference;
    private String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_grocery_list);

        //Initialising the database reference and retrieving user id from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Initialising editText views
        ingredientEditText = findViewById(R.id.groceryIngredientEt);
        groceryListTitleEt = findViewById(R.id.groceryListTitleEt);

        //Initialising recyclerView and the ingredientModelList
        recyclerView = findViewById(R.id.groceryIngredientRecyclerView);
        ingredientModelList = new ArrayList<>();

        //Setting the adapter using the ingredientModelList
        ingredientSearchAdapter = new IngredientSearchAdapter(this, ingredientModelList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ingredientSearchAdapter);
    }

    public void viewRecipes(View v){
        //Bundles all ingredients retrieved from the recyclerView and sends as intent into recipe search viewing activity
        Intent intent = new Intent(CreateGroceryList.this, IngredientSearchResults.class);
        Bundle args = new Bundle();
        args.putSerializable("ingredientList", (Serializable)ingredientModelList);
        intent .putExtra("bundle", args);
        startActivity(intent);
    }

    //Adding items to the ingredient list
    public void addItem(View v){
        //Creates a new IngredientModel, adds it to the List<IngredientModel>
       IngredientModel ingredientModel = new IngredientModel(ingredientEditText.getText().toString());
       ingredientModelList.add(ingredientModel);
       //Notifies the adapter the data set has changed in order to update the recyclerView
       ingredientSearchAdapter.notifyDataSetChanged();
    }

    //Used to finalise the list and create it on the FirebaseDatabase
    public void createList(View v){
        //Checking the user has added ingredients
        if(!ingredientModelList.isEmpty()){
            //Retrieving the current date and formatting it
            Calendar calendar = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dateFormat.format(calendar.getTime());

            //Retrieving the grocery list title from the user input and the ArrayList<String> of Ingredients
            String title =  groceryListTitleEt.getText().toString();
            ArrayList<String> ingredientList = arrayListFromIngredient(ingredientModelList);

            //Creating the grocery list model
            GroceryListModel groceryListModel = new GroceryListModel(date, ingredientList);

            //Checking if user has added a title
            if(title!=null){
                groceryListModel.setTitle(title);
            }

            // Using a single value event listener to read from the FirebaseDatabase
          databaseReference.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  //Creating a current user that is the value of the user retrieved
                  User currentUser = dataSnapshot.getValue(User.class);
                  //Checking if user retrieved is null
                  if(currentUser!=null){
                      //Checking if the user has created any groceryLists yet
                      if(currentUser.groceryListModels == null){
                          //If the user hasn't, it creates a new list adding the grocerListModel just created
                          List<GroceryListModel> groceryListModels = new ArrayList<>();
                          groceryListModels.add(groceryListModel);
                          //Uses databaseReference to set the groceryListModels list to the newly created list
                          databaseReference.child(uId).child("groceryListModels").setValue(groceryListModels);

                          Log.d("Debug log", "Created new groceryListModels");
                      }else{
                          //Retrieves the user's groceryListModels, adds the new groceryListModel
                          List<GroceryListModel> userGroceryListModels = currentUser.groceryListModels;
                          userGroceryListModels.add(groceryListModel);
                          //Uses databaseReference to set the groceryListModels to the new one
                          databaseReference.child(uId).child("groceryListModels").setValue(userGroceryListModels);

                          Log.d("Debug log", "Added to existing groceryListModels");
                      }
                  }
                  finish();
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {
                  //Used to determine whether the request was successful
                  Log.d("Error log", "Couldn't read from database");
              }
          });


       }else{
            //Prompts the user to add ingredients if they haven't done so
            Toast.makeText(this, "You need to add ingredients to your list!", Toast.LENGTH_LONG).show();
        }

    }

    //Creates a list of strings from a list of IngredientModels
    public ArrayList<String> arrayListFromIngredient(List<IngredientModel> ingredientModelList){
        ArrayList<String> ingredientList = new ArrayList<>();

        //Loops through List<IngredientModel>, retrieves ingredient name and adds to ArrayList<String>
        for(int i = 0; i<ingredientModelList.size(); i++){
            String ingredient = ingredientModelList.get(i).getName();
            ingredientList.add(ingredient);
        }

        return ingredientList;
    }

    //User pressed back, kills the activity and returns to the previous screen
    public void backPressed(View v){
        finish();
    }
}