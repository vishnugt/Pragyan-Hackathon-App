package com.npincomplete.pragyanhackathon;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Meliodas on 09/12/2016.
 */

public class TwoFragment extends ListFragment implements AdapterView.OnItemClickListener {

    ListView listView ;
    ProgressDialog progress;

    String auth_token;
    String phoneNum;
    String uName;


    public TwoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    String[] stringarray = null;
    String[] idarray = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {

        SharedPreferences prefs = getActivity().getSharedPreferences("db", MODE_PRIVATE);
        auth_token = prefs.getString("isRegistered", null);
        phoneNum = prefs.getString("phoneNum", null);
        uName = prefs.getString("uName", null);

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
        new LongOperation().execute();



        View view = inflater.inflate(R.layout.fragment_two, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);


        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1)

    }



    int poss = 0;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
       // Toast.makeText(getActivity(), "Item: " + idarray[position], Toast.LENGTH_SHORT).show();

        poss = position;

        new AlertDialog.Builder(getActivity())
                .setTitle("Are you sure?")
                .setMessage("This will place an ambulance request! Use with caution! Make use of the next tab for hospital details")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        progress = new ProgressDialog(getActivity());
                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(false);
                        progress.show();
                        new LongOperation2().execute(
                                Double.toString(tracker.getLatitude()),
                                Double.toString(tracker.getLongitude()),
                                idarray[poss],
                                uName,
                                phoneNum);
                        }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    String outputresponse = "a a";

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL("https://data.archon40.hasura-app.io/v1/query");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + auth_token);
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(String.format( String.valueOf("{\n" +
                        "\t\"type\":\"select\",\n" +
                        "\t\"args\":{\n" +
                        "\t\t\"table\":\"hospital\",\n" +
                        "\t\t\"columns\":[\"id\",\"name\",\"address\",\"phone\"]\n" +
                        "\t}\n" +
                        "}")));
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

    private class LongOperation2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {


            json = new JSONObject();
            try
            {
                json.put("lat",params[0]);
                json.put("long", params[1]);
                json.put("Hosp_id", Integer.parseInt(params[2]));
                json.put("name", params[3]);
                json.put("phone", params[4]);
            }catch (JSONException j)

            {
                Log.d("Second_Fragment", "Err");
            }

            try {
                URL url = new URL("http://02a4ba0f.ngrok.io/user/emergency");
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
           // Toast.makeText(getActivity().getApplicationContext(), outputresponse, Toast.LENGTH_SHORT).show();
            //Intent outputintent = new Intent(getBaseContext(), OutputActivity.class);
            //outputintent.putExtra("json", outputresponse);
            //startActivity(outputintent);
            aftercomplete2();
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
        //Toast.makeText(getActivity(), outputresponse, Toast.LENGTH_SHORT).show();
        String temp = "";
        String tempid = "";

        Log.d("json", outputresponse);
        try
        {
            JSONArray jsonarray = new JSONArray(outputresponse);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String name = jsonobject.getString("name");
                temp = temp + name + "@@";
                tempid = tempid + jsonobject.getString("id") + "@@";
                Log.d("jsons", name + "@@");
            }
        }
        catch(JSONException j)
        {
            Log.d("json", "error");
        }

        idarray = tempid.split("@@");
        stringarray = temp.split("@@");
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, stringarray);
        listView.setAdapter(adapter);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        progress.dismiss();
        tracker = new GPSTracker(getActivity());




    }

    public void aftercomplete2()

    {
        progress.dismiss();
        Intent intent = new Intent(getActivity(), hospital_activity.class);
        intent.putExtra("outputresponse", outputresponse);
        startActivity(intent);


    }




}
