package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Usuario{
    private List<Inmueble> propiedadesPublicadas;

    public Vendedor(String id, String nombre, String identificacion,
                    String telefono, String correo) {
        super(id, nombre, identificacion, telefono, correo, TipoUsuario.VENDEDOR);
        this.propiedadesPublicadas = new ArrayList<>();
    }

    @Override
    public double calcularBeneficio() {
        return getPuntosReputacion() * 0.01 * 1000000;
    }

    public void agregarPropiedad(Inmueble inmueble) {
        propiedadesPublicadas.add(inmueble);
    }

    public List<Inmueble> getPropiedadesPublicadas() { return propiedadesPublicadas; }
    public void setPropiedadesPublicadas(List<Inmueble> propiedadesPublicadas) { this.propiedadesPublicadas = propiedadesPublicadas; }
}
