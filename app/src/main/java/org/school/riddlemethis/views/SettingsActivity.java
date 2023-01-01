package org.school.riddlemethis.views;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import org.school.riddlemethis.R;
import org.school.riddlemethis.appController.AppController;
import org.school.riddlemethis.appController.service.NotificationController;
import org.school.riddlemethis.database.db.Repository;
import org.school.riddlemethis.fragment.dialogs.ResetDialog;
import org.school.riddlemethis.interfaces.DialogResetListener;
import org.school.riddlemethis.prefs.AppSharedPreferences;
import org.school.riddlemethis.source_data.MusicController;
import org.school.riddlemethis.status.UserStatus;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, DialogResetListener {
    private static final String TAG = "SettingsActivity";
    private Button btn_rest, btn_user_manger;
    private SwitchMaterial musicSwitch, notificationSwitch;
    private MaterialTextView tv_setting_score, tv_setting_username;
    //ShearedPref UserStatus
    private MaterialTextView tv_setting_RiddlesAnsweredCount, tv_setting_RiddlesAnsRight, tv_setting_RiddlesAnsWrong,tv_setting_l_solved_count;
    private final UserStatus userStatus = AppSharedPreferences.getInstance(this).getUserStatus();
    //Music
    boolean setSwitchStatus;
    boolean setNotificationSwitch;
    int currentScore = AppSharedPreferences.getInstance(this).getScore();

    private final String singleUsername = AppSharedPreferences.getInstance(this).getSingleUsername();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

    }

    //Must first give a boolean to ActBar for getting back to home Act ( simply i just run a backPressed
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializer();
    }

    private void initializer() {
        findViews();

        tv_setting_score.setText(String.valueOf(currentScore));

        tv_setting_username.setText(singleUsername);

        setSwitchStatus = AppSharedPreferences.getInstance(this).getMusicSwitchCase();
        musicSwitch.setChecked(setSwitchStatus);

        setNotificationSwitch = AppSharedPreferences.getInstance(this).getNotificationSwitchCase();
        notificationSwitch.setChecked(setNotificationSwitch);

        musicSwitchController();

        // STOPSHIP: 12/30/2022 JobScheduler
        notificationSwitchController();

        //ShearedPref Setter
        tv_setting_RiddlesAnsweredCount.setText(String.valueOf(userStatus.getRiddles_answered_count()));
        tv_setting_RiddlesAnsRight.setText(String.valueOf(userStatus.getR_ans_right_count()));
        tv_setting_RiddlesAnsWrong.setText(String.valueOf(userStatus.getR_ans_wrong_count()));
        tv_setting_l_solved_count.setText(String.valueOf(userStatus.getL_solved_count()));


        setActBar();

        setOnClicksBuilder();

    }

    private void setActBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //for Enabling the ->getting back to home Act Method
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void findViews() {
        btn_rest = findViewById(R.id.reset_data_btn);
        btn_user_manger = findViewById(R.id.set_data_btn);

        musicSwitch = findViewById(R.id.musicSwitch);
        notificationSwitch = findViewById(R.id.notificationSwitch);

        tv_setting_score = findViewById(R.id.tv_setting_score);

        tv_setting_username = findViewById(R.id.tv_setting_username);
        //ShearedPref UserStatus
        tv_setting_RiddlesAnsweredCount = findViewById(R.id.tv_setting_RiddlesAnsweredCount);
        tv_setting_RiddlesAnsRight = findViewById(R.id.tv_setting_RiddlesAnsRight);
        tv_setting_RiddlesAnsWrong = findViewById(R.id.tv_setting_RiddlesAnsWrong);
        tv_setting_l_solved_count = findViewById(R.id.tv_setting_l_solved_count);
    }

    private void setOnClicksBuilder() {
        btn_rest.setOnClickListener(this);
        btn_user_manger.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset_data_btn:
                performReset();
                break;
            case R.id.set_data_btn:
                performUserManger();
                break;
        }
    }

    private void musicSwitchController() {


        musicSwitch.setOnCheckedChangeListener((compoundButton, switchState) -> {
            AppSharedPreferences.getInstance(SettingsActivity.this).setMusicSwitchCase(switchState);
            MusicController.getInstance().setCaseToService(switchState);
            MusicController.getInstance().musicRunner();
        });
    }

    private void notificationSwitchController() {

        notificationSwitch.setOnCheckedChangeListener((compoundButton, switchState) -> {
            AppSharedPreferences.getInstance(SettingsActivity.this).setNotificationSwitchCase(switchState);
            NotificationController.getInstance().setCaseToService(switchState);
            NotificationController.getInstance().notificationRunner();
        });
    }

    private void performUserManger() {
        // Intent
        startActivity(new Intent(this, UserActivity.class));
    }

    private void performReset() {
        ResetDialog.newInstance("All data Will get Raise").show(getSupportFragmentManager(), "ResetData");
    }

    @Override
    public void onResetCalled() {
        Log.i(TAG, "onResetCalled: Device Restart The Application");
        AppSharedPreferences.getInstance(this).clearTheSharedPreferences();
        tv_setting_score.setText("");
        tv_setting_username.setText("");
        deleteALevel_evaluation();
        if (deleteALevel_evaluation()){
            try {
                Thread.sleep(1000);
                triggerRebirth(AppController.getInstance());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Your Data got Deleted Successfully", Toast.LENGTH_SHORT).show();
    }

    // a method for restarting the application
    public static void triggerRebirth(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    private boolean deleteALevel_evaluation(){
        Repository repository = new Repository(AppController.getInstance());
        repository.updateLevels_evaluationToDelete(0);
        return true;
    }

}