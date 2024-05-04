package com.example.desmosclonejavafirst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText firstNameET, lastNameET, usernameET, emailET, passwordET, confirmPasswordET;
    private Button signupButton;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.firstNameET = (EditText) findViewById(R.id.firstName);
        this.lastNameET = (EditText) findViewById(R.id.lastName);
        this.usernameET = (EditText) findViewById(R.id.username);
        this.emailET = (EditText) findViewById(R.id.email);
        this.passwordET = (EditText) findViewById(R.id.password);
        this.confirmPasswordET = (EditText) findViewById(R.id.confirmPassword);
        this.signupButton = (Button) findViewById(R.id.buttonSignUp);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameET.getText().toString().trim();
                String lastName = lastNameET.getText().toString().trim();
                String username = usernameET.getText().toString().trim();
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                String confirmPassword = confirmPasswordET.getText().toString().trim();
            }
        });

    }
}