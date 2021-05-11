package com.hola.confiautos.services;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hola.confiautos.ConexionSQLiteHelper;
import com.hola.confiautos.entidades.Usuario;
import com.hola.confiautos.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Collection;

import static com.hola.confiautos.utilidades.Utilidades.*;

public class DaoUsuario {

    public void createUsuario (Usuario usuario, Context context) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper((Context) context, null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();
        String insert = "INSERT INTO " + TABLA_USUARIO +" ( " + USUARIO + "," + PASSWORD + "," + NOMBRE + "," + APELLIDO + "," + TELEFONO + "," + EMAIL + ") " +
                "VALUES ('"+ usuario.getUsuario() + "' , '" + usuario.getPassword() + "' , '" + usuario.getNombre() + "' , '" + usuario.getApellido() + "' , '" + usuario.getTelefono() + "' , '" + usuario.getEmail() + "')";

        db.execSQL(insert);
        db.close();
    }

    /*Update para un solo campo
    public void updateUsuario (Integer id, String usuario, String password, String nombre, String apellido, String telefono, String email, Context context ) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        if (db!= null){
            db.execSQL("UPDATE " + Utilidades.TABLA_USUARIO + " SET "  +Utilidades.PASSWORD + " = '" + password + "' WHERE " +Utilidades.ID+" =" +id, null);
            db.close();
        }
    }*/

