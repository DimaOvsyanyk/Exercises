package com.dimaoprog.exercises.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExerciseImageResponse extends BaseResponse {

    @SerializedName("results")
    @Expose
    private List<ExerciseImage> imageList;

    public List<ExerciseImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<ExerciseImage> imageList) {
        this.imageList = imageList;
    }
}
