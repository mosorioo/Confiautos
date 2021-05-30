package com.hola.confiautos.entidades;

public class Auto {

    private Integer id;
    private Integer idUsuario;
    private String marca;
    private String modelo;
    private Integer anio;
    private String nroMotor;
    private String nroChasis;
    private String fotoAuto;

    //Constructor
    public Auto(Integer id, Integer idUsuario, String marca, String modelo, Integer año, String nroMotor, String nroChasis, String fotoAuto) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = año;
        this.nroMotor = nroMotor;
        this.nroChasis = nroChasis;
        this.fotoAuto = fotoAuto;
    }

    public Auto(String marca, String modelo, Integer año, String nroMotor, String nroChasis, String fotoAuto) {
        this(null, null, marca, modelo, año, nroMotor, nroChasis, fotoAuto);
    }

    public Auto() {
    }

    //Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
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
