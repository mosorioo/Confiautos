package com.hola.confiautos;
// 2do intento

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    EditText us, pas, confpas, nom, ape, tel, email;
    Button btnReg, btnVolv;
    DaoUsuario dao = new DaoUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        us = (EditText) findViewById(R.id.regUsuario);
        pas = (EditText) findViewById(R.id.regPassword);
        confpas = (EditText) findViewById(R.id.regConfPassword);
        nom = (EditText) findViewById(R.id.regNombre);
        ape = (EditText) findViewById(R.id.regApellido);
        tel = (EditText) findViewById(R.id.regNroTelefono);
        email = (EditText) findViewById(R.id.regEmail);
        btnReg = findViewById(R.id.btnRegRegistrar);
        btnVolv = findViewById(R.id.btnRegVolver);

        // reg = (Button) findViewById(R.id.btnRegRegistrar);
        // volv = (Button) findViewById(R.id.btnRegVolver);
        // reg.setOnClickListener((View.OnClickListener) this);
        // volv.setOnClickListener((View.OnClickListener) this);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrarUsuario();
            }
        });

        btnVolv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                volver();
            }
        });


    }

    private void volver() {
    //    onBackPressed();
        Intent i1 = new Intent(Registro.this, MainActivity.class);
        startActivity(i1);
       // finish();
    }

    private void registrarUsuario() {
        Usuario usuario = new Usuario(us.getText().toString(), pas.getText().toString(), nom.getText().toString(), ape.getText().toString(), tel.getText().toString(), email.getText().toString());
        Integer existe = dao.validarUsuario(usuario, this);
        //Integer existe = 0;
        dao.buscarUsuarios(Registro.this);
        if (existe > 0) {
            Toast.makeText(this, "El usuario ingresado ya existe", Toast.LENGTH_LONG).show();
            ((EditText) findViewById(R.id.regUsuario)).setText("");
            ((EditText) findViewById(R.id.regPassword)).setText("");
            ((EditText) findViewById(R.id.regConfPassword)).setText("");
            ((EditText) findViewById(R.id.regNombre)).setText("");
            ((EditText) findViewById(R.id.regApellido)).setText("");
            ((EditText) findViewById(R.id.regNroTelefono)).setText("");
            ((EditText) findViewById(R.id.regEmail)).setText("");
        } else if (!dao.isNull(us.getText().toString(), pas.getText().toString(), nom.getText().toString(), ape.getText().toString(), tel.getText().toString(), email.getText().toString())) {
            Toast.makeText(this, "ERROR: Campos Vacios", Toast.LENGTH_LONG).show();
            ((EditText) findViewById(R.id.regUsuario)).setText("");
            ((EditText) findViewById(R.id.regPassword)).setText("");
            ((EditText) findViewById(R.id.regConfPassword)).setText("");
            ((EditText) findViewById(R.id.regNombre)).setText("");
            ((EditText) findViewById(R.id.regApellido)).setText("");
            ((EditText) findViewById(R.id.regNroTelefono)).setText("");
            ((EditText) findViewById(R.id.regEmail)).setText("");
        }
        else {
            dao.createUsuario(usuario, this);
            Toast.makeText(this, "¡Registro Exitoso!", Toast.LENGTH_LONG).show();
            ((EditText) findViewById(R.id.regUsuario)).setText("");
            ((EditText) findViewById(R.id.regPassword)).setText("");
            ((EditText) findViewById(R.id.regConfPassword)).setText("");
            ((EditText) findViewById(R.id.regNombre)).setText("");
            ((EditText) findViewById(R.id.regApellido)).setText("");
            ((EditText) findViewById(R.id.regNroTelefono)).setText("");
            ((EditText) findViewById(R.id.regEmail)).setText("");
            Intent i = new Intent(Registro.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

    }
}

//fin 2do intento

/*Codigo anterior
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.daoUsuario;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    EditText us, pas, nom, ape, tel, email; // tdocu,
    Button reg, volv;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        us=(EditText)findViewById(R.id.regUsuario);
        pas=(EditText)findViewById(R.id.regPassword);
      //  confpas=(EditText)findViewById(R.id.regConfiPassword);
        nom=(EditText)findViewById(R.id.regNombre);
        ape=(EditText)findViewById(R.id.regApellido);
        tel=(EditText)findViewById(R.id.regNroTelefono);
        email=(EditText)findViewById(R.id.regEmail);
        reg=(Button)findViewById(R.id.btnRegRegistrar);
        volv=(Button)findViewById(R.id.btnRegVolver);
        reg.setOnClickListener(this);
        volv.setOnClickListener(this);
        dao=new daoUsuario(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegRegistrar:
                Usuario u=new Usuario();

                u.setUsuario(us.getText().toString());
                u.setPassword(pas.getText().toString());
               // u.setConfiPassword(confpas.getText().toString());
                u.setNombre(nom.getText().toString());
                u.setApellidos(ape.getText().toString());
                u.setNroTelefono(tel.getText().toString());
                u.setEmail(email.getText().toString()); //hasta aqui recupero los valores, ahora voy a validarlos
                if (!u.isNull()){
                    Toast.makeText(this,"ERROR: Campos Vacios", Toast.LENGTH_LONG).show();
                }else if(dao.insertUsuario(u)){
                    Toast.makeText(this, "¡Registro Exitoso!", Toast.LENGTH_LONG).show();
                    Intent i2=new Intent(Registro.this,MainActivity.class); //Me lleva al Main Activity
                    startActivity(i2);
                    finish();
                }else{
                    Toast.makeText(this, "USUARIO YA REGISTRADO", Toast.LENGTH_LONG).show();

                }

                break;
            case R.id.btnRegVolver:
                Intent i=new Intent(Registro.this,MainActivity.class);
                finish();
                break;
        }
    }
}*/