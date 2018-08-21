package com.example.student.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
    TextView RegisterET;
    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        UsernameEt = (EditText)findViewById(R.id.etUsername);
        PasswordEt = (EditText)findViewById(R.id.etPassword);
        RegisterET = (TextView) findViewById(R.id.register);

        RegisterET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(
                        Login.this);
                alert.setTitle("Forgot Password");
                alert.setMessage("Please proceed to Information Technology Resource Department (ITRD) to assist you with retrieving password. ");
                alert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                alert.show();

            }
        });
    }

    public void OnLogin(View view) {
       if (UsernameEt.getText().toString().isEmpty() || PasswordEt.getText().toString().isEmpty()) {

           Toast.makeText(this, "Please complete the field above!",Toast.LENGTH_SHORT).show();

        }
        else
       {
           CheckLogin checkLogin = new CheckLogin();
           checkLogin.execute("");
           PasswordEt.setText("");



       }


    }

    public class CheckLogin extends AsyncTask<String,String,String>{

        String z = "";
        Boolean isSuccess = false;
        String name = "";


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(Login.this,r,Toast.LENGTH_SHORT).show();
            if (isSuccess){
                Toast.makeText(Login.this,"login success",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(Login.this,"login failed",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                conn = connectionclass();

                if (conn == null){
                    z = "Check Your Internet Access!";
                }else{
                    String username = UsernameEt.getText().toString();
                    String password = PasswordEt.getText().toString();
                    String query = "select * from STUDENT_INFO where username like '" + username + "' and password like '" + password + "';";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs.next()){
                        z = "student";
                        isSuccess = true;
                        conn.close();

                    } else {
                        isSuccess = false;
                        query = "select * from COACH_INFO where username like '" + username + "' and password like '" + password + "';";
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(query);

                        if (rs.next()){
                            z = "coach";
                            isSuccess = true;
                            conn.close();

                        } else
                        {   z = "invalid query";
                            isSuccess = false;

                        }
                    }

                }

            } catch (Exception ex) {
                isSuccess =false;
                z=ex.getMessage();
                Log.d("sql error",z);
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
