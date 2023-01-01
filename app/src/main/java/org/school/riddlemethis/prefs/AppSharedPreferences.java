package org.school.riddlemethis.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import org.school.riddlemethis.database.models.User;
import org.school.riddlemethis.enums.PrefGeneral;
import org.school.riddlemethis.enums.PrefStatusKeys;
import org.school.riddlemethis.enums.PrefUserKeys;
import org.school.riddlemethis.status.UserStatus;

public class AppSharedPreferences {
    private static AppSharedPreferences Instance;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private AppSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
    }

    public static AppSharedPreferences getInstance(Context context) {
        if (Instance == null) {
            Instance = new AppSharedPreferences(context);
        }
        return Instance;
    }

    //just username!!!
    private void writeSingle_name(String s) {
        editor = sharedPreferences.edit();
        editor.putString(PrefUserKeys.user_name.name(), s);
        editor.apply();
    }

    public String getSingleUsername() {
        return sharedPreferences.getString(PrefUserKeys.user_name.name(), "");
    }

    //fin JUST USERNAME

    public void writeAUser(User user) {
        //my lovely idea
        writeSingle_name(user.getUser_name());
        //complete
        editor = sharedPreferences.edit();
        editor.putString(PrefUserKeys.user_name.name(), user.getUser_name());
        editor.putString(PrefUserKeys.email.name(), user.getEmail());
        editor.putString(PrefUserKeys.birthdate.name(), user.getBirthdate());
        editor.putString(PrefUserKeys.gender.name(), user.getGender());
        editor.putString(PrefUserKeys.country.name(), user.getCountry());
        editor.apply();
    }

    public User getAvailableUser() {
        User user = new User();
        user.setUser_name(sharedPreferences.getString(PrefUserKeys.user_name.name(), ""));
        user.setEmail(sharedPreferences.getString(PrefUserKeys.email.name(), ""));
        user.setBirthdate(sharedPreferences.getString(PrefUserKeys.birthdate.name(), ""));
        user.setGender(sharedPreferences.getString(PrefUserKeys.gender.name(), ""));
        user.setCountry(sharedPreferences.getString(PrefUserKeys.country.name(), ""));
        return user;
    }


    //**********[SwitchCaseSetting]***********
    public void setMusicSwitchCase(boolean switchCase) {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefGeneral.switchCase.name(), switchCase);
        editor.apply();
    }

    public boolean getMusicSwitchCase() {
        return sharedPreferences.getBoolean(PrefGeneral.switchCase.name(), true);
    }

    //**********[SwitchCaseSetting]***********
    public void setNotificationSwitchCase(boolean switchCase) {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefGeneral.switchCaseNotification.name(), switchCase);
        editor.apply();
    }

    public boolean getNotificationSwitchCase() {
        return sharedPreferences.getBoolean(PrefGeneral.switchCaseNotification.name(), true);
    }

    //**********[Score]***********
    public void setScore(int score) {
        editor = sharedPreferences.edit();
        editor.putInt(PrefUserKeys.score.name(), score);
        editor.apply();
    }

    public int getScore() {
        return sharedPreferences.getInt(PrefUserKeys.score.name(), 0);
    }

    //**********[ViewPager]***********
    // for setting the ViewPager last Index Usage
    public void setViewPagerLastUsedItem(int lastUsedItemIndex) {
        editor = sharedPreferences.edit();
        editor.putInt("lastUsedItemIndex", lastUsedItemIndex);
        editor.apply();
    }

    // for getting the ViewPager last Index Usage
    public int getViewPagerLastUsedItem() {
        return sharedPreferences.getInt("lastUsedItemIndex", 0);
    }


    //**********[User Status]***********=============================*****************************************
    // for setting the ViewPager last Index Usage
    public void setUserStatus(int riddles_answered_count, int r_ans_right_count, int r_ans_wrong_count, int l_solved_count) {
        editor = sharedPreferences.edit();
        editor.putInt(PrefStatusKeys.riddles_answered_count.name(), riddles_answered_count);
        editor.putInt(PrefStatusKeys.r_ans_right_count.name(), r_ans_right_count);
        editor.putInt(PrefStatusKeys.r_ans_wrong_count.name(), r_ans_wrong_count);
        editor.putInt(PrefStatusKeys.l_solved_count.name(), l_solved_count);
        editor.apply();
    }

    // for getting the user status to setting
    public UserStatus getUserStatus() {
        UserStatus userStatus = new UserStatus();
        userStatus.setRiddles_answered_count(sharedPreferences.getInt(PrefStatusKeys.riddles_answered_count.name(), 0));
        userStatus.setR_ans_right_count(sharedPreferences.getInt(PrefStatusKeys.r_ans_right_count.name(), 0));
        userStatus.setR_ans_wrong_count(sharedPreferences.getInt(PrefStatusKeys.r_ans_wrong_count.name(), 0));
        userStatus.setL_solved_count(sharedPreferences.getInt( PrefStatusKeys.l_solved_count.name(), 0));
        return userStatus;
    }
    //**********[User Status]***********=============================*****************************************


    public void clearTheSharedPreferences() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
