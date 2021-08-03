package com.example.fire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth ;
    EditText Name , Email , Pass ;
    Button register ;
    ProgressBar progressBar ;
    TextView Sign_in ;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main) ;

        //Firebase Initialisation here
        FirebaseApp.initializeApp(this) ;
        auth = FirebaseAuth.getInstance() ;

        Name = findViewById(R.id.name) ;
        Email = findViewById(R.id.email) ;
        Pass = findViewById(R.id.password) ;
        register = findViewById(R.id.btnregister) ;
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        register.setOnClickListener(v -> Register());

        Sign_in = findViewById(R.id.signin) ;
        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , SignIn.class) ;
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onStart() {

        super.onStart();
        FirebaseUser user = auth.getCurrentUser();

        if(user!=null){
            UpdateUi();
        }
    }

    public void Register(){
        progressBar.setVisibility(View.VISIBLE);
        String email = Email.getText().toString() ;
        String pass = Pass.getText().toString();

        if(TextUtils.isEmpty(email)){
            Email.setError("This is required*");
        }

        if(TextUtils.isEmpty(pass)){
            Pass.setError("This is required*");
        }

        if(pass.length()<6){
            Pass.setError("It should be 6 character long *");
        }

        auth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registered successful", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    UpdateUi();
                }else {
                    // If sign in fails, display a message to the user.
                    Log.w("Firebase Error", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    public void UpdateUi(){
        Intent intent = new Intent(getApplicationContext() , User.class);
        startActivity(intent);
        finish();
    }
}