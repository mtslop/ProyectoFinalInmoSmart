package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

import java.util.ArrayList;
import java.util.List;

public class PlataformaInmobiliaria implements OperacionesInmobiliarias {
    private static PlataformaInmobiliaria instanciaUnica;

    private String nombreEmpresa;
    private List<Usuario> listaUsuarios;
    private List<Inmueble> catalogoInmuebles;
    private List<Publicacion> listaPublicaciones;
    private List<Oferta> listaOfertas;
    private List<Transaccion> historicoTransacciones;
    List<Reporte> historicoReportes;
    private int contadorPublicaciones;
    private int contadorOfertas;
    private int contadorTransacciones;

    public PlataformaInmobiliaria(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
        this.listaUsuarios = new ArrayList<>();
        this.catalogoInmuebles = new ArrayList<>();
        this.listaPublicaciones = new ArrayList<>();
        this.listaOfertas = new ArrayList<>();
        this.historicoTransacciones = new ArrayList<>();
        this.contadorPublicaciones = 1;
        this.contadorOfertas = 1;
        this.contadorTransacciones = 1;
    }

    public static PlataformaInmobiliaria getInstancia(String nombre) {
        if (instanciaUnica == null) {
            instanciaUnica = new PlataformaInmobiliaria(nombre);
        }
        return instanciaUnica;
    }

    public boolean registrarUsuario(Usuario usuario) {
        for (Usuario u : listaUsuarios) {
            if (u.getIdentificacion().equals(usuario.getIdentificacion())) {
                System.out.println("Error: ya existe un usuario con identificacion " + usuario.getIdentificacion());
                return false;
            }
        }
        listaUsuarios.add(usuario);
        System.out.println("Usuario registrado: " + usuario.getNombre());
        return true;
    }

