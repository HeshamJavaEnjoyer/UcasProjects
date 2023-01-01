package org.school.riddlemethis.enums;

public enum RiddleType {

    TrueOrFalse(1),
    ChooseTheCorrectAnswer(2),
    CompleteTheSentence(3);

    public final int riddleTypeNum;

    RiddleType(int riddleTypeNum){
        this.riddleTypeNum = riddleTypeNum;
    }
}
