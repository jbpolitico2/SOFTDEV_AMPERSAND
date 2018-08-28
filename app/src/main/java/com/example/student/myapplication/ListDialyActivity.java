package com.example.student.myapplication;

public class ListDialyActivity {

    public String id,activity,team,section,time,date;


    public ListDialyActivity(String id, String activity,String team,String section, String time, String date) {
        this.id = id;
        this.activity = activity;
        this.team = team;
        this.section = section;
        this.time = time;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getActivity() {
        return activity;
    }

    public String getTeam() {
        return team;
    }

    public String getSection() {
        return section;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
