package org.school.riddlemethis.views;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import org.school.riddlemethis.R;
import org.school.riddlemethis.database.models.User;
import org.school.riddlemethis.prefs.AppSharedPreferences;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    private TextInputEditText username_edt, email_edt, birthDate_edt, gender_edt, country_edt;
    private String username, email, birthDate, gender, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    //for getting back to home Act ( simply i just run a backPressed
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Snackbar.make(findViewById(R.id.guideSetting_from_top), "Fill The Fields With Your Data", Snackbar.LENGTH_LONG).show();
        initializer();
    }

    private void initializer() {
        findViews();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //for Enabling the ->getting back to home Act Method
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setAvailableUser();
    }

    private void findViews() {
        username_edt = findViewById(R.id.username_edt);
        email_edt = findViewById(R.id.email_edt);
        birthDate_edt = findViewById(R.id.birthDate_edt);
        gender_edt = findViewById(R.id.gender_edt);
        country_edt = findViewById(R.id.country_edt);
    }

    private void setAvailableUser(){
        if (getAvailableUser()){
            username_edt.setText(username);
            email_edt.setText(email);
            birthDate_edt.setText(birthDate);
            gender_edt.setText(gender);
            country_edt.setText(country);
        }
    }

    private boolean getAvailableUser(){
        User user;
        user = AppSharedPreferences.getInstance(this).getAvailableUser();
        if (user != null){
            username = user.getUser_name();
            email = user.getEmail();
            birthDate = user.getBirthdate();
            gender = user.getGender();
            country = user.getCountry();
        }
        return true;
    }

    private void saveUserData() {
        username = Objects.requireNonNull(username_edt.getText()).toString();
                email = Objects.requireNonNull(email_edt.getText()).toString();
        birthDate = Objects.requireNonNull(birthDate_edt.getText()).toString();
                gender = Objects.requireNonNull(gender_edt.getText()).toString();
        country = Objects.requireNonNull(country_edt.getText()).toString();

        AppSharedPreferences.getInstance(this).writeAUser(new User(username,email,birthDate,gender,country));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveUserData();
    }
}