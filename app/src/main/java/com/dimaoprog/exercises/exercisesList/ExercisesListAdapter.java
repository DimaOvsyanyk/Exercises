package com.dimaoprog.exercises.exercisesList;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dimaoprog.exercises.R;
import com.dimaoprog.exercises.databinding.ItemExerciseBinding;
import com.dimaoprog.exercises.entries.Exercise;

public class ExercisesListAdapter extends ListAdapter<Exercise, ExercisesListViewHolder> {

    public interface ExerciseSelectedListener{
        void openExerciseDetails(int exerciseId);
    }

    private ExerciseSelectedListener exerciseSelectedListener;

    protected ExercisesListAdapter(ExerciseSelectedListener exerciseSelectedListener) {
        super(DIFF_CALLBACK);
        this.exerciseSelectedListener = exerciseSelectedListener;
    }

    private static final DiffUtil.ItemCallback<Exercise> DIFF_CALLBACK = new DiffUtil.ItemCallback<Exercise>() {
        @Override
        public boolean areItemsTheSame(@NonNull Exercise exercise, @NonNull Exercise t1) {
            return exercise.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise exercise, @NonNull Exercise t1) {
            return exercise.getId() == t1.getId() &
                    exercise.getCategory() == t1.getCategory() &
                    exercise.getDescription().equals(t1.getDescription()) &
                    exercise.getName().equals(t1.getName());
        }
    };

    @NonNull
    @Override
    public ExercisesListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemExerciseBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_exercise, viewGroup, false);
        return new ExercisesListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesListViewHolder exercisesListViewHolder, int i) {
        exercisesListViewHolder.onBind(getItem(i), exerciseSelectedListener);
    }
}
