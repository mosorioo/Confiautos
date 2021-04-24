package com.hola.confiautos;

import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import androidx.appcompat.app.AppCompatActivity;

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