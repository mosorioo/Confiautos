package com.hola.confiautos;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.OnMapReadyCallback;
import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

public class Nosotros extends AppCompatActivity implements View.OnClickListener {

    Button btnVolver;
    ImageButton iBtnLLamada, iBtnMensaje, iBtnWsA, iBtnInstag;
    DaoUsuario dao = new DaoUsuario();
    Usuario user;
    String message = "¡Hola! Me gustaria obtener información sobre sus servicios";
    String phoneNo = "+5491164949961";

    private static final int REQUEST_PERMISSION_CALL =100;

    int REQUESTCODE = 200; //Valor para saber si el usuario acepto el permiso
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);
        iBtnLLamada = (ImageButton) findViewById(R.id.iBtnLlamar);
        iBtnMensaje = (ImageButton) findViewById(R.id.iBtnSms);
        iBtnWsA = (ImageButton) findViewById(R.id.iBtnWhatsApp);
        iBtnInstag = (ImageButton) findViewById(R.id.iBtnInstagram);
        btnVolver = (Button) findViewById((R.id.btnNosotrosVolver));
        user = dao.getUserbyID(getIntent().getIntExtra("Id", 0), Nosotros.this);

        iBtnLLamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                    llamar();
                } else{
                    Log.i("TAG", "API >23");
                    if(ContextCompat.checkSelfPermission(Nosotros.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                        Log.i("TAG", "Permiso habilitado");
                        llamar();
                    }else{
                        if(ActivityCompat.shouldShowRequestPermissionRationale(Nosotros.this, Manifest.permission.CALL_PHONE)){ //true
                            Log.i("TAG", "Los permisos se encuentran rechazados");
                        }else{
                            Log.i("TAG", "Solicitud de permisos");
                        }
                        ActivityCompat.requestPermissions(Nosotros.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                    }
                }
            }
        });

        iBtnMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje();
            }
        });

        iBtnWsA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsApp();
            }
        });

        iBtnInstag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagram();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_CALL){
            if(permissions.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i("TAG", "Permiso Solicitado (Request)");
                llamar();
            }else{
                Log.i("TAG", "Permiso denegado (Request)");
                if(ActivityCompat.shouldShowRequestPermissionRationale(Nosotros.this, Manifest.permission.CALL_PHONE)){ //true
                    new AlertDialog.Builder(this).setMessage("Debe habilitar los permisos")
                            .setPositiveButton("Vuelva a intentarlo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(Nosotros.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                }
                            })
                            .setNegativeButton("No, gracias.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //No hizo nada
                                    Log.i("TAG", "Se fue sin hacer nada");
                                }
                            }).show();

                }else{
                    Toast.makeText(this, "Debe habilitar los permisos de manera manual", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //Metodo para Boton Llamar
    //@RequiresApi(api = Build.VERSION_CODES.M)
    private void llamar() {
        // Este va directo a llamar, no realiza la llamada

      //  verificarPermisoCall();

        String phoneNo = "1164949961";
       /* String dial = "tel:" + phoneNo;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial))); */

        //Este realiza la llamada directamente
        // No funciona
        /*
        Intent i1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +phoneNo));
        if(ActivityCompat.checkSelfPermission(Nosotros.this, Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED)
            return;
        startActivity(i1); */

        startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:" +phoneNo)));

    }

    //Metodo para Boton Mensaje
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void mensaje() {
        // Toast.makeText(this, "Funciona el Boton Mensaje", Toast.LENGTH_LONG).show();

        verificarPermisoSms();

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNo));
        smsIntent.putExtra("sms_body", message);
        startActivity(smsIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void verificarPermisoSms(){
        int permisoSms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        //Ver de hacerlo al reves
        if(permisoSms == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(this, "Permisos Concedidos", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions( new String[]{Manifest.permission.SEND_SMS}, REQUESTCODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void verificarPermisoCall(){
        int permisoCall = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int permisoAlmacenamiento = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //Ver de hacerlo al reves
        if(permisoCall == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(this, "Permisos Concedidos", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions( new String[]{Manifest.permission.CALL_PHONE}, REQUESTCODE);
        }
    }


    //Metodo para Boton whatsApp
    private void whatsApp() {
        //Toast.makeText(this, "Funciona el Boton WhatsApp", Toast.LENGTH_LONG).show();
        //Este lo manda a whats app pero manda a seleccionar el contacto
       /* Intent i1 = new Intent();
        i1.setAction(Intent.ACTION_SEND);
        i1.putExtra(Intent.EXTRA_TEXT, "¡¡Hola! Me gustaria obtener información sobre sus servicios");
        i1.setType("text/plain");
        i1.setPackage("com.whatsapp"); //para mandarlo directo a whatsapp
        startActivity(i1);*/

        Intent i1 = new Intent();
        i1.setAction(Intent.ACTION_VIEW);
        String uri = "whatsapp://send?phone=" + phoneNo + "&text=" + message;
        i1.setData(Uri.parse(uri));
        i1.setPackage("com.whatsapp"); //para mandarlo directo a whatsapp
        startActivity(i1);
    }

    //Metodo para Boton Instagram
    private void instagram() {
        //
        // Toast.makeText(this, "Funciona el Boton Instagram", Toast.LENGTH_LONG).show();
        Uri uri = Uri.parse("http://instagram.com/_u/confiautos_malaga");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //No encontró la aplicación, abre la versión web.
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/confiautos_malaga")));
        }
    }

    //Metodo para el boton Volver, este regresa a Inicio
    public void Volver(View view) {
        Intent i = new Intent(Nosotros.this, Inicio.class);
        i.putExtra("Id", user.getId());
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {

    }

}