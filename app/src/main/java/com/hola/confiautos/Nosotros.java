package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

public class Nosotros extends AppCompatActivity {

    Button btnVolver;
    DaoUsuario dao = new DaoUsuario();
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);
        btnVolver = (Button) findViewById((R.id.btnNosotrosVolver));
        user=dao.getUserbyID(getIntent().getIntExtra("Id",0), Nosotros.this);
    }

    //Metodo para el boton Volver, este regresa a Inicio
    public void Volver(View view) {
        Intent i = new Intent(Nosotros.this, Inicio.class);
        i.putExtra("Id", user.getId());
        startActivity(i);
        finish();
    }
}