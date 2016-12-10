package com.npincomplete.pragyanhackathon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Meliodas on 09/12/2016.
 */

public class OneFragment extends Fragment implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    private GoogleMap mMap;
    public OneFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        GPSTracker tracker = new GPSTracker(getActivity());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(tracker.getLatitude(), tracker.getLongitude()), 14));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(tracker.getLatitude(), tracker.getLongitude()))
                .title("You are here!"));
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        return false;
    }


}