    public Collection<Usuario> buscarUsuarios(Context context) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLA_USUARIO, null);
        Collection<Usuario> usuarios = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                usuarios.add(convertToUser(c));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        conn.close();
        return usuarios;
    }

    private Usuario convertToUser(Cursor c) {
        Usuario usuario = new Usuario();
        usuario.setId(c.getInt(c.getColumnIndex(ID)));
        usuario.setUsuario(c.getString(c.getColumnIndex(USUARIO)));
        usuario.setPassword(c.getString(c.getColumnIndex(PASSWORD)));
        usuario.setNombre(c.getString(c.getColumnIndex(NOMBRE)));
        usuario.setApellido(c.getString(c.getColumnIndex(APELLIDO)));
        usuario.setTelefono(c.getString(c.getColumnIndex(TELEFONO)));
        usuario.setEmail(c.getString(c.getColumnIndex(EMAIL)));
        return usuario;
    }

    //Devuelve el usuario que encuentra o null
    public Usuario getUserbyUsuarioAndPass(String user, String pass, Context context) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLA_USUARIO + " WHERE "
                + USUARIO + " ='" + user + "' AND " + PASSWORD + " ='" + pass + "'", null);
        if (c.moveToFirst()) {
            return convertToUser(c);
        }
        return null;
    }

    //
    public Usuario getUserbyID(int id, Context context) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLA_USUARIO + " WHERE "
                + ID+" =" +id, null);
        if (c.moveToFirst()) {
            return convertToUser(c);
        }
        return null;
    }

    public Integer validarUsuario (Usuario usuario, Context context){
      //  ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, null, 1);
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper((Context) context, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Integer exist;
        Cursor c = db.rawQuery("SELECT COUNT (" + USUARIO + ") FROM "
                + TABLA_USUARIO + " WHERE USUARIO = '" + usuario.getUsuario().toString() + "'", null);
        c.moveToNext();
        exist= c.getInt(0);
        c.close();
        db.close();
        conn.close();
        return exist;
    }

    public boolean isNull(String u, String p, String n, String a, String t, String e) {
        if (u.equals("") || p.equals("") || n.equals("") || a.equals("") || t.equals("") || e.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isNullSinUser(String p, String n, String a, String t, String e) {
        if (p.equals("") || n.equals("") || a.equals("") || t.equals("") || e.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    //Actualiza los datos del usuario sin el campo usuario
    public boolean updateUsuario (Usuario user, Context context){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper((Context) context, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues cv= new ContentValues();
        cv.put(PASSWORD, user.getPassword());
        cv.put(NOMBRE, user.getNombre());
        cv.put(APELLIDO, user.getApellido());
        cv.put(TELEFONO, user.getTelefono());
        cv.put(EMAIL, user.getEmail());
        int row = db.update( TABLA_USUARIO, cv, "id="+ user.getId(), null);
        System.out.println(row);
        return row >0;
        //return (db.update( TABLA_USUARIO, cv, "id="+ user.getId(), null)>0); //Como poner el Table: tabla_usuario
    }

}

/*
//Antiguo daoUsuario
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hola.confiautos.entidades.Usuario;

import java.util.ArrayList;

//Esta clase sirve para acceder a la base de datos
//Mi codigo anterior que funciona + o -
public class daoUsuario {
    Context c;
    Usuario u;
    ArrayList<Usuario> lista;
    SQLiteDatabase sql;
    String bd="BDUsuarios";
    String tabla="create table if not exists usuario (id integer primary key autoincrement, usuario text, pass text, nombre text, apellidos text, nroTelefono int, email text)";

    public daoUsuario(Context c){
        this.c=c;
        sql=c.openOrCreateDatabase(bd, Context.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        u=new Usuario();
    }

    public boolean insertUsuario(Usuario u) {
        if (buscar(u.getUsuario()) == 0) { // busca el usuario, si es igual a 0 el usuario no existe
            ContentValues cv = new ContentValues();
            cv.put("usuario", u.getUsuario());
            cv.put("pass", u.getPassword());
            cv.put("nombre", u.getNombre());
            cv.put("apellidos", u.getApellidos());
            cv.put("nrotelefono", u.getNroTelefono());
            cv.put("email", u.getEmail());
            return (sql.insert("usuario", null, cv) > 0);
        } else{
            return false; //significa que no puede insertarse en la base por ya existir
        }
    }

    public int buscar(String u){
        int x=0; //cuenta cuantos usuarios hay con el que acabo de crear
        lista=selectUsuarios();
        for(Usuario us:lista){
            if(us.getUsuario().equals(u)){ //para verificar que no existe otro usuario ingresado con el mismo usuario
                x++;
            }
        }
        return x; //si regresa 0 es xq no hay otro usuario con ese mismo usuario

    }

    public ArrayList<Usuario> selectUsuarios(){
        ArrayList<Usuario> lista=new ArrayList<Usuario>();
        lista.clear();
        Cursor cr =sql.rawQuery("select * from usuario", null);
        if(cr!=null&&cr.moveToFirst()){
            do{
                Usuario u=new Usuario();
                u.setId(cr.getInt(0));
                u.setUsuario(cr.getString(1));
                u.setPassword(cr.getString(2));
                u.setNombre(cr.getString(3));
                u.setApellidos(cr.getString(4));
                u.setNroTelefono(cr.getString(5));
                u.setEmail(cr.getString(6));
                lista.add(u);
            }while(cr.moveToNext());
        }
        return lista;
    }
    //Creo el metodo Login que recibe 2 valores: el usuario y password para validarlos
    //
    public int login(String u, String p){ //va a buscar en la BD que exista
        int a=0;
        Cursor cr =sql.rawQuery("select * from usuario", null);
        if(cr.moveToFirst()){ //el cursor se mueve al primero
            do {
                if (cr.getString(1).equals(u) && cr.getString(2).equals(p)) {
                    a++;
                }
            }while (cr.moveToNext());
        }
        return  a;
    }

    //Creo el metodo de Get usuario para recuperarlo

    public Usuario getUsuario(String u, String p){
        lista=selectUsuarios();
        for(Usuario us:lista){
            if(us.getUsuario().equals(us)&&us.getPassword().equals(p)){
                return us;
            }
        }
        return null;
    }

    public Usuario getUsuarioById(int id){
        lista=selectUsuarios();
        for(Usuario us:lista){
            if(us.getId()==id){
                return us;
            }
        }
        return null;
    }

    /*
    //Para obtener el usuario y password
    public Usuario getUserByUserAndPass (String u, String p, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_USUARIO+" WHERE "
                +Utilidades.CAMPO_USUARIO+" ='"+u+"' AND " +Utilidades.CAMPO_PASSWORD+ " ='" +p+"'",null);
        if (cursor.moveToFirst()){
            return convertToUser(cursor);
        }
        return null;
    }

    //Obtiene el usuario por el ID
    public Usuario getUserById (int id, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_USUARIO+" WHERE "
                +Utilidades.CAMPO_ID+" ="+id,null);
        if (cursor.moveToFirst()){
            return convertToUser(cursor);
        }
        return null;
    }
}*/