package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

import java.util.ArrayList;
import java.util.List;

public class Reporte {
    public void reporteInmueblesMasVendidos(List<Transaccion> transacciones) {
        System.out.println("=== INMUEBLES VENDIDOS / ARRENDADOS ===");
        if (transacciones.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
            return;
        }
        for (int i = 0; i < transacciones.size(); i++) {
            Transaccion t = transacciones.get(i);
            System.out.println((i + 1) + ". " + t.getInmueble() + " | Operacion: " + t.getTipoOperacion()
                    + " | Valor: $" + t.getValorFinal());
        }
    }
    public void reporteCiudadesConMayorDemanda(List<Inmueble> catalogo) {
        System.out.println("=== CIUDADES CON MAYOR DEMANDA ===");
        List<String> ciudades = new ArrayList<>();
        List<Integer> conteos = new ArrayList<>();

        for (int i = 0; i < catalogo.size(); i++) {
            String ciudad = catalogo.get(i).getCiudad();
            boolean encontrado = false;
            for (int j = 0; j < ciudades.size(); j++) {
                if (ciudades.get(j).equalsIgnoreCase(ciudad)) {
                    conteos.set(j, conteos.get(j) + 1);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                ciudades.add(ciudad);
                conteos.add(1);
            }
        }

        for (int i = 0; i < ciudades.size(); i++) {
            System.out.println("Ciudad: " + ciudades.get(i) + " - Inmuebles: " + conteos.get(i));
        }
    }

    public void reporteCompradoresMasActivos(List<Usuario> usuarios) {
        System.out.println("=== COMPRADORES MAS ACTIVOS ===");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getTipoUsuario() == TipoUsuario.COMPRADOR) {
                System.out.println(u);
            }
        }
    }

    public void reporteVendedoresConMasPropiedades(List<Usuario> usuarios) {
        System.out.println("=== VENDEDORES CON MAS PROPIEDADES ===");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getTipoUsuario() == TipoUsuario.VENDEDOR) {
                Vendedor v = (Vendedor) u;
                System.out.println(v.getNombre() + " - Propiedades: " + v.getPropiedadesPublicadas().size()
                        + " - " + v.getClasificacion());
            }
        }
    }
}
