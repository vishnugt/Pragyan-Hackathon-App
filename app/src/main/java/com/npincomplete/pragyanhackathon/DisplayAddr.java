package com.npincomplete.pragyanhackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayAddr extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_addr);

        tv = (TextView) findViewById(R.id.textView3);
        Intent intent = getIntent();
        tv.setText(intent.getStringExtra("address") + intent.getStringExtra("name") + intent.getStringExtra("phone"));

    }
}
