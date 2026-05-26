package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

public class Apartamento extends Inmueble{
    private int pisoUbicacion;
    private double valorAdministracion;

    public Apartamento(String codigo, String direccion, String ciudad, double area, double precio, Vendedor vendedor, int pisoUbicacion, double valorAdministracion) {
        super(codigo, TipoInmueble.APARTAMENTO, direccion, ciudad, area, precio, vendedor);
        this.pisoUbicacion = pisoUbicacion;
        this.valorAdministracion = valorAdministracion;
    }

    public int getPisoUbicacion() { return pisoUbicacion; }
    public void setPisoUbicacion(int pisoUbicacion) { this.pisoUbicacion = pisoUbicacion; }

    public double getValorAdministracion() { return valorAdministracion; }
    public void setValorAdministracion(double valorAdministracion) { this.valorAdministracion = valorAdministracion; }
}
