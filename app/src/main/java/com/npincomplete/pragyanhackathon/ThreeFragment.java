package com.npincomplete.pragyanhackathon;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class ThreeFragment extends ListFragment implements AdapterView.OnItemClickListener {

    ListView listView ;
    ProgressDialog progress;

    String auth_token;
    String phoneNum;
    String uName;


    public ThreeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    String[] stringarray = null;
    String[] idarray = null;
    String[] addressarr = null;
    String[] phonearr = null;
    String[] namearr = null;

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



        View view = inflater.inflate(R.layout.fragment_three, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);


        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1)

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
       // Toast.makeText(getActivity(), "Item: " + idarray[position], Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), DisplayAddr.class);
        intent.putExtra("address", addressarr[position]);
        intent.putExtra("phone", phonearr[position]);
        intent.putExtra("name", stringarray[position]);
        startActivity(intent);

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



    GPSTracker tracker;


    public void aftercomplete()
    {
        //Toast.makeText(getActivity(), outputresponse, Toast.LENGTH_SHORT).show();
        String temp = "";
        String tempid = "";
        String tempaadd = "";
        String temp2 = "";
        String temp3 = "";
        Log.d("json", outputresponse);
        try
        {
            JSONArray jsonarray = new JSONArray(outputresponse);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                temp = temp + jsonobject.getString("name") + "@@";
                tempid = tempid + jsonobject.getString("id") + "@@";
                tempaadd = tempaadd + jsonobject.getString("address") + "@@";
                temp2 = temp2 + jsonobject.getString("phone") + "@@";
            }
        }
        catch(JSONException j)
        {
            Log.d("json", "error");
        }

        idarray = tempid.split("@@");
        stringarray = temp.split("@@");
        addressarr = tempaadd.split("@@");
        phonearr = temp2.split("@@");

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, stringarray);
        listView.setAdapter(adapter);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        progress.dismiss();




    }





}
