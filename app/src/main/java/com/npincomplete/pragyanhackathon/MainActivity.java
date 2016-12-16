package com.npincomplete.pragyanhackathon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private int[] tabIcons = {
            R.drawable.ic_heart,
            R.drawable.ic_hospital,
            R.drawable.ic_location
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!(isLocationEnabled(this) && isNetworkConnected() ) )
        {
            Toast.makeText(this, "Please Enable GPS and Data and Try again", Toast.LENGTH_SHORT).show();
            this.finish();
            //System.exit(0);
        }


        /*if (checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(type, period, 0, this);
*/

        SharedPreferences prefs = getSharedPreferences("db", MODE_PRIVATE);
        String isRegistered = prefs.getString("isRegistered", null);
        if(isRegistered == "l")
        {
            Intent intent = new Intent(this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


        tracker = new GPSTracker(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Emergency Request");
        adapter.addFragment(new TwoFragment(), "Request Ambulance by Hospital");
        adapter.addFragment(new ThreeFragment(), "Hospital Details");
        //adapter.addFragment(new ThreeFragment(), "THREE");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    String auth_token;
    String phoneNum;
    String uName;




    public void ambbtn(View view)
    {
        Intent intent  = new Intent(this, D_Ambulance.class);
        startActivity(intent);

    }

     /*   new AlertDialog.Builder(this)
                .setTitle("Place Immediately?")
                .setMessage("This will place a request! Use with caution! Please fill details if not in a hurry")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Place Immediately", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        progress = new ProgressDialog(getApplicationContext() );
                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(false);
                        progress.show();
                        new LongOperation().execute(
                                Double.toString(tracker.getLatitude()),
                                Double.toString(tracker.getLongitude()),
                                uName,
                                phoneNum,
                                "1",
                                "-1",
                                "1");
                    }})
                .setNegativeButton("Fill Details", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(getApplicationContext()  , A_ambActivity.class);
                        startActivity(intent);
                    }})
                .show();
    }
*/


    public void policebtn(View view)
    {
        new AlertDialog.Builder(this)
                .setTitle("Place Immediately?")
                .setMessage("This will place a request! Use with caution! Please fill details if not in a hurry")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Place Immediately", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        progress = new ProgressDialog(getApplicationContext() );
                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(false);
                        progress.show();
                        new LongOperation().execute(
                                Double.toString(tracker.getLatitude()),
                                Double.toString(tracker.getLongitude()),
                                uName,
                                phoneNum,
                                "2",
                                "-1",
                                "1");
                    }})
                .setNegativeButton("Fill Details", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(getApplicationContext()  , A_policeActivity.class);
                        startActivity(intent);
                    }})
                .show();
    }


    public void firebtn(View view)
    {

        final Context ctx = this;
        new AlertDialog.Builder(this)
                .setTitle("Place Immediately?")
                .setMessage("This will place a request! Use with caution! Please fill details if not in a hurry")
                .setIcon(android.R.drawable.btn_dialog)
                .setPositiveButton("Place Immediately", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        progress = new ProgressDialog( ctx );
                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(false);
                        progress.show();
                        new LongOperation().execute(
                                Double.toString(tracker.getLatitude()),
                                Double.toString(tracker.getLongitude()),
                                uName,
                                phoneNum,
                                "3",
                                "-1",
                                "1");
                    }})
                .setNegativeButton("Fill Details", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(getApplicationContext()  , A_fireActivity.class);
                        startActivity(intent);
                    }})
                .show();
    }

    public void emergencyfunc(View view)
    {

        SharedPreferences prefs = getSharedPreferences("db", MODE_PRIVATE);
        auth_token = prefs.getString("isRegistered", null);
        phoneNum = prefs.getString("phoneNum", null);
        uName = prefs.getString("uName", null);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
        new LongOperation().execute(
                Double.toString(tracker.getLatitude()),
                Double.toString(tracker.getLongitude()),
                uName,
                phoneNum
        );
    }

    JSONObject json;
    String outputresponse;
    ProgressDialog progress;

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            json = new JSONObject();
            try
            {
                json.put("Lat",params[0]);
                json.put("Long", params[1]);
                json.put("Name", params[2]);
                json.put("Phone", params[3]);
                json.put("Type", Integer.parseInt(params[4]) );
                json.put("Description", Integer.parseInt(params[5]) );
                json.put("Number", Integer.parseInt(params[6]) );
                json.put("Token", FirebaseInstanceId.getInstance().getToken());

            }catch (JSONException j)

            {
                Log.d("Second_Fragment", "Err");
            }

            try {
                URL url = new URL("http://4e16c88d.ngrok.io/user/emergency");
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
            //Toast.makeText(getActivity().getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
            aftercomplete();
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
        Toast.makeText(this, outputresponse, Toast.LENGTH_SHORT).show();
        progress.dismiss();
        //Intent intent = new Intent(this, hospital_activity.class);
        //intent.putExtra("outputresponse", outputresponse);
        //startActivity(intent);
    }

}