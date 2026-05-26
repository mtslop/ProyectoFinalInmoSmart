package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

import java.time.LocalDate;

public class Oferta {
    public String codigoOferta;
    public Comprador comprador;
    public Inmueble inmueble;
    public double valorOferta;
    public LocalDate fechaOferta;
    public EstadoOferta estado;

    public Oferta(String codigoOferta, Comprador comprador, Inmueble inmueble, double valorOferta) {
        this.codigoOferta = codigoOferta;
        this.comprador = comprador;
        this.inmueble = inmueble;
        this.valorOferta = valorOferta;
        this.fechaOferta = LocalDate.now();
        this.estado = EstadoOferta.PENDIENTE;
    }

    public String getCodigoOferta() { return codigoOferta; }
    public void setCodigoOferta(String codigoOferta) { this.codigoOferta = codigoOferta; }

    public Comprador getComprador() { return comprador; }
    public void setComprador(Comprador comprador) { this.comprador = comprador; }

    public Inmueble getInmueble() { return inmueble; }
    public void setInmueble(Inmueble inmueble) { this.inmueble = inmueble; }

    public double getValorOferta() { return valorOferta; }
    public void setValorOferta(double valorOferta) { this.valorOferta = valorOferta; }

    public LocalDate getFechaOferta() { return fechaOferta; }
    public void setFechaOferta(LocalDate fechaOferta) { this.fechaOferta = fechaOferta; }

    public EstadoOferta getEstado() { return estado; }
    public void setEstado(EstadoOferta estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Oferta [" + codigoOferta + "] de " + comprador.getNombre()
                + " por $" + valorOferta + " - Estado: " + estado;
    }
}
