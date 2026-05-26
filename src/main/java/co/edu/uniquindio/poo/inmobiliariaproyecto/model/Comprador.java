package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

import java.util.ArrayList;
import java.util.List;

public class Comprador extends Usuario {

    private List<Inmueble> historialBusquedas;

    public Comprador(String id, String nombre, String identificacion, String telefono, String correo) {
        super(id, nombre, identificacion, telefono, correo, TipoUsuario.COMPRADOR);
        this.historialBusquedas = new ArrayList<>();
    }

    @Override
    public double calcularBeneficio() {
        return getPuntosReputacion() * 0.05;
    }

    public List<Inmueble> buscarPropiedades(String criterio, PlataformaInmobiliaria plataforma) {
        System.out.println(getNombre() + " está buscando propiedades en la ciudad: " + criterio);
        List<Inmueble> resultados = plataforma.buscarInmuebles(criterio, null, 0, 0);
        return resultados;
    }

    public void realizarOferta(Inmueble inmueble, double monto, PlataformaInmobiliaria plataforma) {
        System.out.println(getNombre() + " quiere realizar una oferta de $" + monto);

        String codigoOferta = "OFR-" + System.currentTimeMillis();
        Oferta nuevaOferta = new Oferta(codigoOferta,this,inmueble,monto);

        plataforma.realizarOferta(nuevaOferta);
    }

    public void comprar(Inmueble inmueble, PlataformaInmobiliaria plataforma) {
        System.out.println(getNombre() + " ha decidido COMPRAR el inmueble.");
        plataforma.comprarInmueble(inmueble, this, inmueble.getPrecio(), TipoOperacion.VENTA);
    }

    public void alquilar(Inmueble inmueble, PlataformaInmobiliaria plataforma) {
        System.out.println(getNombre() + " ha decidido ALQUILAR el inmueble.");
        plataforma.comprarInmueble(inmueble, this, inmueble.getPrecio(), TipoOperacion.ARRIENDO);
    }

    public void agregarAlHistorial(Inmueble inmueble) {
        if (!historialBusquedas.contains(inmueble)) {
            this.historialBusquedas.add(inmueble);
        }
    }

    public List<Inmueble> getHistorialBusquedas() { return historialBusquedas; }
    public void setHistorialBusquedas(List<Inmueble> historialBusquedas) { this.historialBusquedas = historialBusquedas; }
}