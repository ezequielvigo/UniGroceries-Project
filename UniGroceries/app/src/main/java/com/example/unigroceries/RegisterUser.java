package com.example.unigroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.unigroceries.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2egity);

        //Initialise authenticator
        mAuth = FirebaseAuth.getInstance();

        //Initialize ProgressBar
        progressBar = findViewById(R.id.progressBar);
    }

    public void createAccount(final String email, String password, final String name, final String surname){
        //Launch Firebase method using user data
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Will execute if the operation was successful
                if(task.isSuccessful()){
                    //Creates a new User using user data
                    User user = new User(name,surname,email);
                    //Retrieves instance of FirebaseDatabase to create new user
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            //Creates a new child under Uid that represents a new user
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //Notifies the user that their account has been created and removes the progressBar
                               Toast.makeText(RegisterUser.this, "User registered successfully", Toast.LENGTH_LONG).show();
                               progressBar.setVisibility(View.GONE);
                            }else{
                                //Notifies the user that their account couldn't be created and removes the progressBar
                                Toast.makeText(RegisterUser.this, "User couldn't be registered", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    //Notifies the user that their account couldn't be created and removes the progressBar
                    Toast.makeText(RegisterUser.this, "User couldn't be registered", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void registerNewUser(View v){
        //Sets all EditText objects to their respective fields
        EditText userEmail = findViewById(R.id.etEmailAddress);
        EditText userPassword = findViewById(R.id.etPassword);
        EditText userName = findViewById(R.id.etName);
        EditText userSurname = findViewById(R.id.etSurname);

        //Creates a String from each user input
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();
        String name = userName.getText().toString();
        String surname = userSurname.getText().toString();


        //Checks if any field is left empty and displays and error if so
        if (name.isEmpty()) {
            userName.setError("Name is required");
            userName.requestFocus();
            return;
        }

        if (surname.isEmpty()) {
            userSurname.setError("Surname is required");
            userSurname.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            userPassword.setError("Password is required");
            userPassword.requestFocus();
            return;
        }

        if(email.isEmpty()){
            userEmail.setError("Email is required");
            userEmail.requestFocus();
            return;
        }

        //Checks if the email address entered by the user is invalid, if so it displays an error
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Enter a valid email address");
            userEmail.requestFocus();
            return;
        }

        //Checks if the password  entered is too short, if so it displays and error
        if(password.length()<6){
            userPassword.setError("Password must be longer than 6 characters");
            userPassword.requestFocus();
            return;
        }

        //Shows progressBar and creates the new account
        progressBar.setVisibility(View.VISIBLE);
        createAccount(email, password,name,surname);
    }

    //Redirects user back to login page
    public void bannerClicked(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}