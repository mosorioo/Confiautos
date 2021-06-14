package com.hola.confiautos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

public class Inicio extends AppCompatActivity implements View.OnClickListener {
    Button btnTurnoNuevo, btnMisTurnos, btnNosotros, btnMisAutos, btnMiPerfil, btnSalir;
    TextView nombre;
    //int id = 0; //para recuperar el id de usuario
    Usuario user;
    DaoUsuario dao = new DaoUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        nombre = (TextView) findViewById(R.id.txtMisAutos);
        btnNosotros = (Button) findViewById((R.id.btnNosotros));
        btnTurnoNuevo = (Button) findViewById(R.id.btnTurnoNuevo);
        btnMisTurnos = (Button) findViewById(R.id.btnMisTurnos);
        btnMisAutos = (Button) findViewById((R.id.btnMisAutos));
        btnMiPerfil = (Button) findViewById((R.id.btnMiPerfil));
        btnSalir = (Button) findViewById((R.id.btnSalir));

        user=dao.getUserbyID(getIntent().getIntExtra("Id",0), Inicio.this);
        nombre.setText("Bienvenido/a " +user.getNombre());

    }

    @Override
    public void onClick(View v) {
        }

    //Metodo para el boton Nosotros
    public void InfoNosotros(View view) {
        Intent i = new Intent(Inicio.this, Nosotros.class);
        i.putExtra("Id", user.getId());
        startActivity(i);
    }

    public void TurnoNuevo (View view) {
        Intent i1 = new Intent(Inicio.this, SolicitarTurno.class);
        i1.putExtra("Id", user.getId());
        startActivity(i1);
    }

    //Metodo para el boton MisAutos
    public void MisAutos (View v) {
        Intent i2 = new Intent(Inicio.this, MisAutos.class);
        i2.putExtra("Id", user.getId());
        startActivity(i2);
    }

    //Metodo para el boton MiPerfil
    public void MisDatos (View view) {
        Intent i3 = new Intent(Inicio.this, MiPerfil.class);
        i3.putExtra("Id", user.getId());
        startActivity(i3);
    }

    //Metodo para el boton Salir
    public void CerrarApp(View view) {
       // Otro intent para cerrar la app, funciona raro
      /* Intent i6 = new Intent(Intent.ACTION_MAIN);
        getIntent().addCategory(Intent.CATEGORY_HOME);
        getIntent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i6);
        finish();
        */
        //Con este cierro la app completamente.
        /*finish();
        System.exit(0);*/

        Intent i6 = new Intent(Inicio.this, MainActivity.class); //Me lleva al login
        getIntent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(i6);
        finish();
    }

}