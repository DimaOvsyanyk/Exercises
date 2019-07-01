package com.dimaoprog.exercises.exercisesList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.dimaoprog.exercises.databinding.ItemExerciseBinding;
import com.dimaoprog.exercises.entries.Exercise;

public class ExercisesListViewHolder extends RecyclerView.ViewHolder {

    private ItemExerciseBinding binding;

    public ExercisesListViewHolder(@NonNull ItemExerciseBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(Exercise exercise, ExercisesListAdapter.ExerciseSelectedListener exerciseSelectedListener) {
        binding.setExercise(exercise);
        itemView.setOnClickListener(__ -> exerciseSelectedListener.openExerciseDetails(exercise.getId()));
    }
}
