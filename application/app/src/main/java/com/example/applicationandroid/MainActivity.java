package com.example.applicationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

        RestoTrouver mcdo = new RestoTrouver("mcdo","3");
        RestoTrouver bostonpizza = new RestoTrouver("Boston pizza","4");

        RestoTrouver[] users = new RestoTrouver[]{mcdo,bostonpizza};

        ArrayAdapter<RestoTrouver> arrayAdapter
                = new ArrayAdapter<RestoTrouver>(this, android.R.layout.simple_list_item_1 , users);

        tableau.setAdapter(arrayAdapter);

        setListener();
    }

    private void setListener(){

    }

    public void OnClickrechercherResto(View v){
        Toast.makeText(this, "Afficher les resto qui offrent se repas", Toast.LENGTH_SHORT).show();
    }

    private void GoToMapActivity(){
        Intent sendToMapActivity = new Intent(this, MapsActivity.class);
        startActivity(sendToMapActivity);
    }


}
