package com.hola.confiautos.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;

import com.hola.confiautos.conexionSQLiteHelper.ConexionSQLiteHelper;
import com.hola.confiautos.entidades.Turno;

import static com.hola.confiautos.utilidades.Utilidades.CAMPO_FECHA;
import static com.hola.confiautos.utilidades.Utilidades.CAMPO_HORARIO;
import static com.hola.confiautos.utilidades.Utilidades.CAMPO_ID_AUTO;
import static com.hola.confiautos.utilidades.Utilidades.CAMPO_ID_USUARIO;
import static com.hola.confiautos.utilidades.Utilidades.TABLA_TURNO;

public class DaoTurno {

    public void createTurno (Turno turno, Context context) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper((Context) context, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String insert = "INSERT INTO " + TABLA_TURNO +" ( " + CAMPO_FECHA + "," + CAMPO_HORARIO + "," + CAMPO_ID_AUTO + "," + CAMPO_ID_USUARIO + ") " +
                "VALUES ('"+ turno.getFecha() + "' , '" + turno.getHorario() + "' , '" + turno.getId_auto() + "' , '" + turno.getId_usuario() + "')";

        //db.execSQL(insert);
        db.execSQL(insert);
        db.close();
    }

}
