package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

import java.util.regex.Pattern;

public class MiPerfil extends AppCompatActivity implements View.OnClickListener {

    EditText us, pas, confPass, nom, tel, email;
    Button btnGuard, btnVolv;
    private View v;
    DaoUsuario dao = new DaoUsuario();
    Usuario user;
    Integer id = 0;
    Intent x;

    TextView ePass, eConfPass, eNombreApe, eTel, eEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        us = findViewById(R.id.editUsuario);
        pas = findViewById(R.id.editPassword);
        confPass = findViewById(R.id.editConfPassword);
        nom = findViewById(R.id.editNombre);
        tel = findViewById(R.id.editNroTelefono);
        email = findViewById(R.id.editEmail);
        btnGuard = findViewById(R.id.btnEditGuardar);
        btnVolv = findViewById(R.id.btnEditVolver);

        //TextView de error
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
        tel.setText(user.getTelefono());
        email.setText(user.getEmail());


        btnGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarErrores();
                resetErrores();
                if (validarDatos()) {
                    actualizarUsuario();
                }
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

    public void limpiarErrores(){
        ePass.setVisibility(View.INVISIBLE);
        eConfPass.setVisibility(View.INVISIBLE);
        eNombreApe.setVisibility(View.INVISIBLE);
        eTel.setVisibility(View.INVISIBLE);
        eEmail.setVisibility(View.INVISIBLE);
    }

    public void resetErrores() {
        ePass.setText("La contraseña debe tener 6 números.");
        eConfPass.setText("Las contraseñas ingresadas deben coincidir.");
        eNombreApe.setText("Debe ingresar su nombre y Apellido, solo letras.");
        eTel.setText("El telefono debe contener al menos 10 números.");
        eEmail.setText("El e-mail ingresado no cumple con el formato de e-mail.");
    }

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
           // eConfPass.setText("Las contraseñas ingresadas deben coincidir");
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
            //Toast.makeText(this, "Correo inválido", Toast.LENGTH_LONG).show();
        }
        return pattern.matcher(email).matches();
    }

    private boolean validarDatos() {
        String pass = pas.getText().toString();
        String confirmarPass = confPass.getText().toString();
        String nombreApe = nom.getText().toString();
        String campoTel = tel.getText().toString();
        String campoEmail = email.getText().toString();

        validarTodos();

            if (!pass.equals("") && validarPass(pass)) {
                if (!confirmarPass.equals("") && validarConfPass(confirmarPass) && !confirmarPass.equals(pas)) {
                    if (!nombreApe.equals("") && validarNombreApe(nombreApe)) {
                        if (!campoTel.equals("") && campoTel.length() >= 10) {
                            if (!campoEmail.equals("") && validarEmail(campoEmail)) {
                            } else {
                                return false; //email
                            }
                        } else {
                            eTel.setVisibility(View.VISIBLE);
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
        return true; //return de la función ValidarDatos
    }

    public void validarTodos(){
        String pass = pas.getText().toString();
        String confiPass = confPass.getText().toString();
        String nombre = nom.getText().toString();
        String tele = tel.getText().toString();
        String mail = email.getText().toString();

        limpiarErrores();

        //Valido que todos los campos no esten vacios
        if (pass.equals("")){
            ePass.setText("La contraseña no puede estar vacía");
            ePass.setVisibility(View.VISIBLE);
        }

        if (confiPass.equals("")){
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

    private void actualizarUsuario() {
        user.setUsuario(us.getText().toString());
        user.setPassword(pas.getText().toString());
        user.setNombre(nom.getText().toString());
        user.setTelefono(tel.getText().toString());
        user.setEmail(email.getText().toString());

            dao.updateUsuario(user, this);
            Toast.makeText(this, "¡Los datos se actualizaron correctamente!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MiPerfil.this, Inicio.class);
            i.putExtra("Id", user.getId());
            startActivity(i);
            finish();
    }

    @Override
    public void onClick(View v) {

    }

}

