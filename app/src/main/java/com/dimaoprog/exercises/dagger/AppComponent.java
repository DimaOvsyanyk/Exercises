package com.dimaoprog.exercises.dagger;

import com.dimaoprog.exercises.dataProvider.ExerciseRetrofit;
import com.dimaoprog.exercises.dataProvider.IExerciseDB;

import dagger.Component;

@Component (modules = ExerciseRetrofit.class)
public interface AppComponent {

    IExerciseDB getExerciseApi();
}
