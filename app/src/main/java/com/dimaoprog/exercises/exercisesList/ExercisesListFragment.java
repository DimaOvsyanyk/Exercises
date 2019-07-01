package com.dimaoprog.exercises.exercisesList;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.dimaoprog.exercises.R;
import com.dimaoprog.exercises.databinding.ExercisesListFragmentBinding;
import com.dimaoprog.exercises.entries.Category;
import com.dimaoprog.exercises.entries.Equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExercisesListFragment extends Fragment {

    private ExercisesListViewModel elViewModel;
    private ExercisesListFragmentBinding binding;
    private ExercisesListAdapter rvAdapter;
    private ExercisesListAdapter.ExerciseSelectedListener exerciseSelectedListener;

    public void setExerciseSelectedListener(ExercisesListAdapter.ExerciseSelectedListener exerciseSelectedListener) {
        this.exerciseSelectedListener = exerciseSelectedListener;
    }

    public static ExercisesListFragment newInstance(ExercisesListAdapter.ExerciseSelectedListener exerciseSelectedListener) {
        ExercisesListFragment fragment = new ExercisesListFragment();
        fragment.setExerciseSelectedListener(exerciseSelectedListener);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.exercises_list_fragment, container, false);
        elViewModel = ViewModelProviders.of(this).get(ExercisesListViewModel.class);
        binding.setExercisesListModel(elViewModel);
        setUpRecyclerView();
        elViewModel.getAllDataLoaded().observe(getViewLifecycleOwner(), this::observeLiveData);
        binding.btnExpandFilters.setOnClickListener(this::expandFilters);
        return binding.getRoot();
    }

    private void observeLiveData(boolean loaded) {
        if (loaded) {
            elViewModel.getCategoryFilter().observe(getViewLifecycleOwner(), this::setUpCategorySpinner);
            elViewModel.getEquipmentFilter().observe(getViewLifecycleOwner(), this::setUpEquipmentSpinner);
            elViewModel.getExerciseListLive().observe(getViewLifecycleOwner(), exerciseList -> {
                rvAdapter.submitList(exerciseList);
                rvAdapter.notifyDataSetChanged();
            });
        }
    }

    private void setUpRecyclerView() {
        rvAdapter = new ExercisesListAdapter(exerciseSelectedListener);
        binding.rvExercisesList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvExercisesList.setAdapter(rvAdapter);
        binding.rvExercisesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = llManager.getChildCount();
                int totalItemCount = llManager.getItemCount();
                int firstVisibleItemPositions = llManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPositions) >= (totalItemCount - 10) && firstVisibleItemPositions >= 0) {
                    elViewModel.loadNextPage();
                }
            }
        });
    }

    private ArrayAdapter<String> getSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void setUpEquipmentSpinner(List<Equipment> equipmentList) {
        List<String> newEquipmentList = new ArrayList<>();
        newEquipmentList.add("Choose equipment...");
        for (Equipment equipment : equipmentList) {
            newEquipmentList.add(equipment.getName());
        }
        ArrayAdapter<String> equipmentAdapter = getSpinnerAdapter();
        equipmentAdapter.addAll(newEquipmentList);
        binding.spinnerEquipment.setAdapter(equipmentAdapter);
        binding.spinnerEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    elViewModel.setEquipmentSelected(equipmentList.get(position - 1).getId());
                } else {
                    elViewModel.setEquipmentSelected(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpCategorySpinner(List<Category> categoryList) {
        List<String> newCategoryList = new ArrayList<>();
        newCategoryList.add("Choose category...");
        for (Category category : categoryList) {
            newCategoryList.add(category.getName());
        }
        ArrayAdapter<String> categoryAdapter = getSpinnerAdapter();
        categoryAdapter.addAll(newCategoryList);
        binding.spinnerCategories.setAdapter(categoryAdapter);
        binding.spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    elViewModel.setCategorySelected(categoryList.get(position - 1).getId());
                } else {
                    elViewModel.setCategorySelected(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void expandFilters(View view) {
        boolean notRotate = (view.getRotation() == 0);
        binding.layoutFilters.setVisibility(notRotate ? View.VISIBLE : View.GONE);
        view.animate().setDuration(150).rotation(notRotate ? 180 : 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        elViewModel.disposablesClear();
    }
}
