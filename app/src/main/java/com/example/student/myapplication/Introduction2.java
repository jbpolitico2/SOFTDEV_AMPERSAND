package com.example.student.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Introduction2 extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction2);
        new Handler(). postDelayed (new Runnable() {
            @Override
            public void run(){
                Intent welcome = new Intent(Introduction2.this, Login.class);
                startActivity(welcome);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
