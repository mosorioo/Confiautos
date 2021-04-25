package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Nosotros extends AppCompatActivity {

    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);
        btnVolver = (Button) findViewById((R.id.btnVolver));
    }

    public void Volver(View view) {
        Intent i = new Intent(Nosotros.this, Inicio.class);
        startActivity(i);
        finish();
    }
}