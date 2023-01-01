package org.school.riddlemethis.database.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.school.riddlemethis.database.models.Levels;
import org.school.riddlemethis.database.models.Riddles;

import java.util.List;

public class RiddleViewModel extends AndroidViewModel {
    private Repository repository;
    public RiddleViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }
    //Rep of Riddle
    public void insertRiddles(Riddles riddles){
        repository.insertRiddles(riddles);
    }

    public void multiInsertRiddles(Riddles... riddles){
        repository.multiInsertRiddles(riddles);
    }

    //not for use
    public void updateRiddles(Riddles riddles){

    }
    public void deleteRiddles(Riddles riddles){

    }

    //TODO So important is to use this ... and have a look at old project where u put a number into Fragment to sort the data
    public LiveData<List<Riddles>> getRiddlesByTypeAndLevel(int riddleType_Int , int subLevel){
        return repository.getRiddlesByTypeAndLevel(riddleType_Int,subLevel);
    }

    public LiveData<List<Riddles>> getRiddlesByLevelId(int subLevel){
        return repository.getRiddlesByLevelId(subLevel);
    }

    //****************************************************----LevelRep---********************************************************
    //Rep of Level

    public void insertLevels(Levels levels){
        repository.insertLevels(levels);
    }

    public void multiInsertLevels(Levels... levels){
        repository.multiInsertLevels(levels);
    }

    //********************************************************
    //not for use
    public void updateLevels(Levels levels){

    }
    //********************************************************
    //not for use BUT THIS IS FOR USE
    public void updateLevels_evaluation(int level_num ,double newLevel_evaluation){
        repository.updateLevels_evaluation(level_num,newLevel_evaluation);
    }

    public void updateLevels_evaluationToDelete(double newLevel_evaluation){
        repository.updateLevels_evaluationToDelete(newLevel_evaluation);
    }



    //not for use
    public void deleteLevels(Levels levels){

    }
//********************************************************

    //TODO So important is to use this ... and have a look at old project where u put a number into Fragment to sort the data
    public LiveData<List<Levels>> getLevelBySubLevelAndMinPoint(int subLevel, int minPoint_Int){
        return repository.getLevelBySubLevelAndMinPoint(subLevel,minPoint_Int);
    }

    public LiveData<List<Levels>> getLevelBySubLevel(int subLevel){
        return repository.getLevelBySubLevel(subLevel);
    }

    public LiveData<List<Levels>> getAllLevels(){
        return repository.getAllLevels();
    }
//*************************Getter And Setter For Rep**********************************

    public Repository getRepository() {
        return repository;
    }
    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
