package com.npincomplete.pragyanhackathon;

/**
 * Created by Meliodas on 16/12/2016.
 */

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.MapView;

public abstract class MapListActivity extends ActionBarActivity {

    protected MapLocationAdapter mListAdapter;
    protected RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d__ambulance);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // Determine the number of columns to display, based on screen width.
        int rows = 1;
        GridLayoutManager layoutManager = new GridLayoutManager(this, rows, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mListAdapter = createMapListAdapter();

        // Delay attaching Adapter to RecyclerView until we can ensure that we have correct
        // Google Play service version (in onResume).
    }

    protected abstract MapLocationAdapter createMapListAdapter();

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (mListAdapter != null) {
            for (MapView m : mListAdapter.getMapViews()) {
                m.onLowMemory();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mListAdapter != null) {
            for (MapView m : mListAdapter.getMapViews()) {
                m.onPause();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode == ConnectionResult.SUCCESS) {
            mRecyclerView.setAdapter(mListAdapter);
        } else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1).show();
        }

        if (mListAdapter != null) {
            for (MapView m : mListAdapter.getMapViews()) {
                m.onResume();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mListAdapter != null) {
            for (MapView m : mListAdapter.getMapViews()) {
                m.onDestroy();
            }
        }

        super.onDestroy();
    }

    /**
     * Show a full mapView when a mapView card is selected. This method is attached to each CardView
     * displayed within this activity's RecyclerView.
     *
     * @param view The view (CardView) that was clicked.
     */
    public abstract void showMapDetails(View view);
}