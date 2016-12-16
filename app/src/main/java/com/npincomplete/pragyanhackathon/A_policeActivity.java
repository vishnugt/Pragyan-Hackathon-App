package com.npincomplete.pragyanhackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class A_policeActivity extends AppCompatActivity {


    ListView listView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_police);


        //UI setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] descArray = {"Robbery/Theft/Burglary","Street Fights","Property Conflicts","Self-inflicted injuries/Attempted suicides",
                "Theft","Fighting","Public Nuisance","Missing",
                "Kidnappings", "Traffic Problems", "Forceful actions, riots etc", "Others"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, descArray);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3)
            {
                Object listItem = listView.getItemAtPosition(pos);
                Intent intent = new Intent(getApplicationContext(), SendEmergency.class);
                intent.putExtra("etype",2);
                intent.putExtra("desc", pos);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        onBackPressed();
        return true;
    }
}
