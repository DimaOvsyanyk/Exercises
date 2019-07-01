package com.dimaoprog.exercises;

import android.text.Html;

import com.dimaoprog.exercises.entries.ExerciseInfo;
import com.dimaoprog.exercises.entries.ExerciseInfoSimple;

public class Converter {

    public static String htmlToTextView(String htmlText) {
        return htmlText != null ? Html.fromHtml(htmlText).toString().trim() : "";
    }

    public static ExerciseInfoSimple infoToSimple(ExerciseInfo exerciseInfo) {
        return new ExerciseInfoSimple(exerciseInfo.getName(), exerciseInfo.getCategory().toString(),
                exerciseInfo.getDescription(), "Muscles: " + exerciseInfo.getMuscles().toString(),
                "Secondary muscles: " + exerciseInfo.getMusclesSecondary().toString(),
                "Equipment: " + exerciseInfo.getEquipment().toString());
    }
}
