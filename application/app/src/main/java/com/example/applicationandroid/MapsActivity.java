package com.example.applicationandroid;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        database = FirebaseFirestore.getInstance();
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
        dialog.setContentView(R.layout.cote_card);

        Button buttonSave = dialog.findViewById(R.id.button_Save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = dialog.findViewById(R.id.ratingBar_Cote);
                Float cote = ratingBar.getRating();
                Intent intent = getIntent();
                String googleID = intent.getStringExtra("googleID");
                CoteRestaurant coteRestaurant = new CoteRestaurant(googleID, cote);
                database.collection("cotes").add(coteRestaurant).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                });
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
