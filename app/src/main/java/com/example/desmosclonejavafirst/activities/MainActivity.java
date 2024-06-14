package com.example.desmosclonejavafirst.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.desmosclonejavafirst.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGoToExplanationsActivity = findViewById(R.id.buttonGoToExplanationsActivity);
        Button buttonGoToGraphingCalculatorActivity = findViewById(R.id.buttonGoToGraphingCalculatorActivity);

        // Start background animation
        AnimationDrawable animationDrawable = (AnimationDrawable) findViewById(R.id.mainActivityLayout).getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
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
}