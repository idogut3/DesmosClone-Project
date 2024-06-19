package com.example.desmosclonejavafirst.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.desmosclonejavafirst.R;
import com.example.desmosclonejavafirst.services.BackgroundMusicService;

import java.util.Locale;

public class ExplanationForPolynomialFunctionsActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private TextView explanationTitleET, explanationPart1ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation_for_polynomial_functions);

        Button buttonGenerateExplanationTTS = findViewById(R.id.buttonGenerateExplanationTTS);
        Button buttonStopTTS = findViewById(R.id.stop_tts);

        explanationTitleET = findViewById(R.id.explanationTitle);
        explanationPart1ET = findViewById(R.id.explanationPart1);

        if(!isMyServiceRunning(BackgroundMusicService.class)) {
            stopService(new Intent(this, BackgroundMusicService.class));
        }
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

                int result = textToSpeech.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                .build());

                if (result == TextToSpeech.SUCCESS) {
                    textToSpeech.speak(explanationTitle, TextToSpeech.QUEUE_ADD, null);
                    textToSpeech.speak(explanationPart1, TextToSpeech.QUEUE_ADD, null);
                } else {
                    Log.e("TTS", "Failed to set audio attributes");
                }
            }
        });

        buttonStopTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textToSpeech.isSpeaking()) {
                    textToSpeech.stop();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown TTS
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
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