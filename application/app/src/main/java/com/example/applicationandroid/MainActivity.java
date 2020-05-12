package com.example.applicationandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnRestoListener {
    EditText editTextRecherche;
    Button buttonRechercher;
    SeekBar kmSeekBar;
    TextView kmValue;
    TextView textViewTitre;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseFirestore database;

    private static final String TAG = "MainActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextRecherche = findViewById(R.id.editTextRecherche);
        buttonRechercher = findViewById(R.id.buttonRechercher);
        kmSeekBar = findViewById(R.id.distanceSeekBar);
        kmValue = findViewById(R.id.kmValue);
        textViewTitre = findViewById(R.id.textViewTitre);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewResto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseFirestore.getInstance();

        startAnimation();

        setListener();
    }

    private void setListener(){
        kmSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                kmValue.setText("" + progress);
                kmValue.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.Item_menu){
            GoToMainActivity();
            return true;
        }
        return false;
    }

    private void GoToMainActivity(){
        Intent sendToMainActivity = new Intent(this, MainActivity.class);
        startActivity(sendToMainActivity);
    }

    public void OnClickrechercherResto(View v){
        hideKeyboard(this);
        afficherTableau();
    }

    private void GoToMapActivity(String resto, double lat, double lng, String googleID){
        Intent sendToMapActivity = new Intent(this, MapsActivity.class);
        sendToMapActivity.putExtra("nomResto", resto);
        sendToMapActivity.putExtra("latitude", lat);
        sendToMapActivity.putExtra("longitude", lng);
        sendToMapActivity.putExtra("googleID", googleID);
        startActivity(sendToMapActivity);
    }

    private void afficherTableau(){
        String value = editTextRecherche.getText().toString();
        int distance = kmSeekBar.getProgress();
        int radius = distance * 1000;
        final List<RestoTrouver> lesResto = new ArrayList<RestoTrouver>();
        final MyAdapter.OnRestoListener onRestoListener = new MyAdapter.OnRestoListener() {
            @Override
            public void onRestoClick(int position) {
                String resto = lesResto.get(position).getNom();
                String latitudeS = lesResto.get(position).getLatitude();
                String longitudeS = lesResto.get(position).getLongitude();
                String googleID = lesResto.get(position).getGoogleID();
                double lat = Double.parseDouble(latitudeS);
                double lng = Double.parseDouble(longitudeS);
                GoToMapActivity(resto, lat, lng, googleID);
            }
        };
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+ value +"&key=AIzaSyB7XY8fiHuldU-vSJybZHlDS9sNjDEG7D0&type=restaurant&radius="+radius;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d(TAG, response.toString());
                            JSONArray array = response.getJSONArray("results");
                            for (int i=0;i<array.length();i++){
                                final JSONObject place = array.getJSONObject(i);
                                String status = place.getString("business_status");
                                final String nom = place.getString("name");
                                final String googleID = place.getString("id");
                                final String address = place.getString("formatted_address");
                                JSONObject geo = place.getJSONObject("geometry");
                                JSONObject loc = geo.getJSONObject("location");
                                final String latitude = loc.getString("lat");
                                final String longitude = loc.getString("lng");
                                getRating(googleID).addOnCompleteListener(new OnCompleteListener<Float>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Float> task) {
                                        String finalRating = "";
                                        if(task.getResult() == null){
                                            try {
                                                finalRating = place.getString("rating");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        else {
                                            finalRating = task.getResult().toString();
                                        }
                                        finalRating += "/5";
                                        RestoTrouver currentResto = new RestoTrouver(nom, address, finalRating, latitude, longitude, googleID);
                                        lesResto.add(currentResto);
                                    }
                                });


                            }
                            MyAdapter mAdapter = new MyAdapter(lesResto, onRestoListener);
                            recyclerView.setAdapter(mAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        SingletonRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private Task<Float> getRating(String googleId)
    {
        return database.collection("cotes").whereEqualTo("googleID", googleId).get()
                .continueWith(new Continuation<QuerySnapshot, Float>() {
                    @Override
                    public Float then(@NonNull Task<QuerySnapshot> task) throws Exception {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                List<CoteRestaurant> cotesDuResto = task.getResult().toObjects(CoteRestaurant.class);
                                Float sommeCote = 0f;
                                for (int i = 0; i < cotesDuResto.size(); i++) {
                                    sommeCote += cotesDuResto.get(i).cote;
                                }
                                Float avgCote = (sommeCote / cotesDuResto.size());
                                return avgCote;
                            } else {
                                return null;
                            }
                        }
                        return null;
                    }
                });


                /*.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() > 0){
                                List<CoteRestaurant> cotesDuResto = task.getResult().toObjects(CoteRestaurant.class);
                                Float sommeCote = 0f;
                                for (int i = 0; i < cotesDuResto.size(); i++) {
                                    sommeCote += cotesDuResto.get(i).cote;
                                }
                                Float avgCote = (sommeCote/cotesDuResto.size());
                                actualRating[0] = avgCote.toString();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("test1212", document.getId() + " => " + document.getData());
                                }
                            }
                        }
                    }
                })*/
    }

    private void getImage(String photoReference){
        String url = "https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyB7XY8fiHuldU-vSJybZHlDS9sNjDEG7D0&photoreference=" + photoReference + "&maxwidth=400&maxheight=400";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        SingletonRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onRestoClick(int position) {
        //GoToMapActivity();
    }

    private void startAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        textViewTitre.startAnimation(animation);
    }
}
