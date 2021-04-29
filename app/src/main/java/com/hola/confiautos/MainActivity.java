package com.hola.confiautos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

import static com.hola.confiautos.R.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText usuario, password;
    Button btnLogin, btnRegistrarse;
    DaoUsuario dao = new DaoUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, null, 1); //El 1 corresponde a la version de la BD
        usuario = (EditText) findViewById(id.txtLoginUser);//para enlazarlos
        password = (EditText) findViewById(id.txtLoginPassword);
        btnLogin = (Button) findViewById(id.btnLogin);
        btnRegistrarse = (Button) findViewById(id.btnRegistrarse);
        //btnLogin.setOnClickListener(this); //asigno los eventos
        //btnRegistrarse.setOnClickListener(this);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registro.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usuario.getText().toString();
                String pass = password.getText().toString();
                if (user.equals("") && pass.equals(""))
                    Toast.makeText(MainActivity.this, "ERROR: campos vacios", Toast.LENGTH_SHORT).show();
                else {
                    //Usuario usuario = DaoUsuario(user, pass, MainActivity.this);
                    Usuario usuario = dao.getUserbyUsuarioAndPass(user, pass, MainActivity.this);
                    if (usuario == null) {
                        Toast.makeText(MainActivity.this, "Usuario y/o Password incorrectos", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i2 = new Intent(MainActivity.this, Inicio.class);
                        i2.putExtra("Id", usuario.getId());
                        ((EditText) findViewById(id.regUsuario)).setText("");
                        ((EditText) findViewById(id.regPassword)).setText("");
                        startActivity(i2);
            /*@Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, Inicio.class);
                startActivity(i1);
            }
        });*/

                    }
                }
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}
/*
//Antiguo Main
import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import androidx.appcompat.app.AppCompatActivity;

import com.hola.confiautos.services.daoUsuario;

import static com.hola.confiautos.R.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText usuario, password;
    Button btnLogin, btnRegistrarse;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        usuario=(EditText)findViewById(id.txtLoginUser);//para enlazarlos
        password=(EditText)findViewById(id.txtLoginPassword);
        btnLogin=(Button)findViewById(id.btnLogin);
        btnRegistrarse=(Button)findViewById(id.btnRegistrarse);
        btnLogin.setOnClickListener(this); //asigno los eventos
        btnRegistrarse.setOnClickListener(this);
        dao=new daoUsuario(this);
      }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case id.btnLogin:
                String u=usuario.getText().toString();
                String p=password.getText().toString();
                if(u.equals("")&&p.equals("")){
                    Toast.makeText(this, "ERROR: Campos vacios",Toast.LENGTH_LONG).show();
                }else if(dao.login(u,p)==1){ //si dao es igual a 1 existe, (verifica si existe en la BD)
                    //   Usuario ux=dao.getUsuario(u,p); //guardo el usuario
                    Toast.makeText(this, "Datos correctos", Toast.LENGTH_LONG).show();
                    Intent i2=new Intent(MainActivity.this,Inicio.class); //Me lleva al menu de inicio
                    // i2.putExtra("Id", ux.getId()); // con este intent voy a pasar el id a la siguiente pantalla para que sepa cual es el uduario logueado
                    startActivity(i2);
                    finish();
                }else {
                    Toast.makeText(this, "Usuario y/o Password incorrectos", Toast.LENGTH_LONG).show();
                }
                break;
            case id.btnRegistrarse:
                Intent i=new Intent(MainActivity.this,Registro.class);
                startActivity(i);
                break;
        }

    }
}
*/