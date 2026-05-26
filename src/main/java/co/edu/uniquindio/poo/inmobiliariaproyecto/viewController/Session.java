package co.edu.uniquindio.poo.inmobiliariaproyecto.viewController;

import co.edu.uniquindio.poo.inmobiliariaproyecto.model.PlataformaInmobiliaria;

public class Session {
    private static final PlataformaInmobiliaria plataforma = PlataformaInmobiliaria.getInstancia("InmoSmart");

    private static InmueblesViewController inmueblesController;
    private static OfertasViewController ofertasController;
    private static TransaccionesViewController transaccionesController;
    private static ReportesViewController reportesController;

    public static PlataformaInmobiliaria getPlataforma() { return plataforma; }

    public static InmueblesViewController getInmueblesController() { return inmueblesController; }
    public static void setInmueblesController(InmueblesViewController c) { inmueblesController = c; }

    public static OfertasViewController getOfertasController() { return ofertasController; }
    public static void setOfertasController(OfertasViewController c) { ofertasController = c; }

    public static TransaccionesViewController getTransaccionesController() { return transaccionesController; }
    public static void setTransaccionesController(TransaccionesViewController c) { transaccionesController = c; }

    public static ReportesViewController getReportesController() { return reportesController; }
    public static void setReportesController(ReportesViewController c) { reportesController = c; }
}