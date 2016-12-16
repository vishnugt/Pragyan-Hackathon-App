package com.npincomplete.pragyanhackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class D_Ambulance  extends MapListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected MapLocationAdapter createMapListAdapter() {
        ArrayList<MapLocation> mapLocations = new ArrayList<>(LIST_LOCATIONS.length);
        mapLocations.addAll(Arrays.asList(LIST_LOCATIONS));

        MapLocationAdapter adapter = new MapLocationAdapter();
        adapter.setMapLocations(mapLocations);

        return adapter;
    }

    @Override
    public void showMapDetails(View view) {
    }

    private static final MapLocation[] LIST_LOCATIONS = new MapLocation[]{
            new MapLocation("Beijing", 39.937795, 116.387224),
            new MapLocation("Bern", 46.948020, 7.448206),
            new MapLocation("Breda", 51.589256, 4.774396),
            new MapLocation("Brussels", 50.854509, 4.376678),
            new MapLocation("Cape Town", -33.920455, 18.466941),
            new MapLocation("Copenhagen", 55.679423, 12.577114),
            new MapLocation("Hannover", 52.372026, 9.735672),
            new MapLocation("Helsinki", 60.169653, 24.939480),
            new MapLocation("Hong Kong", 22.325862, 114.165532),
            new MapLocation("Istanbul", 41.034435, 28.977556),
            new MapLocation("Johannesburg", -26.202886, 28.039753),
            new MapLocation("Lisbon", 38.707163, -9.135517),
            new MapLocation("London", 51.500208, -0.126729),
            new MapLocation("Madrid", 40.420006, -3.709924),
            new MapLocation("Mexico City", 19.427050, -99.127571),
            new MapLocation("Moscow", 55.750449, 37.621136),
            new MapLocation("New York", 40.750580, -73.993584),
            new MapLocation("Oslo", 59.910761, 10.749092),
            new MapLocation("Paris", 48.859972, 2.340260),
            new MapLocation("Prague", 50.087811, 14.420460),
            new MapLocation("Rio de Janeiro", -22.90187, -43.232437),
            new MapLocation("Rome", 41.889998, 12.500162),
            new MapLocation("Sao Paolo", -22.863878, -43.244097),
            new MapLocation("Seoul", 37.560908, 126.987705),
            new MapLocation("Stockholm", 59.330650, 18.067360),
            new MapLocation("Sydney", -33.873651, 151.2068896),
            new MapLocation("Taipei", 25.022112, 121.478019),
            new MapLocation("Tokyo", 35.670267, 139.769955),
            new MapLocation("Tulsa Oklahoma", 36.149777, -95.993398),
            new MapLocation("Vaduz", 47.141076, 9.521482),
            new MapLocation("Vienna", 48.209206, 16.372778),
            new MapLocation("Warsaw", 52.235474, 21.004057),
            new MapLocation("Wellington", -41.286480, 174.776217),
            new MapLocation("Winnipeg", 49.875832, -97.150726)
    };
}