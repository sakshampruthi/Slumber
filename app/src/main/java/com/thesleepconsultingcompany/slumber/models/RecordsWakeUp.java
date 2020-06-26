package com.thesleepconsultingcompany.slumber.models;

public class RecordsWakeUp {

    public String date;
    public String actBeforeSleep;
    public String actBeforeBed;
    public String times;
    public String duration;
    public String reason;

    public RecordsWakeUp(){

    }

    public RecordsWakeUp(String date, String actBeforeSleep, String actBeforeBed, String times, String duration, String reason) {
        this.date = date;
        this.actBeforeSleep = actBeforeSleep;
        this.actBeforeBed = actBeforeBed;
        this.times = times;
        this.duration = duration;
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public String getActBeforeSleep() {
        return actBeforeSleep;
    }

    public String getActBeforeBed() {
        return actBeforeBed;
    }

    public String getTimes() {
        return times;
    }

    public String getDuration() {
        return duration;
    }

    public String getReason() {
        return reason;
    }
}
