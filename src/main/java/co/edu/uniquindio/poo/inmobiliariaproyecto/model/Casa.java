package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

public class Casa extends Inmueble{
    private int nroPisos;
    private boolean tienePatio;

    public Casa(String codigo, String direccion, String ciudad,
                double area, double precio, Vendedor vendedor,
                int nroPisos, boolean tienePatio) {
        super(codigo, TipoInmueble.CASA, direccion, ciudad, area, precio, vendedor);
        this.nroPisos = nroPisos;
        this.tienePatio = tienePatio;
    }

    public int getNroPisos() { return nroPisos; }
    public void setNroPisos(int nroPisos) { this.nroPisos = nroPisos; }

    public boolean isTienePatio() { return tienePatio; }
    public void setTienePatio(boolean tienePatio) { this.tienePatio = tienePatio; }
}
