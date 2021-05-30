package com.hola.confiautos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hola.confiautos.entidades.Auto;
import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoAuto;
import com.hola.confiautos.services.DaoUsuario;
import com.hola.confiautos.utilidades.Utilidades;

import java.io.File;
import java.io.IOException;

public class AgregarAuto extends AppCompatActivity {

    EditText campoMarca, campoModelo, campoAnio, campoNroMotor, campoNroChasis;
    Button btnGuardar, btnCancelar, btnCargarFoto, btnTomarFoto;
    TextView errorDatos;
    ImageView fotoAuto;

    private Uri imagenUri; //Formato para almacenar fotos.
    int imagen;
    private Bitmap imgToStorage; //Otro Formato para almacenar fotos.
    String error, direccionUriImg; //almacena la dirección de donde se guarda la imagen

    DaoUsuario dao = new DaoUsuario();
    Usuario user;
    Integer idUser = 0;
    Intent x;

    DaoAuto daoAuto = new DaoAuto();
    Auto car;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_auto);

        campoMarca = findViewById(R.id.regMarca);
        campoModelo = findViewById(R.id.regModelo);
        campoAnio = findViewById(R.id.regAnio);
        campoNroMotor = findViewById(R.id.regNroMotor);
        campoNroChasis = findViewById(R.id.regNroChasis);
        fotoAuto = findViewById(R.id.imgRegFotoAuto);

        btnCargarFoto = findViewById(R.id.btnRegCargarFoto);
        btnTomarFoto = findViewById(R.id.btnRegTomarFoto);
        btnGuardar = findViewById(R.id.btnRegAutoGuardar);
        btnCancelar = findViewById(R.id.btnRegAutoCancelar);

        user = dao.getUserbyID(getIntent().getIntExtra("Id", 0), AgregarAuto.this);

        Bundle bundle = getIntent().getExtras();
        idUser = bundle.getInt("id");

        /*Para modificar auto
        marca.setText(car.getMarca());
        modelo.setText(car.getModelo());
        anio.setText(car.getAnio());
        nroMotor.setText(car.getNroMotor());
        nroChasis.setText(car.getNroChasis());
        fotoAuto.setText() */

        btnCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFotoGaleria();
            }

        });

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }

        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDatos()){
                    guardarAuto();
                } else {
                    errorDatos.setVisibility(View.VISIBLE);
                    errorDatos.setText(error); }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AgregarAuto.this, MisAutos.class);
                i.putExtra("Id", user.getId());
                startActivity(i);
                // finish();
            }
        });



    }

    private void guardarAuto(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.ID_USUARIO, idUser); //no se si es asi
        values.put(Utilidades.MARCA,campoMarca.getText().toString());
        values.put(Utilidades.MODELO,campoModelo.getText().toString());
        values.put(Utilidades.ANIO,campoAnio.getText().toString());
        values.put(Utilidades.NRO_MOTOR,campoNroMotor.getText().toString());
        values.put(Utilidades.NRO_CHASIS,campoNroChasis.getText().toString());
        values.put(Utilidades.FOTO_AUTO,direccionUriImg);

        Long idresultante = db.insert("Auto", "id", values);
        if (idresultante>0){//si no lo llegó a guardar
            btnGuardar.setEnabled(false);//deshabilito para que no lo vuelva a poner
        }
        else{
            btnGuardar.setEnabled(true);
        }
       /* Utilidades.perroLog = (Integer)idresultante.intValue();
        System.out.println("el valor de idresultante es:" + Utilidades.perroLog);*/
        Toast.makeText(this, "Auto agregado" + idresultante, Toast.LENGTH_LONG).show();
        Intent i = new Intent(AgregarAuto.this, MisAutos.class);
        startActivity(i);
        finish();

    }

    private boolean validarDatos(){
        String marca = campoMarca.getText().toString();
        String modelo = campoModelo.getText().toString();
        String anio = campoAnio.getText().toString();
        String nroMotor = campoNroMotor.getText().toString();
        String nroChasis = campoNroChasis.getText().toString();

        if (!marca.equals("") && !modelo.equals("") && !anio.equals("") && !nroMotor.equals("") && !nroChasis.equals("")) {
            if(nroMotor.length()==9){
                return true;
            } else{
                error = "errorrr";
                return false;
            }
        }else {
            error = "Todos los campos deben ser completados";
            return false;
        }
    }

    private void cargarFotoGaleria(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("imagen/");
        imagen =10;
        startActivityForResult(Intent.createChooser(i, "Selecciona imagen ;)"), imagen);

    }

    public void abrirCamara() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagenArchivo = null;
        try {
            imagenArchivo = CrearImagen();// intento asignar los datos de la foto(nombre y donde se guarda)
        } catch (IOException ex) {
            Log.e("Error", ex.toString());// si no se genero el archivo por un error lo informa
        }
        if (imagenArchivo != null) {//si tiene foto sacada, la guarda
            imagenUri = FileProvider.getUriForFile(this, "com.example..fileprovider", imagenArchivo); //faltaaaaaaaa
            imagen =100;//numero que se asigna para la camara
            i.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri);//guarda la imagen y va a la linea siguiente
            startActivityForResult(i, imagen);

        }
    }

    private File CrearImagen() throws IOException {
        String nombreImagen = "Foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);// crea o guarda en la carpeta la foto
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);//concatena el nombre de la foto, donde se guarda y el formato
        direccionUriImg = imagen.getAbsolutePath();//asignamos a la variable la direccion adonde esta la foto guardada
        return imagen;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && imagen ==10) { //cuando uso la galeria vale 10, cuando es camara vale 100
            Uri path = data.getData();
            direccionUriImg = path.toString();
            fotoAuto.setImageURI(path);
            Toast.makeText(this, "Se agregó la foto :" + path, Toast.LENGTH_LONG).show();
        }else if (resultCode == RESULT_OK && imagen ==100){
            File imagenfile=new File(direccionUriImg);
            imgToStorage= BitmapFactory.decodeFile(imagenfile.getAbsolutePath());
            fotoAuto.setImageBitmap(imgToStorage);
        }
    }
}