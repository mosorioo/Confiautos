package com.hola.confiautos.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hola.confiautos.ConexionSQLiteHelper;
import com.hola.confiautos.entidades.Auto;
import com.hola.confiautos.entidades.Usuario;

import static com.hola.confiautos.utilidades.Utilidades.AÑO;
import static com.hola.confiautos.utilidades.Utilidades.ID_USUARIO;
import static com.hola.confiautos.utilidades.Utilidades.MARCA;
import static com.hola.confiautos.utilidades.Utilidades.MODELO;

import static com.hola.confiautos.utilidades.Utilidades.NRO_CHASIS;
import static com.hola.confiautos.utilidades.Utilidades.NRO_MOTOR;
import static com.hola.confiautos.utilidades.Utilidades.TABLA_MIS_AUTOS;


public class DaoAutos {

    public void createAuto (Auto auto, Context context) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper((Context) context, null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();
        String insert = "INSERT INTO " + TABLA_MIS_AUTOS +" ( " + ID_USUARIO + "," + MARCA + "," + MODELO + "," + AÑO + "," + NRO_MOTOR + "," + NRO_CHASIS + ") " +
                "VALUES ('"+ auto.getIdUsuario() + "' , '" + auto.getMarca() + "' , '" + auto.getModelo() + "' , '" + auto.getAño() + "' , '" + auto.getNroMotor() + "' , '" + auto.getNroChasis() + "', '" + auto.getFotoAuto() + "')";

        db.execSQL(insert);
        db.close();
    }

}
