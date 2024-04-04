package com.example.desmosclonejavafirst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText firstName, lastName, username, email, password, confirmPassword;
    private Button signupButton;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.firstName = findViewById(R.id.firstName);
        this.lastName = findViewById(R.id.lastName);
        this.username = findViewById(R.id.username);
        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.password);
        this.confirmPassword = findViewById(R.id.confirmPassword);
        this.signupButton = findViewById(R.id.buttonSignUp);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String firstName = firstName.getText
            }
        });

    }
}