package org.school.riddlemethis.status;

public class UserStatus {
    private int riddles_answered_count;
    private int r_ans_right_count;
    private int r_ans_wrong_count;
    private int l_solved_count;

    public UserStatus() {
    }

    //=======================================
    //Getter & Setter
    public int getRiddles_answered_count() {
        return riddles_answered_count;
    }

    public void setRiddles_answered_count(int riddles_answered_count) {
        this.riddles_answered_count = riddles_answered_count;
    }

    public int getR_ans_right_count() {
        return r_ans_right_count;
    }

    public void setR_ans_right_count(int r_ans_right_count) {
        this.r_ans_right_count = r_ans_right_count;
    }

    public int getR_ans_wrong_count() {
        return r_ans_wrong_count;
    }

    public void setR_ans_wrong_count(int r_ans_wrong_count) {
        this.r_ans_wrong_count = r_ans_wrong_count;
    }

    public int getL_solved_count() {
        return l_solved_count;
    }

    public void setL_solved_count(int l_solved_count) {
        this.l_solved_count = l_solved_count;
    }
}
