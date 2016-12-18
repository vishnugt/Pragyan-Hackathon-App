package com.npincomplete.pragyanhackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

public class hospital_activity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {


    public Double tomovlat, tomovlong;

    private GoogleMap mMap;
    TextView tv;
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostpital_detail_activity);


        tv = (TextView)findViewById(R.id.textView2);
        String temp = "The Ambulance with number ";
        Intent intent = getIntent();

        tomovlat = Double.parseDouble(intent.getStringExtra("Lat") );
        tomovlong = Double.parseDouble( intent.getStringExtra("Long") );
        Float secondss = Float.parseFloat(intent.getStringExtra("Time") );
        temp = temp + intent.getStringExtra("Vehicle_no") + " will arrive in " + Float.toString(secondss) + " minutes.  Driver's (" + intent.getStringExtra("Name") + ") Phone Number is " + intent.getStringExtra("Phone");

        tv.setText(temp);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        GPSTracker tracker = new GPSTracker(getApplicationContext());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(tomovlat, tomovlong), 14));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(tomovlat, tomovlong))
                .title("Ambulance is here"));
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {
    return false;
    }
}