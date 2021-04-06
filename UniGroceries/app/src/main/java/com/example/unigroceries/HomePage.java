package com.example.unigroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unigroceries.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomePage extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String uId;
    private TextView welcomeBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        welcomeBack = findViewById(R.id.welcomeTextView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uId = user.getUid();

        reference.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                if(user != null){
                   String userFullName = currentUser.name;
                   welcomeBack.setText("Welcome Back " +userFullName+"! ");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomePage.this, "Couldn't retrieve user data", Toast.LENGTH_LONG).show();
            }
        });

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