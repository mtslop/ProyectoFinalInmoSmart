package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

import java.util.ArrayList;
import java.util.List;

public class Recomendacion {
    public List<Inmueble> recomendar(Comprador comprador, List<Inmueble> catalogo) {
        List<Inmueble> recomendados = new ArrayList<>();
        List<Inmueble> historial = comprador.getHistorialBusquedas();

        if (historial.isEmpty()) {
            return recomendados;
        }

        for (int i = 0; i < catalogo.size(); i++) {
            Inmueble candidato = catalogo.get(i);

            if (candidato.getEstado() != EstadoInmueble.DISPONIBLE) {
                continue;
            }

            for (int j = 0; j < historial.size(); j++) {
                Inmueble referencia = historial.get(j);

                boolean mismaCiudad = candidato.getCiudad().equalsIgnoreCase(referencia.getCiudad());
                boolean mismoTipo = candidato.getTipo() == referencia.getTipo();
                double precioMin = referencia.getPrecio() * 0.80;
                double precioMax = referencia.getPrecio() * 1.20;
                boolean rangoPrecio = candidato.getPrecio() >= precioMin && candidato.getPrecio() <= precioMax;

                if ((mismaCiudad || mismoTipo || rangoPrecio) && !recomendados.contains(candidato)) {
                    recomendados.add(candidato);
                    break;
                }
            }
        }
        return recomendados;
    }
}
