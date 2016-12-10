package com.npincomplete.pragyanhackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class hospital_activity extends AppCompatActivity {

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostpital_detail_activity);
        Intent intent = getIntent();

        String id = intent.getStringExtra("id");

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
        new LongOperation().execute();



    }


    String outputresponse = "a a";

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL("http://52.66.134.228:8080/api");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "text/plain");
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                //osw.write(String.format( String.valueOf(json)));
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
            Toast.makeText(getApplicationContext(), outputresponse, Toast.LENGTH_SHORT).show();
            //Intent outputintent = new Intent(getBaseContext(), OutputActivity.class);
            //outputintent.putExtra("json", outputresponse);
            //startActivity(outputintent);
            aftercomplete();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    public void aftercomplete()
    {
        progress.dismiss();
    }
}
