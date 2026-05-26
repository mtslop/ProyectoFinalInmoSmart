package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

public class LocalComercial extends Inmueble{
    private boolean tieneVitrina;
    private int nroBanos;

    public LocalComercial(String codigo, String direccion, String ciudad,
                          double area, double precio, Vendedor vendedor,
                          boolean tieneVitrina, int nroBanos) {
        super(codigo, TipoInmueble.LOCAL_COMERCIAL, direccion, ciudad, area, precio, vendedor);
        this.tieneVitrina = tieneVitrina;
        this.nroBanos = nroBanos;
    }

    public boolean isTieneVitrina() { return tieneVitrina; }
    public void setTieneVitrina(boolean tieneVitrina) { this.tieneVitrina = tieneVitrina; }

    public int getNroBanos() { return nroBanos; }
    public void setNroBanos(int nroBanos) { this.nroBanos = nroBanos; }
}
