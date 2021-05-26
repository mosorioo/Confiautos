package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

public class MisAutos extends AppCompatActivity implements View.OnClickListener {

    Button btnVolv;
    private View v;
    DaoUsuario dao = new DaoUsuario();
    Usuario user;
    Integer id = 0;
    Intent x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_autos);

        btnVolv = (Button) findViewById(R.id.btnMisAutosVolver);
        //dao=new daoUsuario(this);
        user = dao.getUserbyID(getIntent().getIntExtra("Id", 0), MisAutos.this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        btnVolv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MisAutos.this, Inicio.class);
                i.putExtra("Id", user.getId());
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}