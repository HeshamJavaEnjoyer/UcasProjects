package org.school.riddlemethis.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.school.riddlemethis.database.models.Levels;

import java.util.List;


@Dao
public interface LevelDao {
    @Insert
    void insertLevels(Levels levels);

    @Insert
    void multiInsertLevels(Levels... levels);

    //********************************************************
    //not for use
    @Update
    void updateLevels(Levels levels)
    ;//********************************************************
    //not for use BUT THIS IS
    @Query("UPDATE Levels SET Level_evaluation =:newLevel_evaluation WHERE level_num =:level_num ")
    void updateLevels_evaluation(int level_num ,double newLevel_evaluation);

    @Query("UPDATE Levels SET Level_evaluation =:newLevel_evaluation")
    void updateLevels_evaluationToDelete(double newLevel_evaluation);




    //not for use
    @Delete
    void deleteLevels(Levels levels);
//********************************************************

    //TODO So important is to use this ... and have a look at old project where u put a number into Fragment to sort the data
    @Query("select * from Levels where level_num=:subLevel and minPointToUnlock=:minPoint_Int")
    LiveData<List<Levels>> getLevelBySubLevelAndMinPoint(int subLevel, int minPoint_Int);

    @Query("select * from Levels where level_num=:subLevel")
    LiveData<List<Levels>> getLevelBySubLevel(int subLevel);

    @Query("select * from Levels")
    LiveData<List<Levels>> getAllLevels();
}
