package com.example.applicationandroid;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;

    //private List<RestoTrouver> restoTrouverDataSet;

    private double longitudeFrom;
    private double latitudeFrom;

    /*MapsActivity(GoogleMap mMap, GoogleApiClient googleApiClient, List<RestoTrouver> restoTrouverDataSet, double longitudeFrom, double latitudeFrom){
        this.mMap = mMap;
        this.googleApiClient = googleApiClient;
        this.restoTrouverDataSet = restoTrouverDataSet;
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
        urlString.append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyB7XY8fiHuldU-vSJybZHlDS9sNjDEG7D0");
        return urlString.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        String leResto = intent.getStringExtra("nomResto");
        double latitude = intent.getDoubleExtra("latitude", 0);
        double longitude = intent.getDoubleExtra("longitude", 0);

        float zoomLevel = (float) 18.0;
        LatLng restoLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(restoLocation).title(leResto));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restoLocation, zoomLevel));
    }
}
