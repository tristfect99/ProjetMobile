package com.example.applicationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {
    EditText editTextRecherche;
    Button buttonRechercher;
    ListView tableau;

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

    public void OnClickrechercherResto(View v){
        afficherTableau();
    }

    private void GoToMapActivity(){
        Intent sendToMapActivity = new Intent(this, MapsActivity.class);
        startActivity(sendToMapActivity);
    }

    private void afficherTableau(){
        RestoTrouver salvatore = new RestoTrouver("salvatore","3");
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
        });
    }


}
