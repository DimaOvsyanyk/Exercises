package com.dimaoprog.exercises.dataProvider;

public class Repository {

    private static Repository repository;

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }




}
