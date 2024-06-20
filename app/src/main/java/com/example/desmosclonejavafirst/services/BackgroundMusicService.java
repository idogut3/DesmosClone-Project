package com.example.desmosclonejavafirst.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.desmosclonejavafirst.R;


/**
 * BackgroundMusicService is a Service class responsible for playing background music.
 */
public class BackgroundMusicService extends Service {

    MediaPlayer player;
    private int pausedPosition = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * Handles the start command for the service.
     *
     * @param intent  The Intent supplied to startService(Intent).
     * @param flags   Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return An integer value that determines what semantics the system should use for the service's current state.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        // Initialize MediaPlayer if not already initialized

        if (player == null) {
            player = MediaPlayer.create(this, R.raw.music_file1);
            player.setLooping(true);
            player.setVolume(60, 60);
        }

        // Handle actions based on incoming intents
        if (action != null && action.equals("PAUSE_MUSIC")) {
            pauseMusic();
        } else
        if (action != null && action.equals("RESUME_MUSIC")) {
            resumeMusic();
        } else {
            if (!player.isPlaying()) {
                player.start();
            }
            else {
                player.seekTo(pausedPosition);
                player.start();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Performs cleanup when the service is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    /**
     * Pauses the currently playing music.
     */

    private void pauseMusic() {
        if (player != null && player.isPlaying()) {
            pausedPosition = player.getCurrentPosition();
            player.pause();
        }
    }

    /**
     * Resumes the previously paused music.
     */
    private void resumeMusic() {
        if (player != null && !player.isPlaying()) {
            player.seekTo(pausedPosition);
            player.start();
        }
    }

}