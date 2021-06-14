package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.hola.confiautos.conexionSQLiteHelper.ConexionSQLiteHelper;
import com.hola.confiautos.entidades.Auto;
import com.hola.confiautos.entidades.Turno;
import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoAuto;
import com.hola.confiautos.services.DaoTurno;
import com.hola.confiautos.services.DaoUsuario;
import com.hola.confiautos.utilidades.Utilidades;

import java.util.ArrayList;

public class SolicitarTurno extends AppCompatActivity {

    Spinner comboAutos;

    ArrayList<String> listaAutos;
    ArrayList<Auto> autosList;

    Button btnConfirmar, btnCancelar;

    ConexionSQLiteHelper conn;

    private View v;

    DaoUsuario dao = new DaoUsuario();
    Usuario user;
    Integer id = 0;
    Intent x;

    TextView formulario, horario, confirmar, errorTurno;
    Button calendario, reloj;
    TextView autoSeleccionado;

    String idAutoSeleccionado;
    Integer idAutoElegido;

    java.util.Date fechaSeleccionada;
    java.util.Date fechaHoy;

    Boolean errorFecha=false;
    String fecha, error;

    java.util.Date horaSeleccionada;
    java.util.Date horaAhora;
    Boolean errorHora=false;
    String hora1;
    boolean mismoDia=false;

    //variables para guardar calendario
    int anio=0;
    int mes= 0;
    int dia=0;

    int anioSeleccionada=0;
    int mesSeleccionada= 0;
    int diaSeleccionada=0;

    int horaParaCal=0;
    int minParaCal=0;

    DaoAuto daoAuto = new DaoAuto();
    Auto auto = new Auto();

