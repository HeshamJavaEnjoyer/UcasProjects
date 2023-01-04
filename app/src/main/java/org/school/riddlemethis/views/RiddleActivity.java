package org.school.riddlemethis.views;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import org.school.riddlemethis.R;
import org.school.riddlemethis.adapters.ViewPagerAdapter;
import org.school.riddlemethis.database.db.RiddleViewModel;
import org.school.riddlemethis.database.models.Riddles;
import org.school.riddlemethis.fragment.dialogs.FinishedRiddlesDialog;
import org.school.riddlemethis.fragment.dialogs.RiddleSkipDialog;
import org.school.riddlemethis.fragment.dialogs.RiddleSuccessDialog;
import org.school.riddlemethis.fragment.dialogs.RiddleTimeOutDialog;
import org.school.riddlemethis.fragment.dialogs.RiddleWrongDialog;
import org.school.riddlemethis.interfaces.AnswerCallback;
import org.school.riddlemethis.interfaces.DialogListener;
import org.school.riddlemethis.interfaces.DialogTimeOutCallback;
import org.school.riddlemethis.interfaces.FragmentAskForSkipListener;
import org.school.riddlemethis.interfaces.TimerListener;
import org.school.riddlemethis.prefs.AppSharedPreferences;
import org.school.riddlemethis.status.UserStatus;

import java.util.ArrayList;
import java.util.Objects;

//DialogTimeOutCallback, TimerListener
public class RiddleActivity extends AppCompatActivity implements AnswerCallback, DialogListener, TimerListener, DialogTimeOutCallback, FragmentAskForSkipListener {
    private static final String TAG = "RiddleActivity";
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView tv_currentLevel, tv_currentScore, tv_riddle_act_riddle_count, tv_standing_riddle;

    /*forget it    private CountDownTimer countDownTimer;
    private boolean isDone;*/

    private TextView tv_timer_forRiddle;

    //    private FloatingActionButton btn_Skip_Riddle;
    private final ArrayList<Riddles> riddlesArrayList = new ArrayList<>();
    private RiddleViewModel viewModel;
    private int level_no;
    private int currentViewItem;

    private double _valueFor_allRiddlesScoreIn1Level;
    private double _valueFor_userGrantedScoreFor1Level = 0;

