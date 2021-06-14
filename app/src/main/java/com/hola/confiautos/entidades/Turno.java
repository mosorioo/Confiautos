package com.hola.confiautos.entidades;

public class Turno {
    private Integer id;
    private String fecha;
    private String horario;
    private String id_auto;
    private String id_usuario;

    public Turno(String fecha, String horario, String id_auto, String id_usuario) {
        this.id = id;
        this.fecha = fecha;
        this.horario = horario;
        this.id_auto= id_auto;
        this.id_usuario = id_usuario;
    }

    public Turno() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        final String fecha = this.fecha;
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() { return horario; }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getId_auto() {
        return id_auto;
    }

    public void setId_auto(String id_auto) { this.id_auto = id_auto;
    }

    public String getId_usuario() { return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}

