package com.example.student.myapplication;

import android.support.v4.app.FragmentActivity;

public class StudentContactActivity {
    private String activity_name,activity_decs;

    public StudentContactActivity(String activity_name, String activity_desc)
    {
        this.setActivity_name(activity_name);
        this.setActivity_desc(activity_desc);
    }

    public StudentContactActivity(FragmentActivity activity, int activity_layout) {
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getActivity_desc() {
        return activity_decs;
    }

    public void setActivity_desc(String activity_decs) {
        this.activity_decs = activity_decs;
    }

}
