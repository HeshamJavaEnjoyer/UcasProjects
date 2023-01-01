package org.school.riddlemethis.database.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(foreignKeys = {@ForeignKey(entity = Levels.class,parentColumns = {"level_num"},childColumns = {"sub_level_num"},onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class Riddles {

    @PrimaryKey (autoGenerate = true)// START SHIP: 12/14/2022 IT WORKED TEMP VAR THAT AUTO INCREMENT
    private int auto_id_forDatabase;

    private int riddle_num;
    //the Question text
    private String riddle_text;
    //how many point the Riddle Gives
    private int riddleGivenPoint;
    // the Time for the RiddleQuestion
    private int riddleTimeBySec;

    //due the chose constructor
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    //answers dependees on the Question Type


    //chose and complete
    private String theRightInTextAnswer;

    // helper text
    private String hintAnswer;

    //from enum
    //ech type have a different constructor
    // notes that u need the number value out of the Enum
    private int riddle_type;

    //for DataBase Relate
    //attach with levels class
    private int sub_level_num;

    //Empty Con
    public Riddles() {
    }

    public Riddles(int riddle_num, String riddle_text, int riddleGivenPoint, int riddleTimeBySec, String answer1, String answer2, String answer3, String answer4, String theRightInTextAnswer, String hintAnswer, int riddle_type, int sub_level_num) {
        this.riddle_num = riddle_num;
        this.riddle_text = riddle_text;
        this.riddleGivenPoint = riddleGivenPoint;
        this.riddleTimeBySec = riddleTimeBySec;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.theRightInTextAnswer = theRightInTextAnswer;
        this.hintAnswer = hintAnswer;
        this.riddle_type = riddle_type;
        this.sub_level_num = sub_level_num;
    }

    // GETTER AND SETTER
    public int getRiddle_num() {
        return riddle_num;
    }

    public void setRiddle_num(int riddle_num) {
        this.riddle_num = riddle_num;
    }

    public String getRiddle_text() {
        return riddle_text;
    }

    public void setRiddle_text(String riddle_text) {
        this.riddle_text = riddle_text;
    }

    public int getRiddleGivenPoint() {
        return riddleGivenPoint;
    }

    public void setRiddleGivenPoint(int riddleGivenPoint) {
        this.riddleGivenPoint = riddleGivenPoint;
    }

    public int getRiddleTimeBySec() {
        return riddleTimeBySec;
    }

    public void setRiddleTimeBySec(int riddleTimeBySec) {
        this.riddleTimeBySec = riddleTimeBySec;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }



    public String getTheRightInTextAnswer() {
        return theRightInTextAnswer;
    }

    public void setTheRightInTextAnswer(String theRightInTextAnswer) {
        this.theRightInTextAnswer = theRightInTextAnswer;
    }

    public String getHintAnswer() {
        return hintAnswer;
    }

    public void setHintAnswer(String hintAnswer) {
        this.hintAnswer = hintAnswer;
    }

    public int getRiddle_type() {
        return riddle_type;
    }

    public void setRiddle_type(int riddle_type) {
        this.riddle_type = riddle_type;
    }

    public int getSub_level_num() {
        return sub_level_num;
    }

    public void setSub_level_num(int sub_level_num) {
        this.sub_level_num = sub_level_num;
    }

    //*********************************************************

    public int getAuto_id_forDatabase() {
        return auto_id_forDatabase;
    }

    public void setAuto_id_forDatabase(int auto_id_forDatabase) {
        this.auto_id_forDatabase = auto_id_forDatabase;
    }
}
