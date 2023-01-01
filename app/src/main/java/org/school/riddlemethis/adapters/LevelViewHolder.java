package org.school.riddlemethis.adapters;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.school.riddlemethis.R;
import org.school.riddlemethis.database.models.Levels;

public class LevelViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView_level_state;
    TextView textView_level_item, textView_level_minPoint, level_evaluation_num;
    RatingBar ratingBarLevel_evaluation;

    public LevelViewHolder(@NonNull View itemView) {
        super(itemView);
        findViews();
    }
    private void findViews() {
        textView_level_item = itemView.findViewById(R.id.textView_level_item);
        textView_level_minPoint = itemView.findViewById(R.id.textView_level_minPoint);
        imageView_level_state = itemView.findViewById(R.id.imageView_level_state);

        level_evaluation_num = itemView.findViewById(R.id.level_evaluation_num);

        ratingBarLevel_evaluation = itemView.findViewById(R.id.ratingBarLevel_evaluation);
    }

    @SuppressLint("SetTextI18n")
    protected void setBindData(@NonNull Levels levels) {
        // set the num of the level
        textView_level_item.setText(String.valueOf(levels.getLevel_num()));
        // set the points that req for each level
        textView_level_minPoint.setText(String.valueOf(levels.getMinPointToUnlock()));
        // set the ratingBarLevel_evaluation each level
        float realLevel_evaluation = (float) levels.getLevel_evaluation();
        level_evaluation_num.setText("%"+realLevel_evaluation);
        checkLevelEvaluation(realLevel_evaluation);
        //if min point for this class = the user score
        imageView_level_state.setImageResource(levels.isLevel_statusOpen() ? R.drawable.ic_lock_open : R.drawable.ic_locked);

        //anotherWay        if (levels.isLevel_statusOpen()) {
//            imageView_level_state.setImageResource(R.drawable.ic_lock_open);
//        } else {
//            imageView_level_state.setImageResource(R.drawable.ic_locked);
//        }
    }

    private void checkLevelEvaluation(float realLevel_evaluation){
        if (realLevel_evaluation == 100) {
            ratingBarLevel_evaluation.setRating(3);
        } else if (realLevel_evaluation >= 50) {
            ratingBarLevel_evaluation.setRating(2.5F);
        } else if (realLevel_evaluation < 50 && realLevel_evaluation != 0) {
            ratingBarLevel_evaluation.setRating(1);
        }
    }

}
