package com.dimaoprog.exercises.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EquipmentListResponse extends BaseResponse {

    @SerializedName("results")
    @Expose
    private List<Equipment> equipmentList;

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }
}
