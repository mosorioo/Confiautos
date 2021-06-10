package com.hola.confiautos;
// 2do intento

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.hola.confiautos.conexionSQLiteHelper.ConexionSQLiteHelper;
import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;
import com.hola.confiautos.utilidades.Utilidades;

import java.util.regex.Pattern;

import static com.hola.confiautos.utilidades.Utilidades.TABLA_USUARIO;

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

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetErrores();
                limpiarErrores();
                if (validarDatos()) {
                    registrarUsuario();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Ocurrió un error ",
                            Snackbar.LENGTH_LONG)
                            .setDuration(2000)
                            .show();
                }
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
        finish();
    }

    /*
   private boolean validarUsuario(String user){
       Pattern pattern = Pattern.compile("[a-zA]");
       //Pattern pattern = Pattern.compile("[a-zA-Z]");
       if (!user.matches(String.valueOf(pattern))) {
           eUser.setVisibility(View.VISIBLE);
           //Toast.makeText(this, "El nombre y apellido debe contener solo letras", Toast.LENGTH_LONG).show();
       }
       return user.matches(String.valueOf(pattern));
   }*/

    private boolean validarPass(String pass) {//Validación de la pass: igual a 8 numeros
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=\\S+$).{6}$");
        if (!pass.matches(String.valueOf(pattern))) {
            ePass.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "La contraseña debe tener 6 caracteres númericos.", Toast.LENGTH_LONG).show();
        }
        return pass.matches(String.valueOf(pattern));
    }

    private boolean validarConfPass(String confiPass) {//Validación del apellido: Solo letras
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=\\S+$).{6}$");
        if (!confiPass.matches(String.valueOf(pattern))) {
            eConfPass.setVisibility(View.VISIBLE);
           // Toast.makeText(this, "La contraseñas ingresadas deben coincidir", Toast.LENGTH_LONG).show();
        }
        return confiPass.matches(String.valueOf(pattern));
    }

    private boolean validarNombreApe(String nombre) {//Validación del nombre: Solo letras
        Pattern pattern = Pattern.compile("[a-zA-Z-ZñÑáéíóúÁÉÍÓÚ]+\\s[a-zA-Z-ZñÑáéíóúÁÉÍÓÚ\\s]+");
        //Pattern pattern = Pattern.compile("[a-zA-Z]");
        if (!nombre.matches(String.valueOf(pattern))) {
            eNombreApe.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "El nombre y apellido debe contener solo letras", Toast.LENGTH_LONG).show();
        }
        return nombre.matches(String.valueOf(pattern));
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (!pattern.matcher(email).matches()) {
            eEmail.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Correo inválido", Toast.LENGTH_LONG).show();
        }
        return pattern.matcher(email).matches();
    }

    private boolean validarDatos() {
        String usuario = us.getText().toString();
        String pass = pas.getText().toString();
        String confirmarPass = confpas.getText().toString();
        String nombreApe = nom.getText().toString();
       // String apellido = ape.getText().toString();
        String campoTel = tel.getText().toString();
        String campoEmail = email.getText().toString();

        validarTodos();

       // if (!usuario.equals("") && validarUsuario(usuario)) {
        if (!usuario.equals("") && usuario.length() == 8){
            if (!pass.equals("") && validarPass(pass)) {
                if (!confirmarPass.equals("") && validarConfPass(confirmarPass) && !confirmarPass.equals(pas)) {
                    if (!nombreApe.equals("") && validarNombreApe(nombreApe)) {
                            if (!campoTel.equals("") && campoTel.length() >= 10) {
                                if (!campoEmail.equals("") && validarEmail(campoEmail)) {
                                } else {
                                    return false; //email
                                }
                            } else {
                                //  error = "El telefono celular debe contener al menos 10 numeros";
                               /* errorDatos.setText("El telefono celular debe contener al menos 10 numeros");
                                errorDatos.setVisibility(View.VISIBLE); */
                                eTel.setVisibility(View.VISIBLE);
                                //Toast.makeText(this, "El telefono celular debe contener al menos 10 numeros", Toast.LENGTH_LONG).show();
                                return false; //telefono
                            }
                    } else {
                        return false; //nombreApellido
                    }
                } else {
                    return false; //confPass
                }
            } else {
                return false; //pass
            }
        } else {
            eUser.setVisibility(View.VISIBLE);
            return false; //usuario
        }
        return true; //return de la función ValidarDatos
    }

    /*
    public void validarCamposVacios() {
        if (!dao.isNull(us.getText().toString(), pas.getText().toString(), nom.getText().toString(), tel.getText().toString(), email.getText().toString()) && confpas.equals("")) { //ape.getText().toString(),
            Toast.makeText(this, "ERROR: Todos los campos se deben completar", Toast.LENGTH_LONG).show();
            limpiarTodo();
        }
    }*/

    public void validarTodos(){
        String usu = us.getText().toString();
        String pass = pas.getText().toString();
        String confPass = confpas.getText().toString();
        String nombre = nom.getText().toString();
        String tele = tel.getText().toString();
        String mail = email.getText().toString();

        //Valido que todos los campos no esten vacios
        if (usu.equals("")){
            eUser.setText("El Usuario no puede estar vacío");
            eUser.setVisibility(View.VISIBLE);
        }
        if (pass.equals("")){
            ePass.setText("La contraseña no puede estar vacía");
            ePass.setVisibility(View.VISIBLE);
        }

        if (confPass.equals("")){
            eConfPass.setText("Confirmar Pass no puede estar vacío");
            eConfPass.setVisibility(View.VISIBLE);
        }
        if (nombre.equals("")){
            eNombreApe.setText("El campo Nombre y Apellido no puede estar vacío");
            eNombreApe.setVisibility(View.VISIBLE);
        }
        if (tele.equals("")){
            eTel.setText("El campo celular no puede estar vacío");
            eTel.setVisibility(View.VISIBLE);
        }
        if (mail.equals("")){
            eEmail.setText("El campo e-mail no puede estar vacío");
            eEmail.setVisibility(View.VISIBLE);
        }
    }

    private void registrarUsuario() {
        Usuario usuario = new Usuario(us.getText().toString(), pas.getText().toString(), nom.getText().toString(), tel.getText().toString(), email.getText().toString()); //ape.getText().toString(),
        Integer existe = dao.validarUsuario(usuario, this);
        //Integer existe = 0;
        dao.buscarUsuarios(Registro.this);
        if (existe > 0) {
            Toast.makeText(this, "El usuario ingresado ya existe", Toast.LENGTH_LONG).show();
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

    //este no se esta usandos
    private void registrarUsuario1() {
        String tablaUsuario;

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cur= db.rawQuery("SELECT * FROM " + TABLA_USUARIO +"", null);
        int count = 0;
        while (cur.moveToNext()) {
            tablaUsuario= cur.getString(1);
            if (us.equals(tablaUsuario)) {
                count++;
            }
        }
        if (count > 0) {
            Toast.makeText(this, "El usuario ingresado ya existe", Toast.LENGTH_LONG).show();
        } else {
        Usuario usuario = new Usuario(us.getText().toString(), pas.getText().toString(), nom.getText().toString(), tel.getText().toString(), email.getText().toString()); //ape.getText().toString(),
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

    public void limpiarErrores(){
        eUser.setVisibility(View.INVISIBLE); //GONE
        ePass.setVisibility(View.INVISIBLE);
        eConfPass.setVisibility(View.INVISIBLE);
        eNombreApe.setVisibility(View.INVISIBLE);
        eTel.setVisibility(View.INVISIBLE);
        eEmail.setVisibility(View.INVISIBLE);

    }

    public void resetErrores() {
        eUser.setText("El usuario debe tener 8 dígitos.");
        ePass.setText("La contraseña debe tener 6 números.");
        eConfPass.setText("Las contraseñas ingresadas deben coincidir.");
        eNombreApe.setText("Debe ingresar su nombre y Apellido, solo letras.");
        eTel.setText("El telefono debe contener al menos 10 números.");
        eEmail.setText("El e-mail ingresado no cumple con el formato de e-mail.");
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