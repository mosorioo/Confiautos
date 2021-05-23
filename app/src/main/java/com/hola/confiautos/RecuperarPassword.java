package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;
import com.hola.confiautos.utilidades.Utilidades;

public class RecuperarPassword extends AppCompatActivity {

    EditText campoEmail, campoPass, campoConfPass, campoUsuario;
    Button btnBuscar, btnGuardar, btnCancelar;
    DaoUsuario dao = new DaoUsuario();
    Usuario user;

    ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        campoEmail = findViewById(R.id.editRecuEmail);
        campoUsuario = findViewById(R.id.editRecuUsuario);
        campoPass = findViewById(R.id.editRecuPassword);
        campoConfPass = findViewById(R.id.editRecuConfiPass);
        btnBuscar = findViewById(R.id.btnBuscarEmail);
        btnGuardar = findViewById(R.id.btnRecuGuardar);
        btnCancelar = findViewById(R.id.btnRecuCancelar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampoEmail();
                consultarUsuario();
                // buscarUsuario(); //No funciona bien
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                if (campoPass == null || campoConfPass == null) {
                    Toast.makeText(this, "ERROR: Debe completar la Password y confirmarla", Toast.LENGTH_LONG).show();
                }
                else */
                    actualizarPassword();
            }

        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecuperarPassword.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    //Es para validar si el email ingresado cumple con el formato de email
    public void validarCampoEmail () {
        String email = campoEmail.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            campoEmail.setError("Correo inválido");
            return;
        }
    }

    /* Este tiene algo raro
    private void buscarUsuario() {
      //  ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros = {campoEmail.getText().toString()};

        try {
            //Select Usuario from tabla_usuarios where email = algo
            Cursor cursor = db.rawQuery("SELECT " + Utilidades.USUARIO + " FROM " + Utilidades.TABLA_USUARIO + " WHERE " + Utilidades.EMAIL + "= ? ", parametros);

            cursor.moveToFirst();
            campoUsuario.setText(cursor.getString(0));

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "El Email no existe", Toast.LENGTH_LONG).show();
            campoEmail.setText("");
        }
    } */

    private void consultarUsuario(){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {campoEmail.getText().toString()};
        String[] cUsuario = {Utilidades.USUARIO};

        try {
            Cursor c= db.query(Utilidades.TABLA_USUARIO,cUsuario,Utilidades.EMAIL+"=?",parametros,null,null,null);
            c.moveToFirst();
            campoUsuario.setText(c.getString(0));
            c.close();
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "El Email no existe", Toast.LENGTH_LONG).show();
            campoEmail.setText("");
        }
    }

    public void actualizarPassword() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametro = {campoEmail.getText().toString()};

        String pass = campoPass.getText().toString();
        String confPass = campoConfPass.getText().toString();

        if (pass.equals("") || confPass.equals("")) {
            Toast.makeText(this, "ERROR: Debe completar la Password y confirmarla", Toast.LENGTH_LONG).show();
        }else if (!validarPass(confPass, pass)) { //no funciona bien
            Toast.makeText(this, "ERROR: Las password no coinciden", Toast.LENGTH_LONG).show();
        }else{
            ContentValues values= new ContentValues();
            values.put(Utilidades.PASSWORD,campoPass.getText().toString());
            db.update(Utilidades.TABLA_USUARIO, values, Utilidades.EMAIL+"=?", parametro);
            Toast.makeText(getApplicationContext(),"¡La password se actualizó correctamente!",Toast.LENGTH_LONG).show();
            limpiar();
            db.close();
            Intent i = new Intent(RecuperarPassword.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    private void limpiar() {
        campoEmail.setText("");
        campoUsuario.setText("");
        campoPass.setText("");
        campoConfPass.setText("");
    }

    private boolean validarPass(String pass, String confpas){
        if (confpas != pass ){
            return false;
            // Toast.makeText(this, "ERROR: La password no coincide", Toast.LENGTH_LONG).show();
        } else {
            return true;
        }
    }


}