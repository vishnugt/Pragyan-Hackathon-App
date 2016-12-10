package com.npincomplete.pragyanhackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.System.in;

public class OTP extends AppCompatActivity {

    EditText et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        et3 = (EditText)findViewById(R.id.otp);

    }

    String outputresponse;
    ProgressDialog progress;

    public void btnfunc2(View v)
    {
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
        new LongOperation2().execute(et3.getText().toString());
    }


    JSONObject json;

    private class LongOperation2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {


            json = new JSONObject();
            try
            {
                json.put("otp",params[0]);

            }catch (JSONException j)

            {
                Log.d("Second_Fragment", "Err");
            }

            try {
                URL url = new URL("http://52.66.134.228:8080/api");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "text/plain");
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
            Toast.makeText(getApplicationContext(), outputresponse, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, OTP.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
