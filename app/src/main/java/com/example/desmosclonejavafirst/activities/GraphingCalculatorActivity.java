package com.example.desmosclonejavafirst.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.desmosclonejavafirst.plot_view.PlotView;
import com.example.desmosclonejavafirst.R;


/**
 * GraphingCalculatorActivity provides an interface for users to input a mathematical function
 * and visualize its graph using a custom PlotView.
 */
public class GraphingCalculatorActivity extends AppCompatActivity {

    // UI components
    private EditText functionInput; // Input field for the mathematical function
    private PlotView plotView; // Custom view for plotting the function

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphing_calculator); // Set the layout for the activity

        functionInput = findViewById(R.id.function_input);
        plotView = findViewById(R.id.plot_view);
        Button plotButton = findViewById(R.id.plot_button);

        // Ensure the plot button has focus to improve user experience
        plotButton.requestFocus();

        // Set a click listener for the plot button
        plotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the function input from the EditText
                String function = functionInput.getText().toString().trim();
                // Set the function in the PlotView to plot the graph
                plotView.setFunction(function);
            }
        });
    }
}