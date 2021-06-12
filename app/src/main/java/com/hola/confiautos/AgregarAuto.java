package com.hola.confiautos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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

import com.google.android.material.snackbar.Snackbar;
import com.hola.confiautos.conexionSQLiteHelper.ConexionSQLiteHelper;
import com.hola.confiautos.entidades.Auto;
import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoAuto;
import com.hola.confiautos.services.DaoUsuario;
import com.hola.confiautos.utilidades.Utilidades;

import java.io.File;
import java.io.IOException;

public class AgregarAuto<onActivityResult> extends AppCompatActivity {

    EditText campoMarca, campoModelo, campoAnio, campoNroMotor, campoNroChasis;
    Button btnGuardar, btnCancelar, btnCargarFoto, btnTomarFoto;
    TextView errorDatos, texIdUsuario; //Borrar el textIdUsuario
    ImageView fotoAuto;

    //para la imagen
    private Uri imagenUri; //Formato para almacenar fotos.
    int imagen;
    private Bitmap imgToStorage; //Otro Formato para almacenar fotos.
    String error, direccionUriImg; //almacena la dirección de donde se guarda la imagen
    private static final int REQUEST_PERMISSION_CODE = 100;
    private static final int REQUEST_IMAGE_GALLERY = 101;
    private static final int REQUEST_PERMISSION_CAMARA = 102;
    private static final int REQUEST_IMAGE_CAMARA = 103;

    private int PICK_IMAGE_REQUEST = 200;
    private Uri imgFilePath;
 //   private Bitmap imgToStorage;
    String pathImagen;
   // private Uri imagenUri;

    Boolean useCam=false;
    Boolean useGallery=false;

    //variables tomar foto
    String RUTA_IMAGEN;
    int TOMAR_FOTO=100;

    DaoUsuario dao = new DaoUsuario();
    Usuario user;
    Integer idUser = 0; //este creo q no va
    Intent x;

    DaoAuto daoAuto = new DaoAuto();
    Auto car;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_auto);

        user = dao.getUserbyID(getIntent().getIntExtra("Id", 0), AgregarAuto.this);
        Bundle bundle = getIntent().getExtras();
        idUser = bundle.getInt("id");

        useCam=false;
        useGallery=false;

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

        texIdUsuario = findViewById(R.id.idUsuario);
        texIdUsuario.setText(user.getId().toString());

        //youtube
        //para la galeria FUNCIONA
        btnCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(AgregarAuto.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        //cargarFotoGaleria();
                        abrirGaleria();
                    }else{
                        ActivityCompat.requestPermissions(AgregarAuto.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
                    }
                }else{
                    //cargarFotoGaleria();
                    abrirGaleria();
                }
            }

        });

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(AgregarAuto.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        //Tomar foto;
                        abrirCamara();
                    }else{
                        ActivityCompat.requestPermissions(AgregarAuto.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CODE);
                    }
                }else{
                    abrirCamara();
                }
            }

        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   if (validarCamposVacios()){
                    guardarAuto();
            /*    } else {
                    errorDatos.setVisibility(View.VISIBLE);
                    errorDatos.setText(error); } */
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AgregarAuto.this, MisAutos.class);
                i.putExtra("Id", user.getId());
                startActivity(i);
                finish();
            }
        });

    }

    private void abrirGaleria() {

        useGallery=true;
        useCam=false;
        Intent i= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(i,PICK_IMAGE_REQUEST);
    }


    public void abrirCamara () {
            useCam = true;
            useGallery = false;

            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File imagenArchivo = null;
            try {
                imagenArchivo = crearImagen();
            } catch (IOException ex) {
                Log.e("Error", ex.toString());

                if (imagenArchivo != null) {
                    imagenUri = FileProvider.getUriForFile(this, "com.example.mycollection.fileprovider", imagenArchivo);

                    i.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri);
                    startActivityForResult(i, TOMAR_FOTO);
                }
            }
    }

        private File crearImagen () throws IOException {
            String nombreImagen = "Foto_";
            File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
            RUTA_IMAGEN = imagen.getAbsolutePath();
            return imagen;
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (useGallery) {
                if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&
                        data.getData() != null) {
                    imgFilePath = data.getData();
                    RUTA_IMAGEN = getRealPathFromURI(imgFilePath).toLowerCase();
                    fotoAuto.setImageURI(imgFilePath);
                }
            }
            if (useCam) {
                if (resultCode == RESULT_OK && data != null) {
                    File imagenfile = new File(RUTA_IMAGEN);
                    imgFilePath = data.getData();

                    if (imagenfile.exists()) {
                        imgToStorage = BitmapFactory.decodeFile(imagenfile.getAbsolutePath());

                        fotoAuto.setImageBitmap(imgToStorage);
                    }
                }
            }

        }
        public String getRealPathFromURI (Uri uri){
            String[] projection = {MediaStore.Images.Media.DATA};
            @SuppressWarnings("deprecation") Cursor cursor = managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        private void guardarAuto () {

            Auto auto = new Auto(user.getId().toString(), campoMarca.getText().toString(), campoModelo.getText().toString(), campoAnio.getText().toString(), campoNroMotor.getText().toString(), campoNroChasis.getText().toString(), RUTA_IMAGEN);

            daoAuto.createAuto(auto, this);
            imgToStorage = null;
            RUTA_IMAGEN = null;
            useGallery = false;
            useCam = false;
            Toast.makeText(this, "Auto agregado", Toast.LENGTH_LONG).show();
            Intent intento = new Intent(getApplicationContext(), MisAutos.class);
            intento.putExtra("Id", user.getId());
            startActivity(intento);
            onBackPressed();
        }

    private boolean validarCamposVacios(){
        //FOTO?
        String marca = campoMarca.getText().toString();
        String modelo = campoModelo.getText().toString();
        String anio = campoAnio.getText().toString(); // if(anio >1886)
        String nroMotor = campoNroMotor.getText().toString();
        String nroChasis = campoNroChasis.getText().toString();

        if (!marca.equals("") && !modelo.equals("") && !anio.equals("") && !nroMotor.equals("") && !nroChasis.equals("")) {
            if(nroMotor.length()==9){
                return true;
            } else{
                error = "El nro de motor debe contener 9 dígitos.";
                return false;
            }
           /* if(campoNroChasis.length()==12){
                return true;
            } else{
                error = "El nro. de chasis debe contener 12 dígitos.";
                return false;
            }*/
        }else {
            error = "Todos los campos deben ser completados";
            return false;
        }
    }
}