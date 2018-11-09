package com.example.brad.fitaid;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class JournalViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> exercises = new MutableLiveData<>();


    public void setExercises(ArrayList<String> input) {
        exercises.setValue(input);
    }

    public LiveData<ArrayList<String>> getExercises() {
        return exercises;
    }
}
