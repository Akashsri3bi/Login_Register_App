package com.example.fire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User extends AppCompatActivity {

    private FirebaseAuth auth ;
    Button logout ;
    TextView user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.btnlogout);
        user = findViewById(R.id.user) ;

        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        if (users != null) {
            // Name, email address, and profile photo Url
            String name = users.getEmail().toString();
            user.setText(String.format("Welcome %s", name));
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getApplicationContext() , MainActivity.class) ;
                startActivity(intent);
                finish();
            }
        });
    }
}