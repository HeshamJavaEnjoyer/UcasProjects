package org.school.riddlemethis.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.school.riddlemethis.database.models.Riddles;

import java.util.List;

@Dao
public interface RiddleDao {
    @Insert
    void insertRiddles(Riddles riddles);

    @Insert
    void multiInsertRiddles(Riddles... riddles);

    //not for use
    @Update
    void updateRiddles(Riddles riddles);

    @Delete
    void deleteRiddles(Riddles riddles);

    //TODO So important is to use this ... and have a look at old project where u put a number into Fragment to sort the data
    @Query("select * from Riddles where riddle_type=:riddleType_Int and sub_level_num=:subLevel")
    LiveData<List<Riddles>> getRiddlesByTypeAndLevel(int riddleType_Int , int subLevel);

    // STOPSHIP: 12/14/2022 Must do
    //TODO build a query that dose n't need the RiddleType cuz i take care off that in fragment
    @Query("select * from Riddles where sub_level_num=:subLevel")
    LiveData<List<Riddles>> getRiddlesByLevelId(int subLevel);

}
