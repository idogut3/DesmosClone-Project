package com.example.desmosclonejavafirst.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

import com.example.desmosclonejavafirst.R;

public class BackgroundMusicService extends Service {
    private static final String TAG = "BackgroundMusicService";
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // We don't need to bind the service, so returning null.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");

        // Initialize the MediaPlayer with a music file from the raw resource folder
        mediaPlayer = MediaPlayer.create(this, R.raw.music_file1); // Ensure you have this file in res/raw folder
        mediaPlayer.setLooping(true); // Set looping
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");

        // Start the music
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");

        // Stop the music and release the MediaPlayer
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
