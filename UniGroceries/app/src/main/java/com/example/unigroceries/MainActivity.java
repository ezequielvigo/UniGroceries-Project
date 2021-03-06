package com.example.unigroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEt;
    private EditText passwordEt;
    public ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        //Initialize the EditText objects
       emailEt = findViewById(R.id.editTextEmailAddress);
       passwordEt = findViewById(R.id.editTextTextPassword);
       progressBar = findViewById(R.id.loginProgressBar);




    }

    public void updateUI(FirebaseUser currentUser) {

    }


    public void signIn(View v){

        //Retrieving user input from Edit Text fields

        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        if(email.isEmpty()){
            emailEt.setError("Email cannot be empty");
            if(password.isEmpty()){
                passwordEt.setError("Password cannot be empty");
            }
            return;
        }

        if(password.isEmpty()){
            passwordEt.setError("Password cannot be empty");
            if(email.isEmpty()){
                emailEt.setError("Email cannot be empty");
            }
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //Calling method to sign In the user with provided email and pssword
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("UniGroceries Debug", "signInWithEmail:success");
                        progressBar.setVisibility(View.GONE);

                        //This is used to check whether the user has verified their email account
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user.isEmailVerified()){
                            startActivity(new Intent(MainActivity.this, HomePage.class));
                        }else{
                            //Sends the user an email with a verification link if they have not
                            user.sendEmailVerification();
                            Toast.makeText(MainActivity.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("UniGroceries Debug", "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                        progressBar.setVisibility(View.GONE);
                    }

                }
            });

    }


    //If the user clicks Register it redirects them to the RegisterUser activity
    public void registerUser(View v){
        startActivity(new Intent(this, RegisterUser.class));
    }


}