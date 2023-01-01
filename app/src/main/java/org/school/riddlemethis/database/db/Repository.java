package org.school.riddlemethis.database.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import org.school.riddlemethis.database.daos.LevelDao;
import org.school.riddlemethis.database.daos.RiddleDao;
import org.school.riddlemethis.database.models.Levels;
import org.school.riddlemethis.database.models.Riddles;

import java.util.List;

public class Repository {
    private final RiddleDao riddleDao;
    private final LevelDao levelDao;

    public Repository(Application application) {
        RiddleDatabase database = RiddleDatabase.getDatabase(application);
        riddleDao = database.riddleDao();
        levelDao = database.levelDao();
    }

    //Dao of Riddle

    public void insertRiddles(Riddles riddles){
        RiddleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                riddleDao.insertRiddles(riddles);
            }
        });
    }

    public void multiInsertRiddles(Riddles... riddles){
        RiddleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                riddleDao.multiInsertRiddles(riddles);
            }
        });
    }

    //not for use
    public void updateRiddles(Riddles riddles){

    }
    public void deleteRiddles(Riddles riddles){

    }

    //TODO So important is to use this ... and have a look at old project where u put a number into Fragment to sort the data
    public LiveData<List<Riddles>> getRiddlesByTypeAndLevel(int riddleType_Int , int subLevel){
        return riddleDao.getRiddlesByTypeAndLevel(riddleType_Int,subLevel);
    }

    public LiveData<List<Riddles>> getRiddlesByLevelId(int subLevel){
        return riddleDao.getRiddlesByLevelId(subLevel);
    }

    //****************************************************----LevelDao---********************************************************
    //Dao of Level

    public void insertLevels(Levels levels){
        RiddleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                levelDao.insertLevels(levels);
            }
        });
    }

    public void multiInsertLevels(Levels... levels){
        RiddleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                levelDao.multiInsertLevels(levels);
            }
        });
    }

    //********************************************************
    //not for use
    public void updateLevels(Levels levels){

    }

    //********************************************************
    //not for use  BUT THESE IS FOR USE
    public void updateLevels_evaluation(int level_num ,double newLevel_evaluation){
        RiddleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                levelDao.updateLevels_evaluation(level_num,newLevel_evaluation);
            }
        });
    }
    public void updateLevels_evaluationToDelete(double newLevel_evaluation){
        RiddleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                levelDao.updateLevels_evaluationToDelete(newLevel_evaluation);
            }
        });
    }




    //not for use
    public void deleteLevels(Levels levels){

    }
//********************************************************

    //TODO So important is to use this ... and have a look at old project where u put a number into Fragment to sort the data
    public LiveData<List<Levels>> getLevelBySubLevelAndMinPoint(int subLevel, int minPoint_Int){
        return levelDao.getLevelBySubLevelAndMinPoint(subLevel,minPoint_Int);
    }

    public LiveData<List<Levels>> getLevelBySubLevel(int subLevel){
        return levelDao.getLevelBySubLevel(subLevel);
    }

    public LiveData<List<Levels>> getAllLevels(){
        return levelDao.getAllLevels();
    }



//************************************************************************************
    // NOT FOR USE GETTER AND SETTER

    public RiddleDao getRiddleDao() {
        return riddleDao;
    }

    public LevelDao getLevelDao() {
        return levelDao;
    }
}

