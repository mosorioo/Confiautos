package com.hola.confiautos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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
            return false; //significa que no puede insertarse en la base pro ya existir
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

}