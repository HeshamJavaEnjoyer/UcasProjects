package org.school.riddlemethis.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.school.riddlemethis.R;
import org.school.riddlemethis.source_data.MusicController;
import org.school.riddlemethis.prefs.AppSharedPreferences;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    //boolean caseOfMusic = true;
    MusicController musicController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // STOPSHIP: 12/14/2022 Temp Test Call a obj of database
        //the op is paused

    }
    @Override
    protected void onStart() {
        super.onStart();
        musicController = new MusicController();
        initializer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void initializer(){
        //for SplashActivity Shown
        controlSplashActivity();
        //for Controlling the music in the background
        controlMusicManger();
    }

    private void controlSplashActivity() {
        //3000ms - 3s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //-boolean isLogged_In = AppSharedPreferences.getInstance(getApplicationContext()).isLoggedIn();
                // NEW IF = condition ? makeIF_true : makeIf_false ;
                Intent splashToHome = new Intent(getBaseContext(),HomeActivity.class);
                startActivity(splashToHome);

            }
        }, 1400);
    }

    public void controlMusicManger(){
        //than depend on the changes up
        playAppMusic();
    }

    private boolean getMusicCase(){
        return AppSharedPreferences.getInstance(this).getMusicSwitchCase();
    }

    // cuz of the advantage of this class context
    private void playAppMusic() {
        //----FirstTimeEver
        if (getMusicCase()){
            musicController.setCaseToService(getMusicCase());
        }else {
            musicController.setCaseToService(false);
        }
    }

}