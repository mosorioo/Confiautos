package com.hola.confiautos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hola.confiautos.conexionSQLiteHelper.ConexionSQLiteHelper;
import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

import static com.hola.confiautos.R.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText usuario, password;
    Button btnLogin, btnRegistrarse, btnRecuPass;
    DaoUsuario dao = new DaoUsuario();
    int REQUESTCODE = 200; //Valor para saber si el usuario acepto el permiso
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, null, 1); //El 1 corresponde a la version de la BD

        usuario = (EditText) findViewById(id.editIngresarUsuario);//para enlazarlos
        password = (EditText) findViewById(id.txtLoginPassword);
        btnLogin = (Button) findViewById(id.btnLogin);
        btnRegistrarse = (Button) findViewById(id.btnRegistrarse);
        btnRecuPass = (Button) findViewById(id.btnOlvidePass);

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
                if (user.equals("") || pass.equals(""))
                    Toast.makeText(MainActivity.this, "ERROR: campos vacios", Toast.LENGTH_SHORT).show();
                else {
                    //Usuario usuario = DaoUsuario(user, pass, MainActivity.this);
                    Usuario usuario = dao.getUserbyUsuarioAndPass(user, pass, MainActivity.this);
                    if (usuario == null) {
                        Toast.makeText(MainActivity.this, "Usuario y/o Password incorrectos", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i2 = new Intent(MainActivity.this, Inicio.class);
                        i2.putExtra("Id", usuario.getId());
                     //   ((EditText) findViewById(id.regUsuario)).setText("");
                      //  ((EditText) findViewById(id.regPassword)).setText("");
                        startActivity(i2);
                        finish();

                    }
                }
            }
        });

        btnRecuPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecuperarPassword.class);
                startActivity(i);
                finish();
            }
        });

        verificarPermisos();
    }

    @Override
    public void onClick(View v) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)

    public void verificarPermisos(){
      int permisoSms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
      int permisoCall = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
      int permisoAlmacenamiento = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

      //Ver de hacerlo al reves
      if(permisoSms == PackageManager.PERMISSION_GRANTED && permisoCall == PackageManager.PERMISSION_GRANTED
              && permisoAlmacenamiento == PackageManager.PERMISSION_GRANTED){
          //Toast.makeText(this, "Permisos Concedidos", Toast.LENGTH_SHORT).show();
      } else {
            requestPermissions( new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTCODE);
      }
    }
}