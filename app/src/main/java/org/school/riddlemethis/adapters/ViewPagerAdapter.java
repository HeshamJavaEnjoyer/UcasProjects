package org.school.riddlemethis.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.school.riddlemethis.database.models.Riddles;
import org.school.riddlemethis.fragment.RiddleFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<Riddles> riddlesList;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Riddles> riddlesList) {
        super(fragmentActivity);
        this.riddlesList = riddlesList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Riddles riddles = riddlesList.get(position);
        int idRiddle = riddles.getRiddle_num();

        //true or false Q
        if (riddlesList.get(position).getRiddle_type() == 1){
            int riddleTimeBySec = riddlesList.get(position).getRiddleTimeBySec();
            boolean isTheQuestionTrue = Boolean.parseBoolean(riddles.getTheRightInTextAnswer());
            String rQ = riddles.getRiddle_text();
            return RiddleFragment.newInstance(idRiddle,rQ,isTheQuestionTrue,riddleTimeBySec);
        }
        //Chooses Q
       else if (riddlesList.get(position).getRiddle_type() == 2){
            int riddleTimeBySec = riddlesList.get(position).getRiddleTimeBySec();
            String rQ = riddles.getRiddle_text(),theRightInTextAnswer = riddles.getTheRightInTextAnswer(),ans1 = riddles.getAnswer1(), ans2 = riddles.getAnswer2(), ans3 = riddles.getAnswer3(), ans4 = riddles.getAnswer4();
            return RiddleFragment.newInstance(idRiddle,rQ,ans1,ans2,ans3,ans4,theRightInTextAnswer,riddleTimeBySec);
        }
       //CompleteTheSentence Q
       else if (riddlesList.get(position).getRiddle_type() == 3){
            int riddleTimeBySec = riddlesList.get(position).getRiddleTimeBySec();
            String rQ = riddles.getRiddle_text() ,theRightInTextAnswer = riddles.getTheRightInTextAnswer();
            return RiddleFragment.newInstance(idRiddle,rQ,theRightInTextAnswer,riddleTimeBySec) ;
       }
       return new Fragment();
    }

    @Override
    public int getItemCount() {
        return riddlesList.size();
    }

    //===============
    @SuppressLint("NotifyDataSetChanged")
    public void setRiddlesList(List<Riddles> riddlesList) {
        this.riddlesList = riddlesList;
        notifyDataSetChanged();
    }
}
