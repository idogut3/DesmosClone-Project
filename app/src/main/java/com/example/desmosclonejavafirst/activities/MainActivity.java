package com.example.desmosclonejavafirst.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.desmosclonejavafirst.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


/**
 * MainActivity serves as the landing page after a successful login.
 * It provides navigation to other activities and displays the user's profile picture.
 */
public class MainActivity extends AppCompatActivity {
    private ImageView imageView;  // ImageView to display the user's profile picture
    private String imageUrl; // URL of the user's profile picture

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        Button buttonGoToExplanationsActivity = findViewById(R.id.buttonGoToExplanationsActivity);
        Button buttonGoToGraphingCalculatorActivity = findViewById(R.id.buttonGoToGraphingCalculatorActivity);
        Button buttonSignOut = findViewById(R.id.buttonSignOut);

        imageView = findViewById(R.id.imageProfilePicView);

        // Start background animation
        AnimationDrawable animationDrawable = (AnimationDrawable) findViewById(R.id.mainActivityLayout).getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        // Get current user's ID from Firebase Authentication
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        // Reference to the user's data in Firebase Realtime Database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        // Add a listener to fetch the user's profile picture URL
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve the imageUrl for the user

                    imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    // Now you have the imageUrl for the specified userId

                    Log.d("FirebaseExample", "Image URL: " + imageUrl);

                    // Display the image using the retrieved URL
                    displayImage(imageUrl);
                } else {
                    Log.d("FirebaseExample", "User not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ProfilePic", "Couldn't retrieve profile picture");
            }
        });

        // Set up the sign-out button's click listener
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut(); //If button clicked signs out

                //Goes to login page
                Intent goToLoginPage = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(goToLoginPage);
                finish();
            }
        });

        buttonGoToExplanationsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToExplanationsActivity = new Intent(MainActivity.this, ExplanationForPolynomialFunctionsActivity.class);
                startActivity(goToExplanationsActivity);
            }
        });

        // Set up the button to navigate to GraphingCalculatorActivity
        buttonGoToGraphingCalculatorActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGraphingCalculatorActivity = new Intent(MainActivity.this, GraphingCalculatorActivity.class);
                startActivity(goToGraphingCalculatorActivity);
            }
        });
    }

    /**
     * Displays the user's profile picture using the provided imageUrl.
     * @param imageUrl The URL of the image to be displayed.
     */
    private void displayImage(String imageUrl) {
        if (imageUrl != null) {
            // Use Glide library to load and display the image
            Glide.with(this).load(imageUrl).into(imageView);
        }
    }
}
