package com.example.desmosclonejavafirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameET, passwordET;
    private Button loginButton, forgotPasswordButton, registerANewUserButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                String username = usernameET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                // TODO: logIn(user)

                Toast.makeText(LoginActivity.this, "you are logged in " + username, Toast.LENGTH_SHORT).show();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(goToMainActivity);


            }
        });
        registerANewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(goToSignUp);
            }
        });

    }








}