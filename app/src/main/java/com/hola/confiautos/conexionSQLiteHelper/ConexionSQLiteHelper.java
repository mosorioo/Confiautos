package com.hola.confiautos.conexionSQLiteHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hola.confiautos.utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

   // final String CREAR_TABLA_USUARIO="CREATE TABLE IF NOT EXISTS usuarios(id integer primary key autoincrement, usuario text, pass text, nombre text, apellidos text, nroTelefono int, email text)";

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "db_confiautos", factory, version);
    }

    @Override
    //Creo la tabla usuario
    public void onCreate(SQLiteDatabase db_confiautos) {
        db_confiautos.execSQL(Utilidades.CREAR_TABLA_USUARIO);
        db_confiautos.execSQL(Utilidades.CREAR_TABLA_MIS_AUTOS);
        db_confiautos.execSQL(Utilidades.CREAR_TABLA_TURNOS);
    }

    @Override
    //Verifica si existe previamente una versión de la BD
    public void onUpgrade(SQLiteDatabase db_confiautos, int versionAntigua, int versionNueva) {
        db_confiautos.execSQL("DROP TABLE IF EXISTS usuarios");
        db_confiautos.execSQL("DROP TABLE IF EXISTS autos");
        db_confiautos.execSQL("DROP TABLE IF EXISTS turnos");
        onCreate(db_confiautos);
    }
}