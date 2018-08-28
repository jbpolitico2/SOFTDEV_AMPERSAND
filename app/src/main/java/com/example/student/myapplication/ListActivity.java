package com.example.student.myapplication;

public class ListActivity {
    public String img;
    public String name;

    public ListActivity(String name, String img){
        this.img =img;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }
}