    DaoTurno daoTurno = new DaoTurno();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_turno);

        formulario = (TextView) findViewById(R.id.textDia);
        calendario = (Button) findViewById(R.id.btnCalendario);
        horario = (TextView) findViewById(R.id.textHora);
        reloj = (Button) findViewById(R.id.btnHorario);
        reloj.setEnabled(false);

        comboAutos = findViewById(R.id.spinner_autos);

        errorTurno = findViewById(R.id.txtErrorTurno);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnCancelar = findViewById(R.id.btnTurnoCancelar);

        confirmar = findViewById(R.id.txtTurnoConfirmado);

        conn=new ConexionSQLiteHelper(this,null,1);

        //dao=new daoUsuario(this);
        user = dao.getUserbyID(getIntent().getIntExtra("Id", 0), SolicitarTurno.this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        consultarListaAutos();

        ArrayAdapter<CharSequence> adapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaAutos);
        comboAutos.setAdapter(adapter);

        comboAutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer autoSeleccionado1=0;
                if (position != 0) {
                    //autoSeleccionado1.setText(autosList.get(position-1).getId());
                    autoSeleccionado1.equals(autosList.get(position-1).getId());
                    //idAutoSeleccionado = autoSeleccionado1.getText().toString();
                    //idAutoElegido = Integer.parseInt(idAutoSeleccionado);
                   // auto.setId(idAutoElegido);
                    auto.setId(autoSeleccionado1);

                } /*else {
                    error = "Todos los campos deben ser completados";
                    errorTurno.setVisibility(View.VISIBLE);
                    errorTurno.setText(error);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                    error = "Todos los campos deben ser completados";
                    errorTurno.setVisibility(View.VISIBLE);
                    errorTurno.setText(error);

            }
        });

    /*    btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SolicitarTurno.this, Inicio.class);
                i.putExtra("Id", user.getId());
                startActivity(i);
                finish();
            }
        }); */

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(SolicitarTurno.this);//lo creo
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Desea cancelar la carga del turno?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast.makeText(SolicitarTurno.this, "Se canceló la carga del turno", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SolicitarTurno.this, Inicio.class);
                        i.putExtra("Id", user.getId());
                        startActivity(i);
                        finish();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();

            }
        });

        //para solicitar los permisos de mensaje de texto
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!formulario.getText().equals("") && !horario.getText().equals("")){ //agregar validacion seleccionar auto
                    if(errorFecha==true || errorHora==true){
                        String mensaje ="Seleccione datos correctos";
                        tiempo(mensaje);
                    }else {
                        guardarTurno();
                    }
                }else{
                    String mensaje= "Todos los campos deben estar completos";
                    tiempo(mensaje);
                }
            }
        });

    }

    private void consultarListaAutos(){

        SQLiteDatabase db=conn.getReadableDatabase();

        Auto auto= null;
        autosList= new ArrayList<Auto>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_MIS_AUTOS + " WHERE "+Utilidades.ID_USUARIO+ " =" +user.getId()+ " ORDER BY id ASC;",null);

        while(cursor.moveToNext()){
            auto=new Auto();
            auto.setId(cursor.getInt(0));
            auto.setIdUsuario(cursor.getString(1));
            auto.setMarca(cursor.getString(2));
            auto.setModelo(cursor.getString(3));
            auto.setAnio(cursor.getString(4));
            auto.setNroMotor(cursor.getString(5));
            auto.setNroChasis(cursor.getString(6));

            if (cursor.getString(7)!=null){
                auto.setFotoAuto(cursor.getString(7));
            }else{
                auto.setFotoAuto(null);
            }
            autosList.add(auto);
        }
        obtenerListaAutos();
    }

    private void obtenerListaAutos(){
        listaAutos= new ArrayList<String>();
        listaAutos.add("Seleccione");

        for(int i=0; i<autosList.size(); i++){

            listaAutos.add(autosList.get(i).getId()+" - "+autosList.get(i).getMarca()+ " "+autosList.get(i).getModelo()); //muestra el id y el modelo de auto
        }
    }

    public void abrirCalendario(View view) throws ParseException {

        Calendar cal= Calendar.getInstance();
        anio= cal.get(Calendar.YEAR);
        mes= cal.get (Calendar.MONTH);
        dia= cal.get (Calendar.DAY_OF_MONTH);
        String fechaActual=dia + "-"+ (mes +1) + "-" + anio;//almaceno la fecha de hoy

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        fechaHoy = format.parse(fechaActual);
        DatePickerDialog dpd= new DatePickerDialog(SolicitarTurno.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        anioSeleccionada=year;
                        mesSeleccionada=month;
                        diaSeleccionada=dayOfMonth;
                        fecha=dayOfMonth + "-"+ (month +1) + "-" + year;//fecha seleccionada por el usuario lo conv en string

                        try {
                            fechaSeleccionada = format.parse(fecha);//convierto el string en fecha
                            validarFecha();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },anio,mes,dia);
        dpd.show();
    }

    void validarFecha(){
        if(fechaHoy.compareTo(fechaSeleccionada)>=0){
            String mensaje= "Los turnos se asignan a partir del siguiente día";
            tiempo(mensaje);
            errorFecha=true;
            reloj.setEnabled(false);

        }else{
            formulario.setText("Día seleccionado:"+ fecha);
            errorFecha=false;
            reloj.setEnabled(true);
        }
    }

    public void abrirHora(View view) throws ParseException {

        Calendar c= Calendar.getInstance();

        int hora= c.get(Calendar.HOUR_OF_DAY);
        int min= c.get(Calendar.MINUTE);

        String horaActual=(hora+1) + ":"+ min;//almaceno lahora de ahora

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        horaAhora = format.parse(horaActual);//captura la hora actual

        TimePickerDialog tmd= new TimePickerDialog(SolicitarTurno.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaParaCal=hourOfDay;
                minParaCal=minute;
                hora1=hourOfDay+":"+minute;
                try {
                    horaSeleccionada = format.parse(hora1);//captura la hora seleccionada
                    validarHora();
                    hora1= format.format(horaSeleccionada);
                    horario.setText("Horario seleccionado: "+ hora1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, hora, min,false);
        tmd.show();
    }

    void validarHora() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        java.util.Date horarioInicio=format.parse("09:00");
        java.util.Date horarioFinal=format.parse("18:00");

        if (horaSeleccionada.after(horarioInicio) && horaSeleccionada.before(horarioFinal)){
            errorHora=false;
        }else {
            String mensaje= "Seleccione un horario entre las 09:00 y 18:00";
            tiempo(mensaje);
            errorHora=true;
        }
    }

    public void tiempo(String mensaje){
        AlertDialog.Builder dialog = new AlertDialog.Builder(SolicitarTurno.this);
        dialog.setCancelable(false);
        dialog.setTitle("¡Atención!");
        dialog.setMessage(mensaje);
        final AlertDialog alert = dialog.create();
        alert.show();  //Muestra dialogo.

        //Crea handler, en 2  segundos cierra el dialogo.
        new Handler().postDelayed(new Runnable(){
            public void run(){
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        }, 2000); //2 segundos
    }

    private void guardarTurno() {

        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
                confirmar.setVisibility(View.VISIBLE);
            }

            public void onFinish() {
                        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(SolicitarTurno.this, null, 1);
                        SQLiteDatabase db = conn.getWritableDatabase();

                     //   Turno turno = new Turno(formulario.getText().toString(), horario.getText().toString(), auto.getId().toString(), user.getId().toString());

       // Turno turno = new Turno(formulario.getText().toString(), horario.getText().toString(), idAutoSeleccionado, user.getId().toString());
                //turno.setId_auto(autoSeleccionado.getText().toString());
                        //daoTurno.createTurno(turno, this);
               // daoTurno.createTurno(turno, this);

                        ContentValues values = new ContentValues();

                        values.put("fecha", formulario.getText().toString());//el valor que se asigne se guarda en la bd
                        values.put("horario", horario.getText().toString());
                        //values.put(idAutoSeleccionado.equals().toString());
                        values.put(Utilidades.CAMPO_ID_AUTO, Utilidades.autoLog);
                        values.put(Utilidades.CAMPO_ID_USUARIO, Utilidades.usuarioLog);

                        Long idresultante = db.insert("turnos", "id", values);

                        agregarEventoCalendario();

                        /*Intent i = new Intent(SolicitarTurno.this, Inicio.class);
                        i.putExtra("Id", user.getId());
                        startActivity(i);*/

                         /*if (idresultante>0){//si no lo llegó a guardar
                            guardarTurno().setEnabled(false);//deshabilito para que no lo vuelva a poner
                        } else{
                            guardarTurno().setEnabled(true); }*/
                        // finish();
                        String mensaje= "Turno agendado: " + formulario.getText().toString() +" "+ horario.getText().toString();
                        compartirSms(mensaje);
                        Intent i1 = new Intent(SolicitarTurno.this, Inicio.class);
                        i1.putExtra("Id", user.getId());
                        startActivity(i1); //con esto va a Inicio

                        finish(); // cuando lo ejecuto en el celular tengo que sacar este finish
                    }
                } .start();
    }

    public void agregarEventoCalendario(){
        Intent calIntent = new Intent(Intent.ACTION_INSERT);//crea intento de insertar
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, "Turno Confiautos");
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Bartolomé Mitre 970, C1036 AAR, Buenos Aires");
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Turno Reservado, llegar 10 min antes.");

        Calendar horaInicio = Calendar.getInstance();//obtengo la instancia
        horaInicio.set(anioSeleccionada, mesSeleccionada, diaSeleccionada,horaParaCal,minParaCal);//creo variable del turno
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);//aclara que el evento no del dia entero

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, horaInicio.getTimeInMillis());//inserto los datos en el calendario

        Calendar horaFin = Calendar.getInstance();//genero la hora del fin del turno
        horaFin.set(anioSeleccionada, mesSeleccionada, diaSeleccionada,horaParaCal+1,minParaCal);//asumo que el turno es de 1 hora
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,horaFin.getTimeInMillis());

        calIntent.putExtra(Intent.EXTRA_EMAIL, "merceosorioo@gmail.com");//email del taller para que le llegue mail
        startActivity(calIntent);

    }

    //para cuando pruebo con el celular
    protected void compartirSms( String mensaje) {
    //forma de enviar mensaje con el share
        Intent intentoObj=new Intent();
        intentoObj.setAction(Intent.ACTION_SEND);
        intentoObj.putExtra(Intent.EXTRA_TEXT,mensaje);
        intentoObj.setType("text/plain");
        Intent shareInt=Intent.createChooser(intentoObj,null);
        startActivity(shareInt);

    }

}