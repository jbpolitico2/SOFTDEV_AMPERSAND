package com.example.student.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";
    Connection conn;
    String type, format;
    String hdate, htime;
    public static String id;
    Spinner activitySpin, teamSpin, sectionSpin;
    String factivity, factivityDesc, ftime, fdate, fteam, fsection, fcoachID;
    private DatePickerDialog.OnDateSetListener mDateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activitySpin =(Spinner) findViewById(R.id.activity);
        final EditText timeEtx = (EditText) findViewById(R.id.time);
        final EditText dateEtx = (EditText) findViewById(R.id.date);
        final EditText activityDescEtx = (EditText) findViewById(R.id.activityDesc);
        teamSpin = (Spinner) findViewById(R.id.team);
        sectionSpin = (Spinner) findViewById(R.id.section);
        Button submit = (Button) findViewById(R.id.submit);
        Button cancel = (Button) findViewById(R.id.cancel);


        acitvitySpinner();
        sectionSpinner();
        teamSpinner();

        timeEtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        timeEtx.setText(hourOfDay + " : "+ minute +" "+ format);
                        htime = hourOfDay+":"+minute+":"+"00";
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });
        dateEtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int month =cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog =new DatePickerDialog(AddActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                           month  = month +1;
                           String date = month +"/"+day+"/"+year;
                           hdate = year+"/"+month+"/"+day;
                           dateEtx.setText(date);
            }
        };


        teamSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                sectionSpin.setEnabled(false);}
                else{
                    sectionSpin.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        sectionSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    teamSpin.setEnabled(false);}
                else{
                    teamSpin.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activitySpin.getSelectedItem() == "Please select activity" || dateEtx.getText().toString().isEmpty() || timeEtx.getText().toString().isEmpty() || activityDescEtx.getText().toString().isEmpty()){
                    Toast.makeText(AddActivity.this, "Please complete the data needed above",Toast.LENGTH_SHORT).show();
                } else if (sectionSpin.getSelectedItem() == "Please select Section" && teamSpin.getSelectedItem() == "Please select Team"){
                    Toast.makeText(AddActivity.this, "Please choose where to assign this activity",Toast.LENGTH_SHORT).show();
                }
                else if (sectionSpin.getSelectedItem() == "Please select Section" && teamSpin.getSelectedItem() != "Please select Team") {
                    fcoachID = Login.getVariable();
                    factivity = activitySpin.getSelectedItem().toString();
                    factivityDesc = activityDescEtx.getText().toString();
                    fdate = hdate;
                    ftime = htime;
                    fteam = teamSpin.getSelectedItem().toString();
                    AddingActivityTeam addingActivityTeam = new AddingActivityTeam();
                    addingActivityTeam.execute("");


                } else {
                    fcoachID = Login.getVariable();
                    factivity = activitySpin.getSelectedItem().toString();
                    factivityDesc = activityDescEtx.getText().toString();
                    fdate = hdate;
                    ftime = htime;
                    fsection = sectionSpin.getSelectedItem().toString();
                    AddingActivitySection addingActivitySection = new AddingActivitySection();
                    addingActivitySection.execute("");


                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddActivity.this);
                builder1.setTitle("Cancel Form");
                builder1.setMessage("Do you wish to drop this form?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });



    }

    public void acitvitySpinner(){
        try {
            Connection con = connectionclass();
            if (con == null) {

            } else {

                String query = "SELECT * FROM ACTIVITY_INFO";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                ArrayList<String> brandList = new ArrayList<String>();

                brandList.add("Please select activity");

                while (rs.next()) {
                    String brand = rs.getString("activity");
                    brandList.add(brand);
                }

                ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                        android.R.layout.simple_list_item_1, brandList);
                activitySpin.setAdapter(NoCoreAdapter);

                stmt.close();
                rs.close();
            }
        }

        catch (Exception ex)
        { }
    }
    public void sectionSpinner(){
        try {
            Connection con = connectionclass();
            if (con == null) {

            } else {

                String query = "select * from SECTION_INFO";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                ArrayList<String> brandList = new ArrayList<String>();

                brandList.add("Please select Section");

                while (rs.next()) {
                    String brand = rs.getString("section");
                    brandList.add(brand);
                }

                ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                        android.R.layout.simple_list_item_1, brandList);
                sectionSpin.setAdapter(NoCoreAdapter);

                stmt.close();
                rs.close();
            }
        }

        catch (Exception ex)
        { }
    }
    public void teamSpinner(){
        try {
            Connection con = connectionclass();
            if (con == null) {

            } else {

                String query = "select * from TEAM_INFO";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                ArrayList<String> brandList = new ArrayList<String>();

                brandList.add("Please select Team");

                while (rs.next()) {
                    String brand = rs.getString("team");
                    brandList.add(brand);
                }

                ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                        android.R.layout.simple_list_item_1, brandList);
                teamSpin.setAdapter(NoCoreAdapter);

                stmt.close();
                rs.close();
            }
        }

        catch (Exception ex)
        { }
    }

    public class AddingActivityTeam extends AsyncTask<String,String,String> {

        String z = "";
        Boolean isSuccess = false;
        String name = "";


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
          Toast.makeText(AddActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess){
                finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                conn = connectionclass();

                if (conn == null) {
                    z = "Check Your Internet Access!";
                } else {
                    String query = "INSERT INTO ACTIVITY_RECORD (activity,activity_desc,coach_id,time,date,team) VALUES('" + factivity + "','" + factivityDesc + "','" + fcoachID + "','" + ftime + "','"  + fdate + "','" + fteam + "')";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs.next()) {

                      z = "Activity Successfully Added!";
                        isSuccess = true;
                        conn.close();

                    } else {
                       z = "invalid query";
                        isSuccess = false;


                    }

                }
            }

            catch(Exception ex){
                isSuccess = false;
              //  z = ex.getMessage();
                Log.d("sql error", z);
            }
            return z;


        }
    }
    public class AddingActivitySection extends AsyncTask<String,String,String> {

        String z = "";
        Boolean isSuccess = false;
        String name = "";


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
          Toast.makeText(AddActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess){
                Toast.makeText(AddActivity.this, "Activity Successfully Added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                conn = connectionclass();

                if (conn == null) {
                    z = "Check Your Internet Access!";
                } else {
                    String query = "INSERT INTO ACTIVITY_RECORD (activity,activity_desc,coach_id,time,date,section) VALUES('" + factivity + "','" + factivityDesc + "','" + fcoachID + "','" + ftime + "','"  + fdate + "','" + fsection + "')";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                        isSuccess = true;
                        conn.close();


                }
            }

            catch(Exception ex){
                isSuccess = true;
                //z = ex.getMessage();
               // Log.d("sql error", z);
            }
            return null;


        }
    }


    @SuppressLint("NewApi")
    public Connection connectionclass(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection= null;
        String ConnectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://softdevs.database.windows.net:1433;DatabaseName=fitnessproject;user=projectfit@softdevs;password=Amper&and1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se)
        {
            Log.e("error here 1: ",se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2: ",e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3: ",e.getMessage());
        }
        return connection;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void selectedTimeFormat(int hour){

        if (hour == 0){
            hour += 12;
            format ="AM";
        }else if (hour == 12){
            format ="PM";
        }else if (hour >12){
            hour -= 12;
            format ="PM";
        }else {
            format = "AM";
        }

    }


}
