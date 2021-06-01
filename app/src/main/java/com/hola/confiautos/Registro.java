package com.hola.confiautos;
// 2do intento

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

import java.util.regex.Pattern;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    EditText us, pas, confpas, nom, tel, email; //ape,
    Button btnReg, btnVolv;
    DaoUsuario dao = new DaoUsuario();
    TextView eUser, ePass, eConfPass, eNombreApe, eTel, eEmail;
    String error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        us = findViewById(R.id.regUsuario);
        pas = findViewById(R.id.regPassword);
        confpas = findViewById(R.id.regConfPassword);
        nom = findViewById(R.id.regNombre);
      //  ape = findViewById(R.id.regApellido);
        tel = findViewById(R.id.regNroTelefono);
        email = findViewById(R.id.regEmail);
        btnReg = findViewById(R.id.btnRegRegistrar);
        btnVolv = findViewById(R.id.btnRegVolver);

        eUser = findViewById(R.id.errorUser);
        ePass = findViewById(R.id.errorPass);
        eConfPass = findViewById(R.id.errorConfPass);
        eNombreApe = findViewById(R.id.errorNombreApe);
        eTel = findViewById(R.id.errorTelefono);
        eEmail = findViewById(R.id.errorEmail);


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

   /* public void validarCampoEmail () {
        String email = email.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email.setError("Correo inválido");
            return;
        }
    }*/

    private boolean validarCampoEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (!pattern.matcher(email).matches()){

            error= "Email invalido";

        }
        return pattern.matcher(email).matches();
    }

    private boolean validarPass (String pass){//controla que la estructura de mail tenga los campos necesarios
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=\\S+$).{8,}$");
        if (!pass.matches(String.valueOf(pattern))){
            error= "La contraseña debe tener al menos 8 caracteres númericos.";
        }
        return pass.matches(String.valueOf(pattern));
    }

    private void registrarUsuario() {
        Usuario usuario = new Usuario(us.getText().toString(), pas.getText().toString(), nom.getText().toString(), tel.getText().toString(), email.getText().toString()); //ape.getText().toString(),
        Integer existe = dao.validarUsuario(usuario, this);
        //Integer existe = 0;
        dao.buscarUsuarios(Registro.this);
        if (existe > 0) {
            Toast.makeText(this, "El usuario ingresado ya existe", Toast.LENGTH_LONG).show();
            limpiarTodo();
        } else if (!dao.isNull(us.getText().toString(), pas.getText().toString(), nom.getText().toString(), tel.getText().toString(), email.getText().toString())) { //ape.getText().toString(),
            Toast.makeText(this, "ERROR: Campos Vacios", Toast.LENGTH_LONG).show();
            limpiarTodo();
        }
        else {
            dao.createUsuario(usuario, this);
            Toast.makeText(this, "¡Registro Exitoso!", Toast.LENGTH_LONG).show();
            limpiarTodo();
            Intent i = new Intent(Registro.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void limpiarTodo() {
        us.setText("");
        pas.setText("");
        confpas.setText("");
        nom.setText("");
       // ape.setText("");
        tel.setText("");
        email.setText("");
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