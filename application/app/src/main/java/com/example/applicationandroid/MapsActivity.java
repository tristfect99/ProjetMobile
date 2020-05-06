package com.example.applicationandroid;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
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

    private double currentLat;
    private double currentLng;

    /*MapsActivity(GoogleMap mMap, GoogleApiClient googleApiClient, double currentLat, double currentLng){
        this.mMap = mMap;
        this.googleApiClient = googleApiClient;
        this.currentLat = currentLat;
        this.currentLng = currentLng;
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
            currentLat = location.getLongitude();
            currentLng = location.getLatitude();
        }
        Toast.makeText(this, "lat: "+ currentLat + "lng: "+ currentLng, Toast.LENGTH_LONG).show();
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

    public void OnClickRetour(View v){
        Intent sendToMainActivity = new Intent(this, MainActivity.class);
        startActivity(sendToMainActivity);
    }

    public void OnClickCoter(View v){
        Toast.makeText(this, "Coter le resto", Toast.LENGTH_SHORT).show();
    }
}
