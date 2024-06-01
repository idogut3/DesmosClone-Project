package com.example.desmosclonejavafirst.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.desmosclonejavafirst.PlotView;
import com.example.desmosclonejavafirst.R;

public class GraphingCalculatorActivity extends AppCompatActivity {
    private EditText functionInput;
    private PlotView plotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphing_calculator);

        functionInput = findViewById(R.id.function_input);
        plotView = findViewById(R.id.plot_view);
        Button plotButton = findViewById(R.id.plot_button);
        plotButton.requestFocus();

        plotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String function = functionInput.getText().toString().trim();
                plotView.setFunction(function);
            }
        });
    }
}