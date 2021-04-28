package com.hola.confiautos.entidades;

//tabla Usuario
public class Usuario {
    private Integer id;
    private String usuario;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    //constructor
    public Usuario(Integer id, String usuario, String password, String nombre, String apellido, String telefono, String email) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    public Usuario(String s, String toString, String string, String s1, String toString1, String string1) {
    }

    //Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

/*Codigo anterior
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

    public boolean isNull() {
        if (Usuario.equals("") && Password.equals("") && Nombre.equals("") && Apellidos.equals("") && NroTelefono.equals("") && Email.equals("")) {
            return false;
        } else {
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
*/