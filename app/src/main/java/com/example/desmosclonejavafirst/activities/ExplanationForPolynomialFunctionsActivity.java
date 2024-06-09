package com.example.desmosclonejavafirst.activities;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.desmosclonejavafirst.R;

import java.util.Locale;

public class ExplanationForPolynomialFunctionsActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private TextView explanationTitleET, explanationPart1ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation_for_polynomial_functions);

        Button buttonGenerateExplanationTTS = findViewById(R.id.buttonGenerateExplanationTTS);

        explanationTitleET = findViewById(R.id.explanationTitle);
        explanationPart1ET = findViewById(R.id.explanationPart1);


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });


        buttonGenerateExplanationTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String explanationTitle = explanationTitleET.getText().toString();
                String explanationPart1 = explanationPart1ET.getText().toString();

                textToSpeech.speak(explanationTitle, TextToSpeech.QUEUE_ADD, null);
                textToSpeech.speak(explanationPart1, TextToSpeech.QUEUE_ADD, null);
            }
        });
    }
}