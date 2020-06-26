package com.thesleepconsultingcompany.slumber.models;

public class RecordsBeforeSleep {
    public String date;
    public String mood;
    public String beverageQuantity;
    public String beverageTime;
    public String exercise;
    public String exerciseDuration;
    public String activity_2_3hrsBefore;

    public RecordsBeforeSleep(String date, String mood, String beverageQuantity, String beverageTime, String exercise, String exerciseDuration, String activity_2_3hrsBefore) {
        this.date = date;
        this.mood = mood;
        this.beverageQuantity = beverageQuantity;
        this.beverageTime = beverageTime;
        this.exercise = exercise;
        this.exerciseDuration = exerciseDuration;
        this.activity_2_3hrsBefore = activity_2_3hrsBefore;
    }
    public RecordsBeforeSleep(){
        //Empty Constructor
    }

    public String getDate() {
        return date;
    }

    public String getMood() {
        return mood;
    }

    public String getBeverageQuantity() {
        return beverageQuantity;
    }

    public String getBeverageTime() {
        return beverageTime;
    }

    public String getExercise() {
        return exercise;
    }

    public String getExerciseDuration() {
        return exerciseDuration;
    }

    public String getActivity_2_3hrsBefore() {
        return activity_2_3hrsBefore;
    }
}
