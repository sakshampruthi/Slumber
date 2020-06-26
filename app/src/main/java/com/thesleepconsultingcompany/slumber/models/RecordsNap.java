package com.thesleepconsultingcompany.slumber.models;

public class RecordsNap {
    String date;
    String bedTime;
    String SleepTime;
    String WakeTime;

    public RecordsNap(String date, String bedTime, String sleepTime, String wakeTime) {
        this.date = date;
        this.bedTime = bedTime;
        SleepTime = sleepTime;
        WakeTime = wakeTime;
    }

    public String getDate() {
        return date;
    }

    public String getBedTime() {
        return bedTime;
    }

    public String getSleepTime() {
        return SleepTime;
    }

    public String getWakeTime() {
        return WakeTime;
    }
}
