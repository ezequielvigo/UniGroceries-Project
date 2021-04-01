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

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(name,surname,email);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                               Toast.makeText(RegisterUser.this, "User registered successfully", Toast.LENGTH_LONG).show();
                               progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(RegisterUser.this, "User couldn't be registered", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });


                }else{
                    Toast.makeText(RegisterUser.this, "User couldn't be registered", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    public void registerNewUser(View v){
        EditText userEmail = findViewById(R.id.etEmailAddress);
        EditText userPassword = findViewById(R.id.etPassword);
        EditText userName = findViewById(R.id.etName);
        EditText userSurname = findViewById(R.id.etSurname);

        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();
        String name = userName.getText().toString();
        String surname = userSurname.getText().toString();
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
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Enter a valid email address");
            userEmail.requestFocus();
            return;
        }

        if(password.length()<6){
            userPassword.setError("Password must be longer than 6 characters");
            userPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        createAccount(email, password,name,surname);
    }

    public void bannerClicked(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}