package com.example.applicationandroid;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;

    private List<RestoTrouver> restoTrouverDataSet;

    //Intent intent = getIntent();
    //int position = intent.getIntExtra("listPosition", 0);

    int position = 1;

    private double longitudeFrom;
    private double latitudeFrom;

    /*MapsActivity(GoogleMap mMap, GoogleApiClient googleApiClient, List<RestoTrouver> restoTrouverDataSet, int position, double longitudeFrom, double latitudeFrom){
        this.mMap = mMap;
        this.googleApiClient = googleApiClient;
        this.restoTrouverDataSet = restoTrouverDataSet;
        this.position = position;
        this.longitudeFrom = longitudeFrom;
        this.latitudeFrom = latitudeFrom;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getCurrentLocation() {
        mMap.clear();
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            longitudeFrom = location.getLongitude();
            latitudeFrom = location.getLatitude();
        }
    }

    public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyB7XY8fiHuldU-vSJybZHlDS9sNjDEG7D0");
        return urlString.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        RestoTrouver restoTrouver = restoTrouverDataSet.get(position);

        String latitudeS = restoTrouver.getLatitude();
        String longitudeS = restoTrouver.getLongitude();
        String leResto = restoTrouver.getNom();

        double latitude = Double.parseDouble(latitudeS);
        double longitude = Double.parseDouble(longitudeS);

        //LatLng resto = new LatLng(46.12, -70.67);
        LatLng resto = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(resto).title(leResto));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(resto));
    }
}
