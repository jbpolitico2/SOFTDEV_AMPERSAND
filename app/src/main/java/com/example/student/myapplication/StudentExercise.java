package com.example.student.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StudentExercise extends Fragment{
    String facility_stringmian;
    JSONObject facilityObject;
    JSONArray facilityArray;
    CoachActivityAdapter contactAdapterf;
    ListView exerciselist;
    FloatingActionButton floatingActionButton;
    TextView counter;
    private static  int count = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stud_exercise, container, false);
        exerciselist = (ListView) rootView.findViewById(R.id.exerciselist);
        contactAdapterf = new CoachActivityAdapter(getActivity(),R.layout.activity_layout);
        exerciselist.setAdapter(contactAdapterf);
        new facilityProcess().execute();
        return rootView;


    }


    class facilityProcess extends AsyncTask<Void, Void, String> {
        String facility_URL;
        String facility_STRING;

        @Override
        protected void onPreExecute() {
            facility_URL = "http://192.168.43.144/projectfit/exercise.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(facility_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((facility_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(facility_STRING).append("\n");
                }
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {

            facility_stringmian = result;
            try {
                facilityObject = new JSONObject(facility_stringmian);
                facilityArray  = new JSONObject(facility_stringmian).getJSONArray("Exercise");
                String activity_name,activity_decs;
                count = 0;

                while (count <facilityArray.length())
                {
                    JSONObject SO = facilityArray.getJSONObject(count);
                    activity_name = SO.getString("exercise");
                    activity_decs = SO.getString("exercise_desc");

                    CoachContactsActivity contactsf = new CoachContactsActivity(activity_name,activity_decs);
                    contactAdapterf.add(contactsf);
                    count ++;
                }

            } catch (JSONException e) {
                e.printStackTrace();


            }
        }
    }
}