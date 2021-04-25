package com.hola.confiautos;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Inicio extends AppCompatActivity implements View.OnClickListener {
    Button btnTurnoNuevo, btnMisTurnos, btnNosotros, btnBeneficios, btnMisAutos, btnMiPerfil, btnSalir;
    TextView nombre;
    int id = 0; //para recuperar el id de usuario
    Usuario u;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        nombre = (TextView) findViewById(R.id.txtNombreUsuario);
        btnNosotros = (Button) findViewById((R.id.btnNosotros));
        btnTurnoNuevo = (Button) findViewById(R.id.btnTurnoNuevo);
        btnMisTurnos = (Button) findViewById(R.id.btnMisTurnos);
        btnMisAutos = (Button) findViewById((R.id.btnMisAutos));
        btnMiPerfil = (Button) findViewById((R.id.btnMiPerfil));
        btnBeneficios = (Button) findViewById((R.id.btnBeneficios));
        btnSalir = (Button) findViewById((R.id.btnSalir));

    /*    Bundle b=getIntent().getExtras();
        id=b.getInt("id");
        dao=new daoUsuario(this);
        u=dao.getUsuarioById(id);
        nombre.setText(u.getNombre()+" "+u.getApellidos());*/
    }

    @Override
    public void onClick(View v) {
        }

    //Metodo para el boton Nosotros
    public void InfoNosotros(View view) {
        Intent i = new Intent(Inicio.this, Nosotros.class);
        startActivity(i);
        finish();
    }

    //Metodo para el boton MiPerfil
    public void MisDatos (View view) {
        Intent i1 = new Intent(Inicio.this, MiPerfil.class);
        startActivity(i1);
        finish();
    }

    //Metodo para el boton Salir
    public void CerrarApp(View view) {
       /* Otro intent para cerrar la app, funciona raro
       Intent i6 = new Intent(Intent.ACTION_MAIN);
        getIntent().addCategory(Intent.CATEGORY_HOME);
        getIntent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i6);
        finish();

        */
        Intent i6 = new Intent(Inicio.this, MainActivity.class); //Me lleva al login
        getIntent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(i6);
    }

}