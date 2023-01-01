package org.school.riddlemethis.source_data;

import android.media.MediaPlayer;
import android.util.Log;
import org.school.riddlemethis.R;
import org.school.riddlemethis.appController.AppController;
import org.school.riddlemethis.prefs.AppSharedPreferences;

public class MusicController {
    private static final String TAG = "MusicController";
    private MediaPlayer mediaPlayer;
    private boolean caseToService;
    private static MusicController Instance;

    public static MusicController getInstance() {
        if (Instance == null) {
            Instance = new MusicController();
        }
        return Instance;
    }

    public MusicController() {
    }
    // MANGER
    public void musicRunner() {
        setCaseToService(AppSharedPreferences.getInstance(AppController.getInstance()).getMusicSwitchCase());

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(AppController.getInstance(), R.raw.mine_song);
            mediaPlayer.setLooping(true);
        }

        if (isCaseToService() && mediaPlayer != null) {
            //playing
            Log.i(TAG, "switchOn: &Ready2Play");
            mediaPlayer.start();
        } else {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    Log.i(TAG, "User music: user setOFF so Im Pausing..");
                    mediaPlayer.pause();
                }
            } else {
                Log.i(TAG, "User music: user setOff but media is null");
            }
        }
    }
    //Setter and Getter
    public boolean isCaseToService() {
        return caseToService;
    }

    public void setCaseToService(boolean caseToService) {
        this.caseToService = caseToService;
    }

    // Exit controller
    public void closeMusicWhenAppClosed() {
        if (mediaPlayer != null) {
            Log.i(TAG, "closeMusicWhenAppClosed: Cleaning-TheMediaPlayerObject");
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                Log.i(TAG, "closeMusicWhenAppClosed: progress is done");
            }
        }
    }

}
