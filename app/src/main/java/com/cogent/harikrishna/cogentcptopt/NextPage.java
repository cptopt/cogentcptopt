package com.cogent.harikrishna.cogentcptopt;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.cogent.harikrishna.cogentcptopt.adapterClasses.Searchadapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class NextPage extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    GoogleApiClient apiClient;
    Location location;
    String address;
    //TextView loctxt;
    Button locbtn;
    double latitude, longitude;
    Toolbar jobtoolbar,locationtoolbar;

    android.widget.SearchView jobsearch,loctionsearch;
    // private SearchView searchView1 = null;
    private SearchView.OnQueryTextListener queryTextListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextpage);

        locbtn= (Button) findViewById(R.id.btnloc);
        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAddress().execute(latitude,longitude);

            }
        });
        jobtoolbar = (Toolbar)findViewById(R.id.toolbarjobs);


        jobsearch = (android.widget.SearchView) findViewById(R.id.jobsearch);

        loctionsearch = (android.widget.SearchView) findViewById(R.id.locsearch);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


        setSupportActionBar(jobtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Jobs");
        jobtoolbar.inflateMenu(R.menu.searchjob);
        //  toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        //  @Override
        //   public boolean onMenuItemClick(MenuItem item) {
        //        onOptionsItemSelected(item);
        //     return false;
        //     }
        // });

        locationtoolbar  = (Toolbar) findViewById(R.id.locationtoolbar);


        locationtoolbar.inflateMenu(R.menu.searchlocation);
        // toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        //   @Override
        //  public boolean onMenuItemClick(MenuItem item) {
        //      onOptionsItemSelected(item);
        //      return false;
        //    }
        //});





        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Jobs"));
        tabLayout.addTab(tabLayout.newTab().setText("Companies"));
        tabLayout.addTab(tabLayout.newTab().setText("Salaries"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        final Searchadapter pagerAdapter = new Searchadapter(getSupportFragmentManager(),tabLayout.getTabCount());

        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        apiClient = new GoogleApiClient.Builder(NextPage.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        apiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }


        location = LocationServices.FusedLocationApi.getLastLocation(apiClient);

        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }

        //latlong.setText(latitude + ", " + longitude);
        new GetAddress().execute(latitude, longitude);



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        apiClient.connect();
    }
    public class GetAddress extends AsyncTask<Double, Void, Void> {

        private static final String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";

        URL reqUrl;
        HttpURLConnection connection;
        String response;

        @Override
        protected Void doInBackground(Double... params) {
            try {
                reqUrl = new URL(url + params[0] + "," + params[1]);
                connection = (HttpURLConnection) reqUrl.openConnection();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                BufferedReader br = new BufferedReader(isr);

                String temp;
                StringBuilder sb = new StringBuilder();

                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }

                response = sb.toString();
                // Toast.makeText(Nextpage.this,"hitted",Toast.LENGTH_SHORT).show();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (response != null) {
                try {

                    JSONObject resObj = new JSONObject(response);
                    JSONArray reqArray = resObj.getJSONArray("results");
                    JSONObject element = reqArray.getJSONObject(6);

                    address = element.getString("formatted_address");
                    loctionsearch.setQueryHint(address);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //  @Override
        //  public boolean onCreateOptionsMenu(Menu menu) {
        //   MenuInflater inflater = getMenuInflater();
        //  inflater.inflate(R.menu.searchjob, menu);
        // SearchManager searchManager =
        //          (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //  searchView =
        //    (SearchView) menu.findItem(R.id.job).getActionView();
        //  searchView.setSearchableInfo(
        //       searchManager.getSearchableInfo(getComponentName()));
        //   searchView.setOnQueryTextListener(queryTextListener);
//inflater.inflate(R.menu.searchlocation,menu);
        //SearchManager searchManager1 =
        //    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //  searchView1 =
        //         (SearchView) menu.findItem(R.id.location).getActionView();
        // searchView1.setSearchableInfo(
        //  searchManager1.getSearchableInfo(getComponentName()));
        // searchView1.setOnQueryTextListener(queryTextListener);
        // return true;
        //  }


    }

}



