package com.example.applicationandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextRecherche;
    Button buttonRechercher;
    SeekBar kmSeekBar;
    TextView kmValue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = "MainActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextRecherche = findViewById(R.id.editTextRecherche);
        buttonRechercher = findViewById(R.id.buttonRechercher);
        kmSeekBar = findViewById(R.id.distanceSeekBar);
        kmValue = findViewById(R.id.kmValue);
        //tableau = findViewById(R.id.tableau);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewResto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
            //GoToMapActivity();
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

    private void GoToMapActivity(){
        Intent sendToMapActivity = new Intent(this, MapsActivity.class);
        startActivity(sendToMapActivity);
    }

    private void afficherTableau(){
        String value = editTextRecherche.getText().toString();
        int distance = kmSeekBar.getProgress();
        int radius = distance * 1000;
        final List<RestoTrouver> lesResto = new ArrayList<RestoTrouver>();
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+ value +"&key=AIzaSyB7XY8fiHuldU-vSJybZHlDS9sNjDEG7D0&type=restaurant&radius="+radius;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d(TAG, response.toString());
                            JSONArray array = response.getJSONArray("results");
                            for (int i=0;i<array.length();i++){
                                JSONObject place = array.getJSONObject(i);
                                String status = place.getString("business_status");
                                String nom = place.getString("name");
                                String address = place.getString("formatted_address");
                                String rating = place.getString("rating");
                                rating += "/5";
                                JSONObject geo = place.getJSONObject("geometry");
                                JSONObject loc = geo.getJSONObject("location");
                                String latitude = loc.getString("lat");
                                String longitude = loc.getString("lng");

                                RestoTrouver currentResto = new RestoTrouver(nom, address, rating, latitude, longitude);
                                lesResto.add(currentResto);

                            }
                            MyAdapter mAdapter = new MyAdapter(lesResto);
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
}
