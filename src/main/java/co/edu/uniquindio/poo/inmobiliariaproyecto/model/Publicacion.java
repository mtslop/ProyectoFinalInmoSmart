package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

import java.time.LocalDate;

public class Publicacion {
    private String codigoPublicacion;
    private LocalDate fechaPublicacion;
    private String descripcion;
    private Inmueble inmueble;

    public Publicacion(String codigoPublicacion, String descripcion, Inmueble inmueble) {
        this.codigoPublicacion = codigoPublicacion;
        this.descripcion = descripcion;
        this.inmueble = inmueble;
        this.fechaPublicacion = LocalDate.now();
    }

    public String getCodigoPublicacion() { return codigoPublicacion; }
    public void setCodigoPublicacion(String codigoPublicacion) { this.codigoPublicacion = codigoPublicacion; }

    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Inmueble getInmueble() { return inmueble; }
    public void setInmueble(Inmueble inmueble) { this.inmueble = inmueble; }

    @Override
    public String toString() {
        return "Publicacion [" + codigoPublicacion + "] - " + fechaPublicacion + " - " + inmueble;
    }
}
