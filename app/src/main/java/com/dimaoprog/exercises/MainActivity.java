package com.dimaoprog.exercises;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dimaoprog.exercises.exerciseDetail.ExerciseDetailFragment;
import com.dimaoprog.exercises.exercisesList.ExercisesListAdapter;
import com.dimaoprog.exercises.exercisesList.ExercisesListFragment;

public class MainActivity extends AppCompatActivity implements ExercisesListAdapter.ExerciseSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openExercisesListFragment();
    }

    private void openExercisesListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, ExercisesListFragment.newInstance(this))
                .commit();
    }

    @Override
    public void openExerciseDetails(int exerciseId) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_detail, ExerciseDetailFragment.newInstance(exerciseId))
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, ExerciseDetailFragment.newInstance(exerciseId))
                    .addToBackStack(null)
                    .commit();
        }
    }
}