    private final int score_shared = AppSharedPreferences.getInstance(this).getScore();
    public static int score_general = 0;
    //TODO*********************************************************************************************
    private final UserStatus userStatus = AppSharedPreferences.getInstance(this).getUserStatus();
    private int m_riddles_answered_count = userStatus.getRiddles_answered_count() + 1;
    private int m_r_ans_right_count = userStatus.getR_ans_right_count();
    private int m_r_ans_wrong_count = userStatus.getR_ans_wrong_count();
    private int m_l_solved_count = userStatus.getL_solved_count();
    private boolean isClicked = false;
    //TODO*********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riddle);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initializer();
        initializeViewPagerAdapter();

//        setTimer(5000);
    }


    private void initializer() {
        if (score_shared > score_general) {
            score_general = score_shared;
        }

        // init the dao of getting data
        viewModel = new ViewModelProvider(this).get(RiddleViewModel.class);
        //Fetching some important Data
        level_no = getIntent().getExtras().getInt("level_no");
        //findViews
        findViews();
        //textS and Stuff
        setterValue();

    }

    private void setterValue() {
        //set-value
        currentViewItem = viewPager2.getCurrentItem();
        //TextView WorkingGood
        tv_currentLevel.setText(String.valueOf(level_no));
        tv_currentScore.setText(String.valueOf(score_general));
        //**Standing
        tv_standing_riddle.setText(String.valueOf(viewPager2.getCurrentItem() + 1));
        //ClickListener
//        btn_Skip_Riddle.setOnClickListener(this);
    }

    private void findViews() {
        viewPager2 = findViewById(R.id.viewPager2);

        tv_currentLevel = findViewById(R.id.tv_riddle_act_level_id);
        tv_currentScore = findViewById(R.id.tv_riddle_act_currentScore);

        tv_riddle_act_riddle_count = findViewById(R.id.tv_riddle_act_riddle_count);

//        btn_Skip_Riddle = findViewById(R.id.skip_f_act_btn);

        tv_timer_forRiddle = findViewById(R.id.tv_timer_forRiddle);

        tv_standing_riddle = findViewById(R.id.tv_standing_riddle);
    }

    private void initializeViewPagerAdapter() {
        //adapter object
        viewPagerAdapter = new ViewPagerAdapter(this, riddlesArrayList);
        viewPager2.setAdapter(viewPagerAdapter);

        //begin test fetching data from data base
        viewModel.getRiddlesByLevelId(level_no).observe(this, riddlesList -> {

            if (!riddlesArrayList.isEmpty()) {
                riddlesArrayList.clear();
                riddlesArrayList.addAll(riddlesList);
            } else {
                riddlesArrayList.addAll(riddlesList);
            }

            viewPagerAdapter.setRiddlesList(riddlesArrayList);

            tv_riddle_act_riddle_count.setText(String.valueOf(riddlesList.size()));

            //getAllRiddlesScore
            for (int i = 0; i < riddlesList.size(); i++) {
                _valueFor_allRiddlesScoreIn1Level += riddlesList.get(i).getRiddleGivenPoint();
            }
            Log.d(TAG, "value_ARSin1Level: " + _valueFor_allRiddlesScoreIn1Level);

        });
        //end test fetching data from data base
        Log.e(TAG, "value_ARSin1Level: " + _valueFor_allRiddlesScoreIn1Level);

        //Stop the user from Moving
        viewPager2.setUserInputEnabled(false);
    }


    //TODO*********************************************************************************************


    //--------------------------------[USER SOLVED RIDDLE]--------------------------------------------------------
    @Override
    public void onSuccess(int riddleId) {


        //---------USER Status
        m_r_ans_right_count++;
        //---------USER Status

        //dialog
        showSuccessDialog();
        //Score
        score_general += getGivenPointForRiddle(riddleId);
        _valueFor_userGrantedScoreFor1Level += getGivenPointForRiddle(riddleId);
        tv_currentScore.setText(String.valueOf(score_general));
        //movement
        moveToNextPager();

    }

    //Dialog Listener For Real Progress Go To onClickWrong to do yourWork
    @Override
    public void onFailed(int riddle_id) {


        //---------USER Status
        m_r_ans_wrong_count++;
        //---------USER Status

        //make sure he getting the right hint for his dialog
        if (riddle_id == riddleIdInArray(riddle_id)) {
            RiddleWrongDialog.newInstance(getHintForDialog(riddle_id)).show(getSupportFragmentManager(), "WrongAnswer");
//
        } else {
            Log.e(TAG, "onFailed: id not found");
        }
        //doing this any way
        Log.d(TAG, "onFailed(): " + "Wrong Bro");

        //riddleIdForArraySkipped = riddle_id;
    }
    //--------------------------------[USER SOLVED RIDDLE]--------------------------------------------------------

    //TODO*********************************************************************************************


    //Dialog Okay Listener==============================================================================================================
    @Override
    public void onClickForWrongAnswer() {
        // do what u want if user press okay btn in dialogWrongAnswer
        //Do Not take away any amount of points
        Log.i(TAG, "onClick() returned: " + "Wrong Answer and OkayDia Clicked");
        moveToNextPager();
    }

    @Override
    public void onClickForSkippedDialog() {
        isClicked = true;
        Log.i(TAG, "onClick() returned: " + "Skipped and OkayDiaSkip Clicked");
        moveToNextPagerSkipped();
    }
    //Dialog Okay Listener==============================================================================================================

    private void moveToNextPager() {
        int new_currentViewItem_ = viewPager2.getCurrentItem();
        if (new_currentViewItem_ == Objects.requireNonNull(viewPager2.getAdapter()).getItemCount() - 1) {
            //** here reached the last page so do ->
            new FinishedRiddlesDialog().show(getSupportFragmentManager(), "DoneRiddles");
            new Handler().postDelayed(this::onBackPressed, 3250);
//            return true;
        } else {
            //---------USER Status
            m_riddles_answered_count++;
            //---------
            viewPager2.setCurrentItem(new_currentViewItem_ + 1, false);
            tv_standing_riddle.setText(String.valueOf(new_currentViewItem_ + 2));
//            return false;
        }
    }

    private void moveToNextPagerSkipped() {
        score_general = score_general - 3;
        tv_currentScore.setText(String.valueOf(score_general));
        int new_currentViewItem_ = viewPager2.getCurrentItem();
        if (new_currentViewItem_ == Objects.requireNonNull(viewPager2.getAdapter()).getItemCount() - 1) {
            //** here reached the last page so do ->
            new FinishedRiddlesDialog().show(getSupportFragmentManager(), "DoneRiddles");
            new Handler().postDelayed(this::onBackPressed, 3250);
        } else {
            viewPager2.setCurrentItem(new_currentViewItem_ + 1);
            tv_standing_riddle.setText(String.valueOf(new_currentViewItem_ + 2));
        }
    }

    private void showSuccessDialog() {
        new RiddleSuccessDialog().show(getSupportFragmentManager(), "RightAnswer");
    }


    //implemented viewOnClick//for Views in activity pressed
    //such as the floating action btn
    private void showSkipDialog() {
        RiddleSkipDialog.newInstance("If you skip (-3) score !").show(getSupportFragmentManager(), "Skip this Riddle");
    }


    //TODO^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    // ****************************************************************
    //helper TOOLS start
    private int riddleIdInArray(int riddle_id) {
        return riddlesArrayList.get(riddle_id - 1).getRiddle_num();
    }

    private String getHintForDialog(int position) {
        return riddlesArrayList.get(position - 1).getHintAnswer();
    }

    private int getGivenPointForRiddle(int position) {
        return riddlesArrayList.get(position - 1).getRiddleGivenPoint();
    }

