package com.example.applicationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText editTextRecherche;
    Button buttonRechercher;
    ListView tableau;
    private static final String TAG = "MainActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextRecherche = findViewById(R.id.editTextRecherche);
        buttonRechercher = findViewById(R.id.buttonRechercher);
        tableau = findViewById(R.id.tableau);

        setListener();
    }

    private void setListener(){

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
        afficherTableau();
    }

    private void GoToMapActivity(){
        Intent sendToMapActivity = new Intent(this, MapsActivity.class);
        startActivity(sendToMapActivity);
    }

    private void afficherTableau(){
        /*RestoTrouver salvatore = new RestoTrouver("salvatore","3");
        RestoTrouver bostonpizza = new RestoTrouver("Boston pizza","4");

        RestoTrouver[] resto = new RestoTrouver[]{salvatore,bostonpizza};

        ArrayAdapter<RestoTrouver> arrayAdapter
                = new ArrayAdapter<RestoTrouver>(this, android.R.layout.simple_list_item_1 , resto);

        tableau.setAdapter(arrayAdapter);

        tableau.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                GoToMapActivity();
            }
        });*/

        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?key=AIzaSyB7XY8fiHuldU-vSJybZHlDS9sNjDEG7D0&inputtype=textquery&input=pizza&type=restaurant";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

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


}