    public Usuario buscarUsuarioPorId(String identificacion) {
        for (Usuario u : listaUsuarios) {
            if (u.getIdentificacion().equals(identificacion)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }

    public boolean actualizarUsuario(String identificacion, Usuario usuarioActualizado) {
        for (Usuario u : listaUsuarios) {
            if (u.getIdentificacion().equals(identificacion)) {
                u.setNombre(usuarioActualizado.getNombre());
                u.setTelefono(usuarioActualizado.getTelefono());
                u.setCorreo(usuarioActualizado.getCorreo());
                System.out.println("Usuario actualizado exitosamente.");
                return true;
            }
        }
        System.out.println("Error: Usuario no encontrado para actualizar.");
        return false;
    }

    public boolean eliminarUsuario(String identificacion) {
        Usuario usuarioAEliminar = null;
        for (Usuario u : listaUsuarios) {
            if (u.getIdentificacion().equals(identificacion)) {
                usuarioAEliminar = u;
                break;
            }
        }
        if (usuarioAEliminar != null) {
            listaUsuarios.remove(usuarioAEliminar);
            System.out.println("Usuario eliminado del sistema.");
            return true;
        }
        System.out.println("Error: Usuario no encontrado para eliminar.");
        return false;
    }

    @Override
    public void publicarInmueble(Inmueble inmueble) {
        if (inmueble.getPrecio() <= 0) {
            System.out.println("Error: el precio debe ser mayor a 0.");
            return;
        }
        if (buscarUsuarioPorId(inmueble.getVendedor().getIdentificacion()) == null) {
            System.out.println("Error: el vendedor no esta registrado en el sistema.");
            return;
        }

        catalogoInmuebles.add(inmueble);
        inmueble.getVendedor().agregarPropiedad(inmueble);

        Vendedor vendedor = inmueble.getVendedor();
        vendedor.setPuntosReputacion(vendedor.getPuntosReputacion() + 10);

        String codigoPub = "PUB-" + contadorPublicaciones++;
        Publicacion pub = new Publicacion(codigoPub, "Publicacion de " + inmueble.getTipo(), inmueble);
        listaPublicaciones.add(pub);

        System.out.println("Inmueble publicado: " + inmueble + " | Publicacion: " + codigoPub);
    }

    public List<Inmueble> buscarInmuebles(String ciudad, TipoInmueble tipo,
                                          double precioMaximo, double areaMinima) {
        List<Inmueble> resultados = new ArrayList<>();
        for (Inmueble inm : catalogoInmuebles) {
            if (inm.getEstado() != EstadoInmueble.DISPONIBLE) {
                continue;
            }
            if (ciudad != null && !inm.getCiudad().equalsIgnoreCase(ciudad)) {
                continue;
            }
            if (tipo != null && inm.getTipo() != tipo) {
                continue;
            }
            if (precioMaximo > 0 && inm.getPrecio() > precioMaximo) {
                continue;
            }
            if (areaMinima > 0 && inm.getArea() < areaMinima) {
                continue;
            }
            resultados.add(inm);
        }
        return resultados;
    }

    public Inmueble buscarInmueblePorCodigo(String codigo) {
        for (Inmueble inm : catalogoInmuebles) {
            if (inm.getCodigo().equalsIgnoreCase(codigo)) {
                return inm;
            }
        }
        return null;
    }

    public boolean actualizarInmueble(String codigo, Inmueble inmuebleActualizado) {
        for (Inmueble inm : catalogoInmuebles) {
            if (inm.getCodigo().equalsIgnoreCase(codigo)) {
                inm.setPrecio(inmuebleActualizado.getPrecio());
                inm.setEstado(inmuebleActualizado.getEstado());
                System.out.println("Inmueble actualizado exitosamente.");
                return true;
            }
        }
        System.out.println("Error: Inmueble no encontrado para actualizar.");
        return false;
    }

    public boolean eliminarInmueble(String codigo) {
        Inmueble inmuebleAEliminar = null;
        for (Inmueble inm : catalogoInmuebles) {
            if (inm.getCodigo().equalsIgnoreCase(codigo)) {
                inmuebleAEliminar = inm;
                break;
            }
        }
        if (inmuebleAEliminar != null) {
            catalogoInmuebles.remove(inmuebleAEliminar);
            System.out.println("Inmueble retirado del catálogo.");
            return true;
        }
        System.out.println("Error: Inmueble no encontrado para eliminar.");
        return false;
    }

    @Override
    public void realizarOferta(Oferta oferta) {
        if (oferta.getValorOferta() <= 0) {
            System.out.println("Error: el valor de la oferta debe ser mayor a 0.");
            return;
        }
        if (buscarUsuarioPorId(oferta.getComprador().getIdentificacion()) == null) {
            System.out.println("Error: el comprador no esta registrado.");
            return;
        }
        Inmueble inmExistente = buscarInmueblePorCodigo(oferta.getInmueble().getCodigo());
        if (inmExistente == null) {
            System.out.println("Error: el inmueble no existe en el catalogo.");
            return;
        }
        if (inmExistente.getEstado() != EstadoInmueble.DISPONIBLE) {
            System.out.println("Error: el inmueble no esta disponible.");
            return;
        }

        listaOfertas.add(oferta);

        Comprador comprador = oferta.getComprador();
        comprador.setPuntosReputacion(comprador.getPuntosReputacion() + 5);

        System.out.println("Oferta registrada: " + oferta);
    }

    public Oferta buscarOfertaPorCodigo(String codigoOferta) {
        for (Oferta ofr : listaOfertas) {
            if (ofr.getCodigoOferta().equalsIgnoreCase(codigoOferta)) {
                return ofr;
            }
        }
        return null;
    }

    public void responderOferta(String codigoOferta, boolean aceptar) {
        for (Oferta oferta : listaOfertas) {
            if (oferta.getCodigoOferta().equals(codigoOferta)) {
                if (aceptar) {
                    oferta.setEstado(EstadoOferta.ACEPTADA);
                    Alerta alerta = new Alerta(
                            "Tu oferta fue aceptada para el inmueble " + oferta.getInmueble().getCodigo(),
                            TipoAlerta.CORREO,
                            oferta.getComprador()
                    );
                    alerta.enviar();
                    System.out.println("Oferta " + codigoOferta + " ACEPTADA.");
                } else {
                    oferta.setEstado(EstadoOferta.RECHAZADA);
                    System.out.println("Oferta " + codigoOferta + " RECHAZADA.");
                }
                return;
            }
        }
        System.out.println("Error: no se encontro la oferta con codigo " + codigoOferta);
    }

    public boolean eliminarOferta(String codigoOferta) {
        Oferta ofertaAEliminar = null;
        for (Oferta ofr : listaOfertas) {
            if (ofr.getCodigoOferta().equalsIgnoreCase(codigoOferta)) {
                ofertaAEliminar = ofr;
                break;
            }
        }
        if (ofertaAEliminar != null) {
            listaOfertas.remove(ofertaAEliminar);
            System.out.println("Oferta eliminada del sistema.");
            return true;
        }
        System.out.println("Error: Oferta no encontrada para eliminar.");
        return false;
    }

    @Override
    public void comprarInmueble(Inmueble inmueble, Comprador comprador,
                                double valorFinal, TipoOperacion tipo) {
        if (valorFinal <= 0) {
            System.out.println("Error: el valor final debe ser mayor a 0.");
            return;
        }
        if (buscarUsuarioPorId(comprador.getIdentificacion()) == null) {
            System.out.println("Error: el comprador no esta registrado.");
            return;
        }
        Inmueble inmReal = buscarInmueblePorCodigo(inmueble.getCodigo());
        if (inmReal == null) {
            System.out.println("Error: el inmueble no existe en el catalogo.");
            return;
        }

        if (tipo == TipoOperacion.VENTA) {
            inmReal.setEstado(EstadoInmueble.VENDIDO);
        } else {
            inmReal.setEstado(EstadoInmueble.ARRENDADO);
        }

        String codigoTx = "TX-" + contadorTransacciones++;
        Vendedor vendedorActual = inmReal.getVendedor();
        if (vendedorActual == null) {
            vendedorActual = new Vendedor("000", "Vendedor General", "", "", "");
        }

        Transaccion tx = new Transaccion(codigoTx, comprador, vendedorActual, inmReal, valorFinal, tipo);
        historicoTransacciones.add(tx);

        comprador.setPuntosReputacion(comprador.getPuntosReputacion() + 150);

        System.out.println("Transaccion completada: " + tx);
    }

    public Transaccion buscarTransaccionPorCodigo(String codigoTx) {
        for (Transaccion tx : historicoTransacciones) {
            if (tx.getCodigoTransaccion().equalsIgnoreCase(codigoTx)) {
                return tx;
            }
        }
        return null;
    }

    public boolean actualizarTransaccion(String codigoTx, double nuevoValorFinal) {
        for (Transaccion tx : historicoTransacciones) {
            if (tx.getCodigoTransaccion().equalsIgnoreCase(codigoTx)) {
                tx.setValorFinal(nuevoValorFinal);
                System.out.println("Monto de la transacción actualizado.");
                return true;
            }
        }
        System.out.println("Error: Transacción no encontrada.");
        return false;
    }

    public boolean eliminarTransaccion(String codigoTx) {
        Transaccion txAEliminar = null;
        for (Transaccion tx : historicoTransacciones) {
            if (tx.getCodigoTransaccion().equalsIgnoreCase(codigoTx)) {
                txAEliminar = tx;
                break;
            }
        }
        if (txAEliminar != null) {
            historicoTransacciones.remove(txAEliminar);
            System.out.println("Transacción eliminada del histórico.");
            return true;
        }
        System.out.println("Error: Transacción no encontrada.");
        return false;
    }

    public List<Inmueble> generarRecomendacionesInteligentes(Comprador comprador) {
        List<Inmueble> recomendaciones = new ArrayList<>();
        List<Inmueble> historial = comprador.getHistorialBusquedas();

        if (historial == null || historial.isEmpty()) {
            System.out.println("El historial está vacío. No hay comportamiento registrado para procesar.");
            return recomendaciones;
        }

        for (Inmueble inmuebleCatalogo : catalogoInmuebles) {
            if (inmuebleCatalogo.getEstado() != EstadoInmueble.DISPONIBLE) {
                continue;
            }

            for (Inmueble inmuebleHistorial : historial) {
                if (inmuebleCatalogo.getCiudad().equalsIgnoreCase(inmuebleHistorial.getCiudad()) ||
                        inmuebleCatalogo.getTipo() == inmuebleHistorial.getTipo()) {
                    if (!recomendaciones.contains(inmuebleCatalogo)) {
                        recomendaciones.add(inmuebleCatalogo);
                    }
                }
            }
        }
        return recomendaciones;
    }

    @Override
    public void generarReporte() {
        Reporte nuevoReporte = new Reporte();

        System.out.println("\n========================================");
        System.out.println("   REPORTE DE LA PLATAFORMA " + nombreEmpresa);
        System.out.println("========================================");

        nuevoReporte.reporteInmueblesMasVendidos(historicoTransacciones);
        nuevoReporte.reporteCiudadesConMayorDemanda(catalogoInmuebles);
        nuevoReporte.reporteCompradoresMasActivos(listaUsuarios);
        nuevoReporte.reporteVendedoresConMasPropiedades(listaUsuarios);

        System.out.println("========================================\n");

        historicoReportes.add(nuevoReporte);
    }

    public List<Reporte> obtenerTodosLosReportes() {
        return new ArrayList<>(historicoReportes);
    }

    public boolean actualizarReporte(Reporte reporteAntiguo) {
        for (Reporte rep : historicoReportes) {
            if (rep.equals(reporteAntiguo)) {
                rep.reporteInmueblesMasVendidos(historicoTransacciones);
                rep.reporteCiudadesConMayorDemanda(catalogoInmuebles);
                rep.reporteCompradoresMasActivos(listaUsuarios);
                rep.reporteVendedoresConMasPropiedades(listaUsuarios);
                System.out.println("Reporte actualizado con los datos más recientes del sistema.");
                return true;
            }
        }
        System.out.println("Error: No se encontró el reporte para actualizar.");
        return false;
    }

    public boolean eliminarReporte(Reporte reporte) {
        Reporte reporteAEliminar = null;
        for (Reporte rep : historicoReportes) {
            if (rep.equals(reporte)) {
                reporteAEliminar = rep;
                break;
            }
        }
        if (reporteAEliminar != null) {
            historicoReportes.remove(reporteAEliminar);
            System.out.println("Reporte eliminado del histórico con éxito.");
            return true;
        }
        System.out.println("Error: No se encontró el reporte a eliminar.");
        return false;
    }

    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }

    public List<Usuario> getListaUsuarios() { return listaUsuarios; }
    public void setListaUsuarios(List<Usuario> listaUsuarios) { this.listaUsuarios = listaUsuarios; }

    public List<Inmueble> getCatalogoInmuebles() { return catalogoInmuebles; }
    public void setCatalogoInmuebles(List<Inmueble> catalogoInmuebles) { this.catalogoInmuebles = catalogoInmuebles; }

    public List<Publicacion> getListaPublicaciones() { return listaPublicaciones; }
    public void setListaPublicaciones(List<Publicacion> listaPublicaciones) { this.listaPublicaciones = listaPublicaciones; }

    public List<Oferta> getListaOffers() { return listaOfertas; }
    public void setListaOfertas(List<Oferta> listaOfertas) { this.listaOfertas = listaOfertas; }

    public List<Transaccion> getHistoricoTransacciones() { return historicoTransacciones; }
    public void setHistoricoTransacciones(List<Transaccion> historicoTransacciones) { this.historicoTransacciones = historicoTransacciones; }
}