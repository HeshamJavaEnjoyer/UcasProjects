package org.school.riddlemethis.views;

import static org.school.riddlemethis.views.RiddleActivity.score_general;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.school.riddlemethis.R;
import org.school.riddlemethis.adapters.LevelsAdapter;
import org.school.riddlemethis.database.db.RiddleViewModel;
import org.school.riddlemethis.database.models.Levels;
import org.school.riddlemethis.interfaces.ProcessCallback;
import org.school.riddlemethis.prefs.AppSharedPreferences;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity implements ProcessCallback {
    private RecyclerView recyclerView;

    private TextView tv_play_act_currentScore;

    private RiddleViewModel viewModel;

    private LevelsAdapter levelsAdapter;


    private final ArrayList<Levels> levelsArrayList = new ArrayList<>();

    private int sharedScoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        viewModel = new ViewModelProvider(this).get(RiddleViewModel.class);
    }


    @Override
    protected void onStart() {
        super.onStart();
        findViews();
        levelsAdapter = new LevelsAdapter(levelsArrayList, this);
        recyclerView.setAdapter(levelsAdapter);
        initializer();
        //********

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initializer() {

        sharedScoreValue = AppSharedPreferences.getInstance(this).getScore();

        if (sharedScoreValue < score_general) {
            sharedScoreValue = score_general;
        }

        tv_play_act_currentScore.setText(String.valueOf(sharedScoreValue));

        initializeRecyclerAdapter();


    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);

        tv_play_act_currentScore = findViewById(R.id.tv_play_act_currentScore);
    }

    private void initializeRecyclerAdapter() {
        //TODO GET THE REAL SCORE
//        int tempScore = sharedScoreValue;

        viewModel.getAllLevels().observe(this, levels -> {
            Log.i("PlayActivity", "initializeRecyclerAdapter: observeHasRun");
            levelsArrayList.addAll(levels);

            for (int i = 0; i < levelsArrayList.size(); i++) {
                Log.i("PlayActivity", "inSide ForLoop: ScoreWorkingOn=>"+sharedScoreValue);
                levelsArrayList.get(i).setLevel_statusOpen(levelsArrayList.get(i).getMinPointToUnlock() <= sharedScoreValue);
                Log.i("inSide", "changingValuesOfLevels: crackedOpen:"+levelsArrayList.get(i).getLevel_num()+"=>"+levelsArrayList.get(i).isLevel_statusOpen());
            }
            //1 check this if multiply UPDATE WORKS PERFECTLY
            if (!levelsArrayList.isEmpty()){
                Log.i("PlayActivity", "initializeRecyclerAdapter: isNotEmpty");
                levelsArrayList.clear();
                levelsAdapter.setLevelsArrayList((ArrayList<Levels>) levels);
                Log.i("PlayActivity", "initializeRecyclerAdapter: AdapterHasTakenAValue");
            }
//                levelsAdapter.setLevelsArrayList((ArrayList<Levels>) levels);
        });
    }


    //Dos-not work-------UPDATE NOW IT DOSE
    @Override
    public void onItemClicked(int level_no, boolean level_statusOpen) {
        if (level_statusOpen) {

            Intent intent = new Intent(this, RiddleActivity.class);
            intent.putExtra("level_no", level_no);
            startActivity(intent);

        } else {
            //IF THE LEVEL CLOSED
            Snackbar.make(findViewById(R.id.recyclerView), "Closed Level Collect More Point and come back", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(this, "Closed Level Collect More Point and come back", Toast.LENGTH_SHORT).show();
        }

        //whatever else ALWAYS WORKS IN ANY CASE
        //Toast.makeText(PlayActivity.this, "WOW IT Did work", Toast.LENGTH_SHORT).show();
    }
    // STOPSHIP: 12/3/2022 @11:30
}