//    private int getTimeForRiddle(int position) {
//        return riddlesArrayList.get(position - 1).getRiddleTimeBySec();
//    }
    //TODO^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    // ****************************************************************
    // TOOLS end


    //Timer

    //TODO+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // STOPSHIP: 12/30/2022 TIMER


    /*-
    //Dialog for each done
    private void doOnTimerDone() {
        RiddleTimeOutDialog.newInstance("Sorry Time is Up").show(getSupportFragmentManager(), "TimeOut");
        countDownTimer.cancel();

    }

*/
//    @Override
//    public void onClickForTimeOut() {
//        if (onTimerFinished()) moveToNextPager();
//    }
    //TODO+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    //TODO---------------------------------------------------------------------------------

    //-------------[connected with Shared for saving the Question standing Item]
    private void saveCurrentItemForUser() {
        currentViewItem = viewPager2.getCurrentItem();
        if (currentViewItem != 0) {
            AppSharedPreferences.getInstance(this).setViewPagerLastUsedItem(currentViewItem);
        }
    }

    private void getSavedItemForUser() {
        currentViewItem = AppSharedPreferences.getInstance(this).getViewPagerLastUsedItem();
        viewPager2.setCurrentItem(currentViewItem);
    }

    //[connected with Shared for saving the Question standing]
    @Override
    protected void onPause() {
        super.onPause();
        // STOPSHIP: 12/28/2022 SO IMPORTANT AND IT DID WORKED
        saveCurrentItemForUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // STOPSHIP: 12/28/2022 SO IMPORTANT AND IT DID WORKED
        getSavedItemForUser();
    }
    //-------------[connected with Shared for saving the Question standing Item]
    //TODO---------------------------------------------------------------------------------


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO done UPDATING Levels_evaluation which is (USERsCORE/ ON ALL)
        updateTheLevels_evaluation();//now every solved level has a value of percentage for how good did the user did
        //****************************************
        if (score_general >= 0) {
            AppSharedPreferences.getInstance(this).setScore(score_general);
        }
        //---------USER Status
        m_l_solved_count++;
        AppSharedPreferences.getInstance(this).setUserStatus(m_riddles_answered_count, m_r_ans_right_count, m_r_ans_wrong_count, m_l_solved_count);
        //---------USER Status
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (score_general >= 0) {
            AppSharedPreferences.getInstance(this).setScore(score_general);
        }
    }

    //✨✨✨✨✨✨✨✨✨✨✨✨👌👌👌👌👌👌✔✔✔✔✔✔
    //This Lovely method updates a single value in table Level
    private void updateTheLevels_evaluation() {
        double _finalValueFor_updateLevels_evaluation = _valueFor_userGrantedScoreFor1Level / _valueFor_allRiddlesScoreIn1Level;
        Log.i(TAG, "percentage%:" + (_finalValueFor_updateLevels_evaluation * 100));
        viewModel.updateLevels_evaluation(level_no, (_finalValueFor_updateLevels_evaluation * 100));
    }

    @Override
    public void setTimerTickDuration(long remainingTime) {
        tv_timer_forRiddle.setTextColor(remainingTime <= 5 ? getResources().getColor(R.color.shiny_red) : getResources().getColor(R.color.white));
        tv_timer_forRiddle.setText(String.valueOf(remainingTime / 1000));
    }

    @Override
    public void onTimerFinished() {
        RiddleTimeOutDialog.newInstance("Sorry But The Time is Over").show(getSupportFragmentManager(), "");
    }

    @Override
    public void onClickForTimeOut() {
        moveToNextPager();
    }

    @Override
    public void onFloatingBtnSkipClicked() {
        showSkipDialog();
        if (isClicked) {
            return;
        }
        setClicked(false);
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

}