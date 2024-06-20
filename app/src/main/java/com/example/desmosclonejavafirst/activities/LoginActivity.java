package com.example.desmosclonejavafirst.activities;
// Import statements for necessary classes and functions
//import static com.example.desmosclonejavafirst.security.HashingFunctions.encryptPasswordInSHA1;

import static com.example.desmosclonejavafirst.validations.text_validations.TextValidation.passedAllTextValidationsForLogin;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
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

/**
 * LoginActivity is responsible for handling user login operations.
 * It includes features such as email and password validation,
 * background animation, and background music service management.
 */

public class LoginActivity extends AppCompatActivity {
    // UI elements for email and password input
    private EditText emailET, passwordET;

    // Firebase Authentication instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Authentication instance
        this.mAuth = FirebaseAuth.getInstance();

        // Link UI elements to code
        this.emailET = findViewById(R.id.email);
        this.passwordET = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.buttonLogin);
        Button forgotPasswordButton = findViewById(R.id.buttonForgotPassword);
        Button registerANewUserButton = findViewById(R.id.buttonRegisterANewUser);

        // Set button background colors to transparent
        forgotPasswordButton.setBackgroundColor(Color.TRANSPARENT);
        registerANewUserButton.setBackgroundColor(Color.TRANSPARENT);

        // Start background animation
        AnimationDrawable animationDrawable = (AnimationDrawable) findViewById(R.id.linearLayout1).getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        // Start background music service if not already running
        if (!isMyServiceRunning(BackgroundMusicService.class)) {
            startService(new Intent(this, BackgroundMusicService.class));
        }

        // Set OnClickListener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get email and password input
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                // Create a list of CredentialAttribute objects for validation
                LinkedList<CredentialAttribute> credentials = new LinkedList<>(Arrays.asList(
                        new CredentialAttribute("Email", emailET),
                        new CredentialAttribute("Password", passwordET)));

                // Validate the input
                boolean isInputValid = passedAllTextValidationsForLogin(credentials, LoginActivity.this);
                // If input is valid, proceed with login
                if (isInputValid) {
//                    String hashedPassword = encryptPasswordInSHA1(password);
                    // Sign in with email and password using Firebase Authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) { // Login successful, navigate to MainActivity
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(goToMainActivity);
                                        finish();
                                    } else {  // Login failed, show error message
                                        Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {  // Input is invalid, show error message
                    Toast.makeText(LoginActivity.this, "Login failed, input problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set OnClickListener for register new user button
        registerANewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUpActivity for user registration
                Intent goToSignUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(goToSignUpActivity);
            }
        });

        // Set OnClickListener for forgot password button
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ForgotPasswordActivity for password recovery
                Intent goToForgotPasswordActivity = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(goToForgotPasswordActivity);
            }
        });
    }

    /**
     * Checks if a specified service is running in the background.
     *
     * @param serviceClass The service class to check.
     * @return true if the service is running, false otherwise.
     */
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
