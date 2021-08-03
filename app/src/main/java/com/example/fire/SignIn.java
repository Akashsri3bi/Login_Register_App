package com.example.fire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SignIn extends AppCompatActivity {

    private FirebaseAuth auth ;
    EditText Email , Pass ;
    Button Login;
    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Firebase Initialisation here
        FirebaseApp.initializeApp(this) ;
        auth = FirebaseAuth.getInstance() ;

        Email = findViewById(R.id.email) ;
        Pass = findViewById(R.id.password) ;
        Login = findViewById(R.id.btnlogin) ;
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        Login.setOnClickListener(v -> login());
    }

    public void login(){
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

        auth.signInWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = auth.getCurrentUser();
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Welcome",
                            Toast.LENGTH_SHORT).show();
                    UpdateUi();

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) ;
    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void UpdateUi(){
        Intent intent = new Intent(getApplicationContext() , User.class);
        startActivity(intent);
        finish();
    }
}