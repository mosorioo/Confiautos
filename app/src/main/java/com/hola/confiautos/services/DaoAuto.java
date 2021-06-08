package com.hola.confiautos.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hola.confiautos.conexionSQLiteHelper.ConexionSQLiteHelper;
import com.hola.confiautos.entidades.Auto;

import static com.hola.confiautos.utilidades.Utilidades.ANIO;
import static com.hola.confiautos.utilidades.Utilidades.FOTO_AUTO;
import static com.hola.confiautos.utilidades.Utilidades.ID_USUARIO;
import static com.hola.confiautos.utilidades.Utilidades.MARCA;
import static com.hola.confiautos.utilidades.Utilidades.MODELO;
import static com.hola.confiautos.utilidades.Utilidades.NRO_CHASIS;
import static com.hola.confiautos.utilidades.Utilidades.NRO_MOTOR;
import static com.hola.confiautos.utilidades.Utilidades.TABLA_MIS_AUTOS;


public class DaoAuto {

    public void createAuto (Auto auto, Context context) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper((Context) context, null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        String insert = "INSERT INTO " + TABLA_MIS_AUTOS +" ( " + ID_USUARIO + "," + MARCA + "," + MODELO + "," + ANIO + "," + NRO_MOTOR + "," + NRO_CHASIS + ", " + FOTO_AUTO +") " +
                "VALUES ('"+ auto.getIdUsuario() + "' , '" + auto.getMarca() + "' , '" + auto.getModelo() + "' , '" + auto.getAnio() + "' , '" + auto.getNroMotor() + "' , '" + auto.getNroChasis() + "', '" + auto.getFotoAuto() + "')";

        db.execSQL(insert);
        db.close();
    }

}
