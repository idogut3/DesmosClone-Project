package com.example.desmosclonejavafirst.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.desmosclonejavafirst.R;

public class BackgroundMusicService extends Service {

    MediaPlayer player;
    private int pausedPosition = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //Starting the service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        //setting values for Media-player

        if (player == null) {
            player = MediaPlayer.create(this, R.raw.music_file1);
            player.setLooping(true);
            player.setVolume(60, 60);
        }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    private void pauseMusic() {
        if (player != null && player.isPlaying()) {
            pausedPosition = player.getCurrentPosition();
            player.pause();
        }
    }

    private void resumeMusic() {
        if (player != null && !player.isPlaying()) {
            player.seekTo(pausedPosition);
            player.start();
        }
    }

}