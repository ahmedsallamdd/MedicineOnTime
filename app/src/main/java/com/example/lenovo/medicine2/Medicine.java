package com.example.lenovo.medicine2;

/**
 * Created by LENOVO on 4/1/2018.
 */

public class Medicine {
    int id;
    private String name, dose, time, interval;
    public Medicine(int id, String name, String dose, String time, String interval){
        super();
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.time = time;
        this.interval = interval;
    }

    public int getId() {return id;}

    public String getName(){
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getTime() {
        return time;
    }

    public String getInterval() {return interval;}
}
