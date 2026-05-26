package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

public class Terreno extends Inmueble{
    private String tipoSuelo;

    public Terreno(String codigo, String direccion, String ciudad,
                   double area, double precio, Vendedor vendedor,
                   String tipoSuelo) {
        super(codigo, TipoInmueble.TERRENO, direccion, ciudad, area, precio, vendedor);
        this.tipoSuelo = tipoSuelo;
    }

    public String getTipoSuelo() { return tipoSuelo; }
    public void setTipoSuelo(String tipoSuelo) { this.tipoSuelo = tipoSuelo; }
}
