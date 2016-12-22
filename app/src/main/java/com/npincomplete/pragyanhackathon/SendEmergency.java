package com.npincomplete.pragyanhackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendEmergency extends AppCompatActivity {

    Toolbar toolbar;
    EditText editText;
    ProgressDialog progress;

    String auth_token;
    String phoneNum;
    String uName;
    Integer etype;
    Integer desc;
    Integer numOfPeople;

    String outputresponse;
    JSONObject json;
    String fcm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences prefs = getSharedPreferences("db", MODE_PRIVATE);
        auth_token = prefs.getString("isRegistered", null);
        phoneNum = prefs.getString("phoneNum", null);
        uName = prefs.getString("uName", null);
        fcm = prefs.getString("fcm", "fcm");


        Intent intent = getIntent();
        etype = intent.getIntExtra("etype", 0);
        desc = intent.getIntExtra("desc", -1);



        editText = (EditText)findViewById(R.id.editText);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText.requestFocus();

    }

    public void sendemer(View view)
    {
        numOfPeople = Integer.parseInt(editText.getText().toString());
        GPSTracker tracker = new GPSTracker(this);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
        new LongOperation().execute(
                Double.toString(tracker.getLatitude()),
                Double.toString(tracker.getLongitude()),
                uName,
                phoneNum,
                etype.toString(),
                desc.toString(),
                numOfPeople.toString()
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        onBackPressed();
        return true;
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            json = new JSONObject();
            try
            {
                json.put("Lat",params[0]);
                json.put("Long", params[1]);
                json.put("Name", params[2]);
                json.put("Phone", params[3]);
                json.put("Type", Integer.parseInt(params[4]) );
                json.put("Description", Integer.parseInt(params[5]) );
                json.put("Number", Integer.parseInt(params[6]) );
                json.put("Token", fcm);

                Log.d("jsonoutput", json.toString());

            }catch (JSONException j)

            {
                Log.d("Second_Fragment", "Err");
            }

            try {
                URL url = new URL("http://52.66.134.228:4000/user/emergency");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                //connection.setRequestProperty("Authorization", "Bearer " + auth_token);
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(String.format( String.valueOf(json)));
                osw.flush();
                osw.close();


                InputStream stream = connection.getInputStream();
                InputStreamReader isReader = new InputStreamReader(stream );
                BufferedReader br = new BufferedReader(isReader );
                outputresponse = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getActivity().getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
            aftercomplete();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    GPSTracker tracker;


    public void aftercomplete()
    {
        Log.d("outputresponse", outputresponse);
        progress.dismiss();
        Toast.makeText(this, "Please wait for furhter notification", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:9498055829" ));
        startActivity(intent);

        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);


    }



}
