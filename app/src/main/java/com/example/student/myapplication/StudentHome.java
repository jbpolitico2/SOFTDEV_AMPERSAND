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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentHome extends Fragment{
    private ArrayList<ListDialyActivity> itemArrayList;
    private MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean success = false;
    Connection conn;
    public static String sectionpass, teampass;
    String id_pass;
    String first_name, last_name, middle_name, id_number, team, section, contact, email;
    TextView fname, id, teamtxt, sectiontxt, contacttxt, emailtxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.prof_home, container, false);
        id_pass = Login.getVariable();
        listView = (ListView) rootView.findViewById(R.id.exerciselist);
        itemArrayList = new ArrayList<ListDialyActivity>();
        CheckLogin checkLogin = new CheckLogin();
        checkLogin.execute("");
        SyncData orderData = new SyncData();
        orderData.execute("");



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
                id.setText("ID Number: "+id_pass);
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
                        first_name = rs.getString("first_name");
                        last_name = rs.getString("last_name");
                        middle_name = rs.getString("middle_name");
                        contact = rs.getString("contact");
                        email = rs.getString("email");
                        team = rs.getString("team");
                        section = rs.getString("section");
                        sectionpass = rs.getString("section");
                        teampass = rs.getString("team");
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

    private class SyncData extends AsyncTask<String, String, String> {

        String msg = "Connection Error";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getContext(), "Synchronizing","Loading... Please wait!",true);
        }

        @Override
        protected void onPostExecute(String msg) {
            progress.dismiss();
            // Toast.makeText(getContext(),msg +"",Toast.LENGTH_SHORT).show();
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
                    String query = "SELECT activity_record.id,activity_record.activity,activity_record.activity_desc,activity_record.team,activity_record.section,activity_record.coach_id,CONVERT (VARCHAR(10), activity_record.date, 107) as date, CONVERT (VARCHAR(10), time, 8) as time from activity_record WHERE section = '" + section + "' OR team ='" + team + "'";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs !=null)
                    {
                        while (rs.next()){

                            try {
                                itemArrayList.add(new ListDialyActivity(rs.getString("id"),rs.getString("activity")
                                        ,rs.getString("team"),rs.getString("section"),rs.getString("time"),rs.getString("date")));
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
            return null;

        }
    }
    public class MyAppAdapter extends BaseAdapter {




        public class ViewHolder{
            TextView activitytxt;
            TextView sectiontxt;
            TextView datetxt;
            TextView id;
            TextView timetxt;
        }

        public List<ListDialyActivity> parkingList;

        public Context context;
        ArrayList<ListDialyActivity> arrayList;

        private MyAppAdapter (List<ListDialyActivity> apps, Context context){

            this.parkingList = apps;
            this.context =context;
            arrayList = new ArrayList<ListDialyActivity>();
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
            MyAppAdapter.ViewHolder viewHolder = null;

            if (rowView == null){
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.dailyactivity_list,parent,false);
                viewHolder = new MyAppAdapter.ViewHolder();
                viewHolder.activitytxt = (TextView) rowView.findViewById(R.id.activity);
                viewHolder.sectiontxt = (TextView) rowView.findViewById(R.id.section);
                viewHolder.datetxt = (TextView) rowView.findViewById(R.id.date);
                viewHolder.id = (TextView) rowView.findViewById(R.id.id);
                viewHolder.timetxt = (TextView) rowView.findViewById(R.id.time);
                rowView.setTag(viewHolder);

            }
            else {
                viewHolder =(MyAppAdapter.ViewHolder)convertView.getTag();

            }

            viewHolder.activitytxt.setText(parkingList.get(position).getActivity()+"");
            viewHolder.sectiontxt.setText(parkingList.get(position).getSection()+parkingList.get(position).getTeam());
            viewHolder.datetxt.setText(parkingList.get(position).getDate()+"");
            viewHolder.id.setText(parkingList.get(position).getId()+"");
            viewHolder.timetxt.setText(parkingList.get(position).getTime()+"");
            return rowView;
        }

    }


    public static String getVariable1()
    {
        return sectionpass;
    }
    public static String getVariable2()
    {
        return teampass;
    }
}
