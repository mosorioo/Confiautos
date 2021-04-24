package com.hola.confiautos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Inicio extends AppCompatActivity implements View.OnClickListener{
    Button btnTurnoNuevo, btnMisTurnos, btnNosotros, btnBeneficios, btnMisAutos, btnMiPerfil, btnSalir;
    TextView nombre;
    int id=0; //para recuperar el id de usuario
    Usuario u;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        nombre=(TextView)findViewById(R.id.txtNombreUsuario);
        btnNosotros=(Button)findViewById((R.id.btnNosotros));
        btnTurnoNuevo=(Button) findViewById(R.id.btnTurnoNuevo);
        btnMisTurnos=(Button) findViewById(R.id.btnMisTurnos);
        btnMisAutos=(Button)findViewById((R.id.btnMisAutos));
        btnMiPerfil=(Button)findViewById((R.id.btnMiPerfil));
        btnBeneficios=(Button)findViewById((R.id.btnBeneficios));
        btnSalir=(Button)findViewById((R.id.btnSalir));
        //Agregar el resto de botones

    /*    Bundle b=getIntent().getExtras();
        id=b.getInt("id");
        dao=new daoUsuario(this);
        u=dao.getUsuarioById(id);
        nombre.setText(u.getNombre()+" "+u.getApellidos());*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNosotros:
                Intent i=new Intent(Inicio.this,Nosotros.class);
                startActivity(i);
                break;

           /* case R.id.btnTurnoNuevo:
                Intent a=new Intent(Inicio.this,TurnoNuevo.class); //Me lleva al menu de inicio
                startActivity(a);
                break;*/
         /*  case R.id.btnMisTurnos:
                Intent a=new Intent(Inicio.this,MisTurnos.class); //Me lleva al menu de inicio
                startActivity(a);
                break;*/

            case R.id.btnMiPerfil:
                Intent i1=new Intent(Inicio.this,MiPerfil.class);
                startActivity(i1);
                break;

            case R.id.btnSalir:
                Intent i2=new Intent(Inicio.this,MainActivity.class); //cierra la sesión y me lleva al Main
                startActivity(i2);
                finish();
                break;
        }
    }
}