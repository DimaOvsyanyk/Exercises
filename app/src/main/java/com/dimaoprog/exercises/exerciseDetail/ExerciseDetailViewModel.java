package com.dimaoprog.exercises.exerciseDetail;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.dimaoprog.exercises.Converter;
import com.dimaoprog.exercises.dagger.DaggerAppComponent;
import com.dimaoprog.exercises.dataProvider.IExerciseDB;
import com.dimaoprog.exercises.entries.ExerciseImage;
import com.dimaoprog.exercises.entries.ExerciseImageResponse;
import com.dimaoprog.exercises.entries.ExerciseInfoSimple;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ExerciseDetailViewModel extends ViewModel {

    private IExerciseDB exerciseDB;
    private CompositeDisposable disposables = new CompositeDisposable();
    private int exerciseId;

    private ObservableField<ExerciseInfoSimple> exerciseInfoSimple = new ObservableField<>();
    private ObservableField<List<ExerciseImage>> imageList = new ObservableField<>();
    private ObservableBoolean loading = new ObservableBoolean();

    public ExerciseDetailViewModel() {
        exerciseDB = DaggerAppComponent.create().getExerciseApi();
    }

    private void loadDetailData() {
        loadImageList();
        loadExerciseInfo();
    }

    private void loadExerciseInfo() {
        setLoading(true);
        disposables.add(exerciseDB.getExerciseInfo(exerciseId)
                .map(Response::body)
                .map(Converter::infoToSimple)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(exerciseInfoSimple1 -> {
                    setExerciseInfoSimple(exerciseInfoSimple1);
                    setLoading(false);
                }));
    }

    private void loadImageList() {
        disposables.add(exerciseDB.getExerciseImages(exerciseId)
                .map(Response::body)
                .map(ExerciseImageResponse::getImageList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setImageList));
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
        loadDetailData();
    }

    public void disposablesClear() {
        disposables.clear();
    }

    public ObservableField<ExerciseInfoSimple> getExerciseInfoSimple() {
        return exerciseInfoSimple;
    }

    public void setExerciseInfoSimple(ExerciseInfoSimple exerciseInfoSimple) {
        this.exerciseInfoSimple.set(exerciseInfoSimple);
    }

    public ObservableField<List<ExerciseImage>> getImageList() {
        return imageList;
    }

    public void setImageList(List<ExerciseImage> imageList) {
        this.imageList.set(imageList);
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading.set(loading);
    }
}
