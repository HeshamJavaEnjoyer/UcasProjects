package org.school.riddlemethis.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.school.riddlemethis.R;
import org.school.riddlemethis.source_data.MusicController;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStartGame, btnSettings, btnExit;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializer();
    }

    private void initializer() {
        findViews();
        new Handler().postDelayed(this::setBtnAnimation, 650);
        setOnClickListener();
    }

    private void findViews() {
        btnStartGame = findViewById(R.id.btnHomeStartGame);
        btnSettings = findViewById(R.id.btnHomeSettings);
        btnExit = findViewById(R.id.btnHomeExit);
    }

    private void setOnClickListener() {
        btnStartGame.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    private void performGameMove() {
        intent = new Intent(HomeActivity.this, PlayActivity.class);
        startActivity(intent);
    }

    private void performSettingMove() {
        //Todo settings intent
        intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void performExitMove() {
        onDestroy();
        finish();
        System.exit(0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnHomeStartGame) {
            //Todo Perform Home
            Snackbar.make(findViewById(R.id.snackBar_view), "HomeSweetHome", Snackbar.LENGTH_LONG).show();
            performGameMove();
        } else if (view.getId() == R.id.btnHomeSettings) {
            //todo perform Settings
            Snackbar.make(findViewById(R.id.snackBar_view), "Settings", Snackbar.LENGTH_LONG).show();
            performSettingMove();
        } else if (view.getId() == R.id.btnHomeExit) {
            //todo perform Exit
            Snackbar.make(findViewById(R.id.snackBar_view), "Exit", Snackbar.LENGTH_LONG).show();
            performExitMove();
        }
    }


    private void setBtnAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        btnStartGame.startAnimation(animation);
        btnSettings.startAnimation(animation);
        btnExit.startAnimation(animation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearAnimationForActViews();
        //here i close the appMusic when close
        MusicController.getInstance().closeMusicWhenAppClosed();
    }

    private void clearAnimationForActViews() {
        btnStartGame.clearAnimation();
        btnSettings.clearAnimation();
        btnExit.clearAnimation();
    }

}