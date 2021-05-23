package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RecuperarPassword extends AppCompatActivity {

    EditText campoEmail, campoPass, campoConfPass, campoUsuario;
    Button btnRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        campoEmail = findViewById(R.id.editIngresarEmail);
        campoUsuario = findViewById(R.id.editRecuUsuario);
        campoPass = findViewById(R.id.editRecuPassword);
        campoConfPass = findViewById(R.id.editRecuConfiPass)
        btnRecuperar = findViewById(R.id.btnBuscarEmail);

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampoEmail();
            }
        });
    }

    public void validarCampoEmail (){
        String email = campoEmail.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            campoEmail.setError("Correo invalido");
            return;
        }
    }

}