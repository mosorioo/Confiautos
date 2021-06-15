package com.hola.confiautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hola.confiautos.conexionSQLiteHelper.ConexionSQLiteHelper;
import com.hola.confiautos.entidades.Auto;
import com.hola.confiautos.entidades.AutoAdaptador;
import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.services.DaoAuto;
import com.hola.confiautos.services.DaoUsuario;
import com.hola.confiautos.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class MisAutos extends AppCompatActivity implements View.OnClickListener {

    Button btnVolv, btnAgregar, btnModificar, btnEliminar;
    TextView texIdUsuario;
    private View v;

    DaoUsuario dao = new DaoUsuario();
    Usuario user;
    Integer id = 0; //para recuperar el id de usuario
    Intent x;

    //auto
    DaoAuto daoAuto = new DaoAuto();
    Auto auto = new Auto();


    ListView mListView;
    List<Auto> mListAutos;
    ListAdapter mAdapter;
    ConexionSQLiteHelper conn; //conn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_autos);

        conn=new ConexionSQLiteHelper(this,null,1);
        mListView=findViewById(R.id.listAutos);

        user = dao.getUserbyID(getIntent().getIntExtra("Id", 0), MisAutos.this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

      //  llenarBasePrueba();
        consultarAutos();

        mAdapter=new AutoAdaptador(this,R.layout.card_view_auto,mListAutos);
        mListView.setAdapter(mAdapter);

        btnAgregar = findViewById(R.id.btnAgregarAuto);
        btnEliminar = findViewById(R.id.btnEliminarAuto);
        btnVolv = findViewById(R.id.btnMisAutosVolver);
        //dao=new daoUsuario(this);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MisAutos.this, AgregarAuto.class);
                i.putExtra("Id", user.getId());
                startActivity(i);
                finish();
            }
        });

        btnVolv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MisAutos.this, Inicio.class);
                i.putExtra("Id", user.getId());
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    //Solo para la prueba rapida

    private void llenarBasePrueba() {

        SQLiteDatabase db=conn.getWritableDatabase();

        for(int i=0;i<3;i++){
            ContentValues values=new ContentValues();
            values.put(Utilidades.ID_USUARIO,id.toString());
            values.put(Utilidades.MARCA,"PEUGEOT ");
            values.put(Utilidades.MODELO,"207");
            values.put(Utilidades.ANIO,"2006");
            values.put(Utilidades.NRO_MOTOR,"121245454");
            values.put(Utilidades.NRO_CHASIS,"555454");
            Long idresultante=db.insert(Utilidades.TABLA_MIS_AUTOS,Utilidades.ID_AUTO,values);
        }

    }

    private void consultarAutos() {

        SQLiteDatabase db=conn.getReadableDatabase();
        Auto autoObj= null;
        mListAutos= new ArrayList<Auto>();

     //   Cursor cursor1=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_MIS_AUTOS +" ORDER BY id DESC;",null);

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_MIS_AUTOS + " WHERE "+Utilidades.ID_USUARIO+ " =" +user.getId()+ " ORDER BY id ASC;",null);

        while(cursor.moveToNext()){
            autoObj=new Auto();
            autoObj.setId(cursor.getInt(0));
            autoObj.setIdUsuario(cursor.getString(1));
            autoObj.setMarca(cursor.getString(2));
            autoObj.setModelo(cursor.getString(3));
            autoObj.setAnio(cursor.getString(4));
            autoObj.setNroMotor(cursor.getString(5));
            autoObj.setNroChasis(cursor.getString(6));

            if (cursor.getString(7)!=null){
                autoObj.setFotoAuto(cursor.getString(7));
            }else{
                autoObj.setFotoAuto(null);
            }

            //autoObj.setFotoAuto(null);
            mListAutos.add(autoObj);
        }
    }
}