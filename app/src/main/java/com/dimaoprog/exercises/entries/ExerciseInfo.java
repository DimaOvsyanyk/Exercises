package com.dimaoprog.exercises.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExerciseInfo {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("muscles")
    @Expose
    private List<Muscle> muscles;
    @SerializedName("muscles_secondary")
    @Expose
    private List<Muscle> musclesSecondary;
    @SerializedName("equipment")
    @Expose
    private List<Equipment> equipment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Muscle> getMuscles() {
        return muscles;
    }

    public void setMuscles(List<Muscle> muscles) {
        this.muscles = muscles;
    }

    public List<Muscle> getMusclesSecondary() {
        return musclesSecondary;
    }

    public void setMusclesSecondary(List<Muscle> musclesSecondary) {
        this.musclesSecondary = musclesSecondary;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }
}
