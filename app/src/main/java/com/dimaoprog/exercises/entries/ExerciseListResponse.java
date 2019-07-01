package com.dimaoprog.exercises.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExerciseListResponse extends BaseResponse {

    @SerializedName("results")
    @Expose
    private List<Exercise> exerciseList;

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @Override
    public String toString() {
        return "ExerciseListResponse{" +
                "count=" + getCount() +
                ", next='" + getNext() + '\'' +
                ", previous=" + getPrevious() +
                ", exercises=" + exerciseList.size() +
                '}';
    }
}