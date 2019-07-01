package com.dimaoprog.exercises.exerciseDetail;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimaoprog.exercises.R;
import com.dimaoprog.exercises.databinding.ExerciseDetailFragmentBinding;

import java.util.Objects;

public class ExerciseDetailFragment extends Fragment {

    private ExerciseDetailViewModel edViewModel;
    private static final String EXERCISE_ID = "exerciseId";

    public static ExerciseDetailFragment newInstance(int exerciseId) {
        Bundle args = new Bundle();
        args.putInt(EXERCISE_ID, exerciseId);
        ExerciseDetailFragment fragment = new ExerciseDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ExerciseDetailFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.exercise_detail_fragment, container, false);
        edViewModel = ViewModelProviders.of(this).get(ExerciseDetailViewModel.class);
        edViewModel.setExerciseId(Objects.requireNonNull(getArguments()).getInt(EXERCISE_ID));
        binding.setDetailModel(edViewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        edViewModel.disposablesClear();
    }
}