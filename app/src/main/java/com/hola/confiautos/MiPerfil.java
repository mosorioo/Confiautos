package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

public class MiPerfil extends AppCompatActivity implements View.OnClickListener {

    EditText us, pas, nom, tel, email; //ape,
    Button btnGuard, btnVolv;
    TextView error;
    private View v;
    DaoUsuario dao = new DaoUsuario();
    Usuario user;
    Integer id = 0;
    Intent x;

    TextView eUser, ePass, eConfPass, eNombreApe, eTel, eEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        us = findViewById(R.id.editUsuario);
        pas = findViewById(R.id.editPassword);
        nom = findViewById(R.id.editNombre);
        //ape = findViewById(R.id.editApellido);
        tel = findViewById(R.id.editNroTelefono);
        email = findViewById(R.id.editEmail);
        btnGuard = findViewById(R.id.btnEditGuardar);
        btnVolv = findViewById(R.id.btnEditVolver);

        eUser = findViewById(R.id.errorPerfilUsuario);
        ePass = findViewById(R.id.errorPerfilPassword);
        eConfPass = findViewById(R.id.errorPerfilConfPass);
        eNombreApe = findViewById(R.id.errorPerfilNombreApe);
        eTel = findViewById(R.id.errorPerfilTelefono);
        eEmail = findViewById(R.id.errorPerfilEmail);

        //dao=new daoUsuario(this);
        user = dao.getUserbyID(getIntent().getIntExtra("Id", 0), MiPerfil.this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        //dao= new DaoUsuario(this);
        us.setText(user.getUsuario());
        pas.setText(user.getPassword());
        nom.setText(user.getNombre());
      //  ape.setText(user.getApellido());
        tel.setText(user.getTelefono());
        email.setText(user.getEmail());


        btnGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarUsuario();
            }

        });

        btnVolv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MiPerfil.this, Inicio.class);
                i.putExtra("Id", user.getId());
                startActivity(i);
                finish();
            }
        });

    }

    private void actualizarUsuario() {
        user.setUsuario(us.getText().toString());
        user.setPassword(pas.getText().toString());
        user.setNombre(nom.getText().toString());
      //  user.setApellido(ape.getText().toString());
        user.setTelefono(tel.getText().toString());
        user.setEmail(email.getText().toString());

        // if (!dao.isNullSinUser(pas.getText().toString(), nom.getText().toString(), ape.getText().toString(), tel.getText().toString(), email.getText().toString())) {
        if (!user.isNullSinUser()) {
            Toast.makeText(this, "ERROR: Todos los campos deben ser completados", Toast.LENGTH_LONG).show();
        } else {
            dao.updateUsuario(user, this);
            Toast.makeText(this, "¡Los datos se actualizaron correctamente!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MiPerfil.this, Inicio.class);
            i.putExtra("Id", user.getId());
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

    }

  /*  //Metodo para cargar el formulario con los datos del usuario logueado
    public void Buscar (View view){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String id = ;

    }*/

 /*   //Metodo para el boton Volver, este regresa a Inicio
    public void Volver(View view) {
        Intent i = new Intent(MiPerfil.this, Inicio.class);
        i.putExtra("Id", user.getId());
        startActivity(i);
        finish();
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditVolver:
                Intent i = new Intent(MiPerfil.this, Inicio.class);
                i.putExtra("Id", user.getId());
                startActivity(i);
                finish();
                break;
        }
*/

}

