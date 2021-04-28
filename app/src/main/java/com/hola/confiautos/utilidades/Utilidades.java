package com.hola.confiautos.utilidades;

//En esta clase tengo las constantes que representan los campos y las tablas de la Base de Datos

public class Utilidades {
    public static final String TABLA_USUARIO = "usuario";
    public static final String ID = "id";
    public static final String USUARIO = "usuario";
    public static final String PASSWORD = "password";
    public static final String NOMBRE = "nombre";
    public static final String APELLIDO = "apellido";
    public static final String TELEFONO = "telefono";
    public static final String EMAIL = "email";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + " " +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USUARIO + " TEXT, " +
            "" + PASSWORD + " TEXT, " + NOMBRE + " TEXT, " + APELLIDO + " TEXT, " + TELEFONO + " TEXT, " + EMAIL + " TEXT)";
}