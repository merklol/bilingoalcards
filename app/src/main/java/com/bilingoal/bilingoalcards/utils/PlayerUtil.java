package com.bilingoal.bilingoalcards.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class PlayerUtil {
    public static void playAssetSound(Context context, String soundFileName, float volume) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();

            AssetFileDescriptor descriptor = context.getAssets().openFd("audios/" + soundFileName + ".mp3");
            mediaPlayer.setDataSource(
                    descriptor.getFileDescriptor(), descriptor.getStartOffset(),
                    descriptor.getLength());

            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(volume, volume);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
