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

    public void setDate(String date) {
        this.date = date;
    }

    public String getactBeforeSleep() {
        return actBeforeSleep;
    }

    public void setactBeforeSleep(String actBeforeSleep) {
        this.actBeforeSleep = actBeforeSleep;
    }

    public String getactBeforeBed() {
        return actBeforeBed;
    }

    public void setactBeforeBed(String actBeforeBed) {
        this.actBeforeBed = actBeforeBed;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
