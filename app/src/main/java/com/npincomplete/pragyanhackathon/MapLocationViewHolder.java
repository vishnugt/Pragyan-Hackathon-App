package com.npincomplete.pragyanhackathon;

/**
 * Created by Meliodas on 16/12/2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapLocationViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
    public TextView title;
    public TextView description;

    protected GoogleMap mGoogleMap;
    protected MapLocation mMapLocation;

    public MapView mapView;
    private Context mContext;

    public MapLocationViewHolder(Context context, View view) {
        super(view);

        mContext = context;

        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);
        mapView = (MapView) view.findViewById(R.id.map);

        mapView.onCreate(null);
        mapView.getMapAsync(this);
    }

    public void setMapLocation(MapLocation mapLocation) {
        mMapLocation = mapLocation;

        // If the map is ready, update its content.
        if (mGoogleMap != null) {
            updateMapContents();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        MapsInitializer.initialize(mContext);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        // If we have map data, update the map content.
        if (mMapLocation != null) {
            updateMapContents();
        }
    }

    protected void updateMapContents() {
        // Since the mapView is re-used, need to remove pre-existing mapView features.
        mGoogleMap.clear();

        // Update the mapView feature data and camera position.
        mGoogleMap.addMarker(new MarkerOptions().position(mMapLocation.center));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mMapLocation.center, 10f);
        mGoogleMap.moveCamera(cameraUpdate);
    }
}