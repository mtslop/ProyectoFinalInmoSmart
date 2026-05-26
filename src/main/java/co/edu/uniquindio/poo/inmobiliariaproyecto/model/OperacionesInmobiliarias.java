package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

public interface OperacionesInmobiliarias {
    void publicarInmueble(Inmueble inmueble);
    void realizarOferta(Oferta oferta);
    void comprarInmueble(Inmueble inmueble, Comprador comprador, double valorFinal, TipoOperacion tipo);
    void generarReporte();
}
