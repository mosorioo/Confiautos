package com.hola.confiautos.utilidades;

//En esta clase tengo las constantes que representan los campos y las tablas de la Base de Datos

public class Utilidades {

    //TABLA USUARIOS
    public static final String TABLA_USUARIO = "usuario";
    public static final String ID = "id";
    public static final String USUARIO = "usuario";
    public static final String PASSWORD = "password";
    public static final String NOMBRE = "nombre";
    public static final String APELLIDO = "apellido";
    public static final String TELEFONO = "telefono";
    public static final String EMAIL = "email";

    public static final String TABLA_MIS_AUTOS = "mis_autos";
    public static final String ID_AUTO = "id";
    public static final String ID_USUARIO = "id_usuario";
    public static final String MARCA = "marca";
    public static final String MODELO = "modelo";
    public static final String AÑO = "año";
    public static final String NRO_MOTOR = "nor_motor";
    public static final String NRO_CHASIS = "nro_chasis";
    public static final String FOTO_AUTO = "foto_auto";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + " " +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USUARIO + " TEXT, " +
            "" + PASSWORD + " TEXT, " + NOMBRE + " TEXT, " + APELLIDO + " TEXT, " + TELEFONO + " TEXT, " + EMAIL + " TEXT)";

    public static final String CREAR_TABLA_MIS_AUTOS = "CREATE TABLE " + TABLA_MIS_AUTOS + " " +
            "(" + ID_AUTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_USUARIO + " TEXT, " + MARCA + " TEXT, " +
            "" + MODELO + " TEXT, " + AÑO + " TEXT, " + NRO_MOTOR + " TEXT, " + NRO_CHASIS + " TEXT, " + FOTO_AUTO + " TEXT)";

}