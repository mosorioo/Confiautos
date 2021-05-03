package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

public class MiPerfil extends AppCompatActivity implements View.OnClickListener{

    EditText us, pas, nom, ape, tel, email;
    Button guard, volv;
    private View v;
    DaoUsuario dao = new DaoUsuario();
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        us=(EditText)findViewById(R.id.editUsuario);
        pas=(EditText)findViewById(R.id.editPassword);
        nom=(EditText)findViewById(R.id.editNombre);
        ape=(EditText)findViewById(R.id.editApellido);
        tel=(EditText)findViewById(R.id.editNroTelefono);
        email=(EditText)findViewById(R.id.editEmail);
        guard=(Button)findViewById(R.id.btnEditGuardar);
        volv=(Button)findViewById(R.id.btnEditVolver);
        guard.setOnClickListener(this);
        volv.setOnClickListener(this);
        //dao=new daoUsuario(this);
        user=dao.getUserbyID(getIntent().getIntExtra("Id",0), MiPerfil.this);

    }

  /*  @Override
    public void onClick(View v) {

    } */

    //Metodo para el boton Volver, este regresa a Inicio
  /*  public void Volver(View view) {
        Intent i = new Intent(MiPerfil.this, Inicio.class);
        startActivity(i);
        finish();
    } */

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditVolver:
                Intent i = new Intent(MiPerfil.this, Inicio.class);
                i.putExtra("Id", user.getId());
                startActivity(i);
                break;
        }
    }
}