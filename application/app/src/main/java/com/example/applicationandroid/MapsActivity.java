package com.example.applicationandroid;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private double currentLat;
    private double currentLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        createAndShowDialog();
    }

    private void createAndShowDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cote_dialog);

        Button dialogBtnAddNote = dialog.findViewById(R.id.button_note);

        dialogBtnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextNote = dialog.findViewById(R.id.editText_note);
                RestoTrouver NoteToAdd = new RestoTrouver("","","","","");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
