package org.school.riddlemethis.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.school.riddlemethis.R;
import org.school.riddlemethis.database.models.Levels;
import org.school.riddlemethis.interfaces.ProcessCallback;

import java.util.ArrayList;

public class LevelsAdapter extends RecyclerView.Adapter<LevelViewHolder> {
    private ArrayList<Levels> levelsArrayList;
    private final ProcessCallback callback;


    public LevelsAdapter(ArrayList<Levels> levelsArrayList,ProcessCallback processCallback) {
        this.levelsArrayList = levelsArrayList;
        this.callback = processCallback;
    }


    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.levels_item,parent,false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        Levels levels = levelsArrayList.get(position);
        //set all levels into our items
        holder.setBindData(levels);

        //for clicking on any item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (levels.isLevel_statusOpen())
                    view.setBackgroundResource(R.drawable.shape_level_item_clicked);

               // Toast.makeText(view.getContext(), "Opened ? > "+levels.isLevel_statusOpen(), Toast.LENGTH_SHORT).show();

                callback.onItemClicked(levels.getLevel_num(),levels.isLevel_statusOpen());

            }
        });


    }

    @Override
    public int getItemCount() {
        return levelsArrayList.size();
    }


    //===================

    public void setLevelsArrayList(ArrayList<Levels> levelsArrayList) {
        this.levelsArrayList = levelsArrayList;
        notifyDataSetChanged();
//        notifyItemChanged(0,levelsArrayList.size());
    }
    //===================
}
