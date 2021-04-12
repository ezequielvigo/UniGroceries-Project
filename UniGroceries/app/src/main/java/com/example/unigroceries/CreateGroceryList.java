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

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ingredientEditText = findViewById(R.id.groceryIngredientEt);
        groceryListTitleEt = findViewById(R.id.groceryListTitleEt);

        recyclerView = findViewById(R.id.groceryIngredientRecyclerView);
        ingredientModelList = new ArrayList<>();

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

    public void createList(View v){
        if(!ingredientModelList.isEmpty()){
            Calendar calendar = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dateFormat.format(calendar.getTime());

            String title =  groceryListTitleEt.getText().toString();
            ArrayList<String> ingredientList = arrayListFromIngredient(ingredientModelList);

            GroceryListModel groceryListModel = new GroceryListModel(date, ingredientList);

            if(title!=null){
                groceryListModel.setTitle(title);
            }

          databaseReference.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  User currentUser = dataSnapshot.getValue(User.class);
                  if(currentUser!=null){
                      if(currentUser.groceryListModels == null){
                          List<GroceryListModel> groceryListModels = new ArrayList<>();
                          groceryListModels.add(groceryListModel);
                          databaseReference.child(uId).child("groceryListModels").setValue(groceryListModels);
                      }else{
                          List<GroceryListModel> userGroceryListModels = currentUser.groceryListModels;
                          userGroceryListModels.add(groceryListModel);
                          databaseReference.child(uId).child("groceryListModels").setValue(userGroceryListModels);
                      }
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {
                  Log.d("Error log", "Couldn't read from database");
              }
          });


       }else{
            Toast.makeText(this, "You need to add ingredients to your list!", Toast.LENGTH_LONG).show();
        }

    }

    public ArrayList<String> arrayListFromIngredient(List<IngredientModel> ingredientModelList){
        ArrayList<String> ingredientList = new ArrayList<>();

        for(int i = 0; i<ingredientModelList.size(); i++){
            String ingredient = ingredientModelList.get(i).getName();
            ingredientList.add(ingredient);
        }

        return ingredientList;
    }

    public void backPressed(View v){
        finish();
    }
}