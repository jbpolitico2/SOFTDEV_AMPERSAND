package com.example.student.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CoachHome extends Fragment{
    Connection conn;
    String id_pass;
    String first_name, last_name, middle_name, id_number, team, section, contact, email;
    TextView fname, id, teamtxt, sectiontxt, contacttxt, emailtxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.prof_home, container, false);
        id_pass = Login.getVariable();

        CheckLogin checkLogin = new CheckLogin();
        checkLogin.execute("");


        return rootView;
    }


    public class CheckLogin extends AsyncTask<String,String,String> {

        String z = "";
        Boolean isSuccess = false;
        String name = "";


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
            if (isSuccess) {
                id = (TextView) getActivity().findViewById(R.id.id);
                fname = (TextView) getActivity().findViewById(R.id.name);
                sectiontxt = (TextView)  getActivity().findViewById(R.id.section);
                teamtxt = (TextView)  getActivity().findViewById(R.id.team);
                contacttxt = (TextView)  getActivity().findViewById(R.id.contact);
                emailtxt = (TextView)  getActivity().findViewById(R.id.email);
                id.setText("ID Number: "+id_number);
                fname.setText(last_name +", "+first_name+" "+middle_name);
                sectiontxt.setText(section);
                teamtxt.setText(team);
                contacttxt.setText(contact);
                emailtxt.setText(email);
            } else {
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                conn = connectionclass();

                if (conn == null) {
                    z = "Check Your Internet Access!";
                } else {
                    String query = "SELECT * from COACH_INFO where id = '" + id_pass + "'";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        id_number = rs.getString("id");
                        first_name = rs.getString("first_name");
                        last_name = rs.getString("last_name");
                        middle_name = rs.getString("middle_name");
                        contact = rs.getString("contact");
                        email = rs.getString("email");
                        team = rs.getString("team");
                        section = rs.getString("section");
                  //      z = "Query Successful";
                        isSuccess = true;
                        conn.close();

                    } else {
                 //       z = "invalid query";
                        isSuccess = false;


                    }

                }
            }

            catch(Exception ex){
                isSuccess = false;
               // z = ex.getMessage();
                Log.d("sql error", z);
            }
            return z;


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
}
