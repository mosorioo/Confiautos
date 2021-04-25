package com.hola.confiautos;

//Tabla Usuario, aqui se guardan los datos de cada usuario
public class Usuario {
    int Id;
    String Usuario, Password, Nombre, Apellidos, NroTelefono, Email;

    public Usuario(String usuario, String password, String nombre, String apellidos, String nroTelefono, String email) {
        Usuario = usuario;
        Password = password;
        Nombre = nombre;
        Apellidos = apellidos;
        NroTelefono = nroTelefono;
        Email = email;
    }

    public boolean isNull(){
        if(Usuario.equals("")&&Password.equals("")&&Nombre.equals("")&&Apellidos.equals("")&&NroTelefono.equals("")&&Email.equals("")){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Id=" + Id +
                ", Usuario='" + Usuario + '\'' +
                ", Password='" + Password + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Apellidos='" + Apellidos + '\'' +
                ", NroTelefono='" + NroTelefono + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }

    public Usuario() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getNroTelefono() {
        return NroTelefono;
    }

    public void setNroTelefono(String nroTelefono) {
        NroTelefono = nroTelefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
