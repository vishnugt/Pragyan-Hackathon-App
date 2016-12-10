package com.npincomplete.pragyanhackathon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Meliodas on 09/12/2016.
 */

public class OneFragment extends android.support.v4.app.Fragment implements GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    public OneFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        return false;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

}