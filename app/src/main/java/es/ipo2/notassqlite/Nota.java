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

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }
}
