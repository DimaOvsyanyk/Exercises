package com.dimaoprog.exercises.entries;

import android.databinding.BaseObservable;

public class ExerciseInfoSimple extends BaseObservable {

    private String name;
    private String category;
    private String description;
    private String muscles;
    private String musclesSecondary;
    private String equipment;

    public ExerciseInfoSimple(String name, String category, String description,
                              String muscles, String musclesSecondary, String equipment) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.muscles = muscles;
        this.musclesSecondary = musclesSecondary;
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMuscles() {
        return muscles;
    }

    public void setMuscles(String muscles) {
        this.muscles = muscles;
    }

    public String getMusclesSecondary() {
        return musclesSecondary;
    }

    public void setMusclesSecondary(String musclesSecondary) {
        this.musclesSecondary = musclesSecondary;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}