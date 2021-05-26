package com.hola.confiautos.entidades;

import android.content.Intent;

public class Auto {

    private Integer id;
    private Integer idUsuario;
    private String marca;
    private String modelo;
    private Integer año;
    private String nroMotor;
    private String nroChasis;

    //Constructor
    public Auto(Integer id, Integer idUsuario, String marca, String modelo, Integer año, String nroMotor, String nroChasis) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.nroMotor = nroMotor;
        this.nroChasis = nroChasis;
    }

    public Auto(String marca, String modelo, Integer año, String nroMotor, String nroChasis) {
        this(null, null, marca, modelo, año, nroMotor, nroChasis);
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

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
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
}
