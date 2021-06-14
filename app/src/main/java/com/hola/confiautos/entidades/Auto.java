package com.hola.confiautos.entidades;

public class Auto {

    private Integer id; //0
    private String idUsuario; //1
    private String marca; //2
    private String modelo; //3
    private String anio; //4
    private String nroMotor; //5
    private String nroChasis; //6
    private String fotoAuto; //7

    //Constructor
    public Auto(String idUsuario, String marca, String modelo, String año, String nroMotor, String nroChasis, String fotoAuto) {
        this.idUsuario = idUsuario;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = año;
        this.nroMotor = nroMotor;
        this.nroChasis = nroChasis;
        this.fotoAuto = fotoAuto;
    }

    public Auto() {
    }

    public Auto(Integer id) {
        this.id = id;
    }

  /*  public Auto(String toString, String toString1, String toString2, String toString3, String toString4, String toString5, String ruta_imagen) {
    }*/

    //Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String  getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getNroMotor() {
        return nroMotor;
    }

    public void setNroMotor(String nroMotor) {
        this.nroMotor = nroMotor;
    }

    public String getNroChasis() {
        return nroChasis;
    }

    public void setNroChasis(String nroChasis) {
        this.nroChasis = nroChasis;
    }

    public String getFotoAuto() { return fotoAuto;
    }

    public void setFotoAuto(String fotoAuto) { this.fotoAuto = fotoAuto;
    }
}
