package com.example.desmosclonejavafirst.activities;

import static com.example.desmosclonejavafirst.validations.text_validations.TextValidation.passedAllTextValidationsForLogin;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.desmosclonejavafirst.R;
import com.example.desmosclonejavafirst.services.BackgroundMusicService;
import com.example.desmosclonejavafirst.validations.text_validations.CredentialAttribute;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.LinkedList;

public class LoginActivity extends AppCompatActivity {
    private EditText emailET, passwordET;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.mAuth = FirebaseAuth.getInstance();

        this.emailET = findViewById(R.id.email);
        this.passwordET = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.buttonLogin);
        Button forgotPasswordButton = findViewById(R.id.buttonForgotPassword);
        Button registerANewUserButton = findViewById(R.id.buttonRegisterANewUser);

        forgotPasswordButton.setBackgroundColor(Color.TRANSPARENT);
        registerANewUserButton.setBackgroundColor(Color.TRANSPARENT);

        // Start background animation
        AnimationDrawable animationDrawable = (AnimationDrawable) findViewById(R.id.linearLayout1).getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        if(!isMyServiceRunning(BackgroundMusicService.class)) {
            startService(new Intent(this, BackgroundMusicService.class));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                LinkedList<CredentialAttribute> credentials = new LinkedList<>(Arrays.asList(
                        new CredentialAttribute("Email", emailET),
                        new CredentialAttribute("Password", passwordET)));

                boolean isInputValid = passedAllTextValidationsForLogin(credentials, LoginActivity.this);

                if (isInputValid) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(goToMainActivity);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed, input problem", Toast.LENGTH_SHORT).show();
                }
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
    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
