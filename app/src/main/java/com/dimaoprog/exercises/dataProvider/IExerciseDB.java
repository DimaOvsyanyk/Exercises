package com.dimaoprog.exercises.dataProvider;

import com.dimaoprog.exercises.entries.CategoryListResponse;
import com.dimaoprog.exercises.entries.EquipmentListResponse;
import com.dimaoprog.exercises.entries.ExerciseImageResponse;
import com.dimaoprog.exercises.entries.ExerciseInfo;
import com.dimaoprog.exercises.entries.ExerciseListResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IExerciseDB {

    @GET("exercise/?language=2&status=2&limit=30")
    Single<Response<ExerciseListResponse>> getExerciseData(@Query("category") Integer category,
                                                           @Query("equipment") Integer equipment,
                                                           @Query("page") int page);

    @GET("exerciseinfo/{id}/")
    Single<Response<ExerciseInfo>> getExerciseInfo(@Path("id") int exerciseId);

    @GET("exerciseimage/")
    Single<Response<ExerciseImageResponse>> getExerciseImages(@Query("exercise") int exerciseId);

    @GET("exercisecategory/")
    Single<Response<CategoryListResponse>> getCategoryListData();

    @GET("equipment/")
    Single<Response<EquipmentListResponse>> getEquipmentListData();


}