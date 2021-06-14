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
    public static final String ANIO = "año";
    public static final String NRO_MOTOR = "nor_motor";
    public static final String NRO_CHASIS = "nro_chasis";
    public static final String FOTO_AUTO = "foto_auto";

    public static final String TABLA_TURNO="turnos";
    public static final String CAMPO_TURNO_ID="id";
    public static final String CAMPO_FECHA="fecha";
    public static final String CAMPO_HORARIO= "horario";
    public static final String CAMPO_ID_AUTO="id_auto";
    //public static final String CAMPO_ID_AUTO="autoSeleccionado";
    public static final String CAMPO_ID_USUARIO="id_usuario";



    public static Integer usuarioLog;
    public static Integer autoLog;

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + " " +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USUARIO + " TEXT, " +
            "" + PASSWORD + " TEXT, " + NOMBRE + " TEXT, " + APELLIDO + " TEXT, " + TELEFONO + " TEXT, " + EMAIL + " TEXT)";

    public static final String CREAR_TABLA_MIS_AUTOS = "CREATE TABLE " + TABLA_MIS_AUTOS + " " +
            "(" + ID_AUTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_USUARIO + " TEXT, " + MARCA + " TEXT, " +
            "" + MODELO + " TEXT, " + ANIO + " TEXT, " + NRO_MOTOR + " TEXT, " + NRO_CHASIS + " TEXT, " + FOTO_AUTO + " TEXT)";


    public static final String CREAR_TABLA_TURNOS = "CREATE TABLE "+ TABLA_TURNO+ " " +
            "(" +CAMPO_TURNO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_FECHA +"   TEXT, " + CAMPO_HORARIO + " TEXT, " +
            "" + CAMPO_ID_AUTO + " TEXT," +CAMPO_ID_USUARIO +" TEXT)";

}