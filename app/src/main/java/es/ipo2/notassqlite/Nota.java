package es.ipo2.notassqlite;

/**
 * Created by alber on 24/05/2018.
 */

public class Nota {
    private int idNota;
    private String titulo;
    private  String contenido;
    private int prioridad;
    private String fecha;
    private String tipo;

    public Nota(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;

    }

    public Nota(String titulo, String contenido,  String fecha, String tipo, int prioridad) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.prioridad = prioridad;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Nota(int idNota,String titulo, String contenido,  String fecha, String tipo, int prioridad) {
        this.idNota=idNota;
        this.titulo = titulo;
        this.contenido = contenido;
        this.prioridad = prioridad;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
