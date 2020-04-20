package com.example.applicationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
        Toast.makeText(this, "Afficher les resto qui offrent se repas", Toast.LENGTH_SHORT).show();
    }

    private void GoToMapActivity(){
        Intent sendToMapActivity = new Intent(this, MapsActivity.class);
        startActivity(sendToMapActivity);
    }
}
