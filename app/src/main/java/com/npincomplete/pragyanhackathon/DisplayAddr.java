package com.npincomplete.pragyanhackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Rating;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DisplayAddr extends AppCompatActivity {

    RatingBar rb, rb2;
    TextView hosp_name, hosp_addr, hosp_phone;
    String id = null;
    String name = null;
    String auth_token = null;
    String address = null;
    String phonenumber = null;
    String rating = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_addr);

        hosp_name = (TextView)findViewById(R.id.hosp_name);
        hosp_addr = (TextView)findViewById(R.id.hosp_addr);
        hosp_phone = (TextView)findViewById(R.id.hosp_phone);
        rb = (RatingBar) findViewById(R.id.ratingBar);
        rb2 = (RatingBar) findViewById(R.id.ratingBar2);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        auth_token = intent.getStringExtra("auth_token");
        address = intent.getStringExtra("address");
        phonenumber = intent.getStringExtra("phone");
        //tv.setText(intent.getStringExtra("address") + intent.getStringExtra("name") + intent.getStringExtra("phone"));
        name = intent.getStringExtra("name");
        rating = intent.getStringExtra("rating");

        hosp_name.setText(name);
        hosp_addr.setText(address);
        hosp_phone.setText(phonenumber);
        rb2.setRating(Float.parseFloat(rating));
    }

    public void btnfunc(View view)
    {
        Toast.makeText(this, "Thanks for Rating!", Toast.LENGTH_SHORT).show();



        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
        new LongOperation().execute( String.valueOf(Math.round(rb.getRating())) , id);
    }

    String outputresponse;

    String outputResponse = null;
    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {




            json = new JSONObject();
            try
            {
                json.put("Rating", Integer.parseInt(params[0]));
                json.put("Id", Integer.parseInt(id));
            }catch (JSONException j)

            {
                Log.d("Second_Fragment", "Err");
            }

            try {
                URL url = new URL("http://23b8e3b4.ngrok.io/user/rating");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + auth_token);
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(String.valueOf(json));
                osw.flush();
                osw.close();


                InputStream stream = connection.getInputStream();
                int n = 0;
                char[] buffer = new char[1024 * 4];
                InputStreamReader reader = new InputStreamReader(stream, "UTF8");
                StringWriter writer = new StringWriter();
                while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
                outputresponse = writer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }




    @Override
    protected void onPostExecute(String result) {
        //  Toast.makeText(getActivity().getApplicationContext(), outputresponse, Toast.LENGTH_SHORT).show();
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


    JSONObject json;
    StringBuilder total;

    ProgressDialog progress;

    GPSTracker tracker;


    public void aftercomplete()
    {
        progress.dismiss();

    }
}



