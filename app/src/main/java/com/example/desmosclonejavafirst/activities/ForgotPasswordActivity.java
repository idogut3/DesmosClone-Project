package com.example.desmosclonejavafirst.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.desmosclonejavafirst.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;


/**
 * ForgotPasswordActivity allows users to reset their password by sending a password reset email.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    // Declaration of UI components
    private Button buttonReset;
    private EditText emailET;
//    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private String strEmail;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialization of UI components
        Button buttonGoBack = findViewById(R.id.btnForgotPasswordBack);
        buttonReset = findViewById(R.id.btnReset);
        emailET = findViewById(R.id.edtForgotPasswordEmail);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Listener for Reset Button
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = emailET.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword();
                } else {
                    emailET.setError("Email field can't be empty");
                }
            }
        });


        // Listener for Go Back Button
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                onBackPressed();
            }
        });

    }

    /**
     * Sends a password reset email to the user.
     */
    private void ResetPassword() {
//        progressBar.setVisibility(View.VISIBLE);
        buttonReset.setVisibility(View.INVISIBLE);


        // Send password reset email
        mAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ForgotPasswordActivity.this, "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, "Error :- " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.INVISIBLE);
                        buttonReset.setVisibility(View.VISIBLE);
                    }
                });
    }
}