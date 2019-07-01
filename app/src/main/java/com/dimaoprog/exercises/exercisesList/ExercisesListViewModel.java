package com.dimaoprog.exercises.exercisesList;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.dimaoprog.exercises.dagger.DaggerAppComponent;
import com.dimaoprog.exercises.dataProvider.IExerciseDB;
import com.dimaoprog.exercises.entries.Category;
import com.dimaoprog.exercises.entries.Equipment;
import com.dimaoprog.exercises.entries.Exercise;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ExercisesListViewModel extends ViewModel {

    private IExerciseDB exerciseDB;
    private CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<List<Category>> categoryFilter = new MutableLiveData<>();
    private MutableLiveData<List<Equipment>> equipmentFilter = new MutableLiveData<>();

    private List<Exercise> exerciseList = new ArrayList<>();
    private MutableLiveData<List<Exercise>> exerciseListLive = new MutableLiveData<>();

    private int loadingPage;
    private int exercisesCount;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    private boolean loadingNow;
    private ObservableBoolean showProgress = new ObservableBoolean();

    private boolean firstTimeLoading = true;
    private boolean categoryFilterLoaded = false;
    private boolean equipmentFilterLoaded = false;
    private boolean exerciseListLoaded = false;

    private MutableLiveData<Boolean> allDataLoaded = new MutableLiveData<>();

    private Integer categorySelected = null;
    private Integer equipmentSelected = null;

    public ExercisesListViewModel() {
        exerciseDB = DaggerAppComponent.create().getExerciseApi();
        checkAllDataLoaded();
        getDataForFilters();
        loadFirstPage();
    }

    public void loadFirstPage() {
        firstTimeLoading = true;
        loadingPage = 1;
        getExercises();
    }

    public void loadNextPage() {
        if (hasNextPage & !loadingNow) {
            loadingPage++;
            getExercises();
        }
    }

    private void getDataForFilters() {
        disposables.add(exerciseDB.getCategoryListData()
                .map(Response::body)
                .map(it -> it.getCategoryList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    setCategoryFilter(it);
                    categoryFilterLoaded = true;
                    checkAllDataLoaded();
                }));
        disposables.add(exerciseDB.getEquipmentListData()
                .map(Response::body)
                .map(it -> it.getEquipmentList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    setEquipmentFilter(it);
                    equipmentFilterLoaded = true;
                    checkAllDataLoaded();
                }));
    }

    private void getExercises() {
        setLoadingNow(true);
        disposables.add(exerciseDB.getExerciseData(categorySelected, equipmentSelected, loadingPage)
                .map(Response::body)
                .map(it -> {
                    exercisesCount = it.getCount();
                    hasNextPage = it.getNext() != null;
                    hasPreviousPage = it.getPrevious() != null;
                    return it.getExerciseList();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    if (firstTimeLoading) {
                        setExerciseList(it);
                        firstTimeLoading = false;
                        if (!(categoryFilterLoaded & equipmentFilterLoaded & exerciseListLoaded)) {
                            exerciseListLoaded = true;
                            checkAllDataLoaded();
                        }
                    } else {
                        addNewExercisesToList(it);
                    }
                    setLoadingNow(false);
                }));
    }

    private void addNewExercisesToList(List<Exercise> newExerciseList) {
        exerciseList.addAll(newExerciseList);
        setExerciseListLive(exerciseList);
    }

    private void checkAllDataLoaded() {
        setAllDataLoaded(categoryFilterLoaded & equipmentFilterLoaded & exerciseListLoaded);
    }

    public void disposablesClear() {
        disposables.clear();
    }

    public Integer getCategorySelected() {
        return categorySelected;
    }

    public void setCategorySelected(Integer categorySelected) {
        this.categorySelected = categorySelected;
    }

    public Integer getEquipmentSelected() {
        return equipmentSelected;
    }

    public void setEquipmentSelected(Integer equipmentSelected) {
        this.equipmentSelected = equipmentSelected;
    }

    public MutableLiveData<List<Category>> getCategoryFilter() {
        return categoryFilter;
    }

    public void setCategoryFilter(List<Category> categoryFilter) {
        this.categoryFilter.setValue(categoryFilter);
    }

    public MutableLiveData<List<Equipment>> getEquipmentFilter() {
        return equipmentFilter;
    }

    public void setEquipmentFilter(List<Equipment> equipmentFilter) {
        this.equipmentFilter.setValue(equipmentFilter);
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
        setExerciseListLive(exerciseList);
    }

    public MutableLiveData<List<Exercise>> getExerciseListLive() {
        return exerciseListLive;
    }

    public void setExerciseListLive(List<Exercise> exerciseList) {
        this.exerciseListLive.setValue(exerciseList);
    }

    public MutableLiveData<Boolean> getAllDataLoaded() {
        return allDataLoaded;
    }

    public void setAllDataLoaded(boolean allDataLoaded) {
        this.allDataLoaded.setValue(allDataLoaded);
    }

    public boolean isLoadingNow() {
        return loadingNow;
    }

    public void setLoadingNow(boolean loadingNow) {
        this.loadingNow = loadingNow;
        setShowProgress(loadingNow);
    }

    public ObservableBoolean getShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress.set(showProgress);
    }
}
