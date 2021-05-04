package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoUsuario;

public class Nosotros extends AppCompatActivity {

    Button btnVolver;
    ImageButton iBtnLLamada, iBtnMensaje, iBtnWsA, iBtnInstag;
    DaoUsuario dao = new DaoUsuario();
    Usuario user;
    String message = "¡Hola! Me gustaria obtener información sobre sus servicios";
    String phoneNo = "+5491169452234";

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
                llamar();
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

    //Metodo para Boton Llamar
    private void llamar() {
        // Este va directo al nativo de llamar, no realiza la llamada
        String phoneNo = "1169452234";
        String dial = "tel:" + phoneNo;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));

        /*
        //Este realiza la llamada directamente
        // No funciona
       Intent i1 = new Intent(Intent.ACTION_CALL, Uri.parse("1169452234"));
        if(ActivityCompat.checkSelfPermission(Nosotros.this, Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED)
            return;
        startActivity(i1);*/
    }

    //Metodo para Boton Mensaje
    private void mensaje() {
        // Toast.makeText(this, "Funciona el Boton Mensaje", Toast.LENGTH_LONG).show();

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNo));
        smsIntent.putExtra("sms_body", message);
        startActivity(smsIntent);
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
        String uri = "whatsapp://send?phone="+phoneNo + "&text="+message;
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

}