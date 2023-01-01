package org.school.riddlemethis.database.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Levels {
    @PrimaryKey
    private int level_num;

    private int minPointToUnlock;


    //states of the level
    private boolean level_statusOpen = false;

    //BIG TEST TIME
    private double Level_evaluation = 0;

    public Levels() {
    }



    //============================================================================
    public Levels(int level_num, int minPointToUnlock) {
        this.level_num = level_num;
        this.minPointToUnlock = minPointToUnlock;
    }


    //setter and getter
    public int getLevel_num() {
        return level_num;
    }

    public void setLevel_num(int level_num) {
        this.level_num = level_num;
    }

    public int getMinPointToUnlock() {
        return minPointToUnlock;
    }

    public void setMinPointToUnlock(int minPointToUnlock) {
        this.minPointToUnlock = minPointToUnlock;
    }

    //*************************************************************



    //*************************************************************
    public boolean isLevel_statusOpen() {
        return level_statusOpen;
    }

    public void setLevel_statusOpen(boolean level_statusOpen) {
        this.level_statusOpen = level_statusOpen;
    }

    //*************************************************************


    public double getLevel_evaluation() {
        return Level_evaluation;
    }

    public void setLevel_evaluation(int level_evaluation) {
        Level_evaluation = level_evaluation;
    }
}
