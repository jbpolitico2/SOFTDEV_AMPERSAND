package com.example.student.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.design.widget.FloatingActionButton;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class CoachExercise extends Fragment{

    private ArrayList<CoachListActivity> itemArrayList;
    private MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean success = false;
    private Connection conn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.prof_exercise, container, false);
        listView = (ListView) rootView.findViewById(R.id.exerciselist);
        itemArrayList = new ArrayList<CoachListActivity>();
        SyncData orderData = new SyncData();
        orderData.execute("");
        return rootView;
    }

    private class SyncData extends AsyncTask<String, String, String>{

        String msg = "Connection Error";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
          progress = ProgressDialog.show(getContext(), "Synchronizing","Loading... Please wait!",true);
        }

        @Override
        protected void onPostExecute(String msg) {
            progress.dismiss();
            Toast.makeText(getContext(),msg +"",Toast.LENGTH_SHORT).show();
            if(success == false){
            }
            else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, getContext());
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                }catch (Exception ex){

                }

            }

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                conn = connectionclass();

                if (conn == null) {
                    success = false;
                } else {
                    String query = "SELECT * FROM ACTIVITY_INFO";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs !=null)
                    {
                        while (rs.next()){

                            try {
                                itemArrayList.add(new CoachListActivity(rs.getString("activity"),rs.getString("activity_desc")));
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                     //   msg = "Found";
                        success = true;

                    }
                    else {
                       // msg = "No Data Found";
                        success = false;
                    }

                }
            }

            catch(Exception e){
               // e.printStackTrace();
               // Writer writer = new StringWriter();
               // e.printStackTrace(new PrintWriter(writer));
                //msg = writer.toString();
                success = false;
            }
            return msg;

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

    public class MyAppAdapter extends BaseAdapter{




        public class ViewHolder{
           TextView textActivity;
           TextView textActivityDesc;
       }

       public List <CoachListActivity> parkingList;

       public Context context;
       ArrayList<CoachListActivity> arrayList;

       private MyAppAdapter (List<CoachListActivity> apps, Context context){

           this.parkingList = apps;
           this.context =context;
           arrayList = new ArrayList<CoachListActivity>();
           arrayList.addAll(parkingList);
       }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

           View rowView = convertView;
           ViewHolder viewHolder = null;

           if (rowView == null){
               LayoutInflater inflater = getLayoutInflater();
               rowView = inflater.inflate(R.layout.activity_layout,parent,false);
               viewHolder = new ViewHolder();
               viewHolder.textActivity = (TextView) rowView.findViewById(R.id.txActivityName);
               viewHolder.textActivityDesc = (TextView) rowView.findViewById(R.id.txActivityDecs);
               rowView.setTag(viewHolder);

           }
           else {
               viewHolder =(ViewHolder)convertView.getTag();

           }

           viewHolder.textActivity.setText(parkingList.get(position).getName()+"");
            viewHolder.textActivityDesc.setText(parkingList.get(position).getImg()+"");
            return rowView;
        }

    }
}
