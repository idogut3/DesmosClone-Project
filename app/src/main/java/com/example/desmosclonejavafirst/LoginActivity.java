package com.example.desmosclonejavafirst;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

    }








}