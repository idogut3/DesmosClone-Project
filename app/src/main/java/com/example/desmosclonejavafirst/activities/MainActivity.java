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

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGoToExplanationsActivity = findViewById(R.id.buttonGoToExplanationsActivity);
        Button buttonGoToGraphingCalculatorActivity = findViewById(R.id.buttonGoToGraphingCalculatorActivity);
        imageView = findViewById(R.id.imageProfilePicView);

        // Start background animation
        AnimationDrawable animationDrawable = (AnimationDrawable) findViewById(R.id.mainActivityLayout).getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    // Now you have the imageUrl for the specified userId
                    Log.d("FirebaseExample", "Image URL: " + imageUrl);
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

        buttonGoToExplanationsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToExplanationsActivity = new Intent(MainActivity.this, ExplanationForPolynomialFunctionsActivity.class);
                startActivity(goToExplanationsActivity);
            }
        });

        buttonGoToGraphingCalculatorActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGraphingCalculatorActivity = new Intent(MainActivity.this, GraphingCalculatorActivity.class);
                startActivity(goToGraphingCalculatorActivity);
            }
        });
    }

    // Function to display the image using Glide
    private void displayImage(String imageUrl) {
        if (imageUrl != null) {
            // Use Glide to load the image
            Glide.with(this).load(imageUrl).into(imageView);
        }
    }
}
