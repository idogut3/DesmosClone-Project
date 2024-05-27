package com.example.desmosclonejavafirst.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.desmosclonejavafirst.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailET, passwordET;
    private Button loginButton, forgotPasswordButton, registerANewUserButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        this.mAuth = FirebaseAuth.getInstance();

        this.emailET = findViewById(R.id.email);
        this.passwordET = findViewById(R.id.password);
        this.loginButton = findViewById(R.id.buttonLogin);
        this.forgotPasswordButton = findViewById(R.id.buttonForgotPassword);
        this.registerANewUserButton = findViewById(R.id.buttonRegisterANewUser);

        forgotPasswordButton.setBackgroundColor(Color.TRANSPARENT);
        registerANewUserButton.setBackgroundColor(Color.TRANSPARENT);

        AnimationDrawable animationDrawable = (AnimationDrawable) findViewById(R.id.linearLayout1).getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                // TODO: logIn(user)
//                mAuth.signInWithEmailAndPassword(email, password);


                // Toast.makeText(LoginActivity.this, "you are logged in " + username, Toast.LENGTH_SHORT).show();


                Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(goToMainActivity);
                finish();

            }
        });
        registerANewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSignUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(goToSignUpActivity);
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToForgotPasswordActivity = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(goToForgotPasswordActivity);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


}