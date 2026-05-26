package co.edu.uniquindio.poo.inmobiliariaproyecto.viewController;

import co.edu.uniquindio.poo.inmobiliariaproyecto.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportesViewController implements Initializable {
    @FXML private TextArea txtReporte;
    @FXML private ComboBox<String> cmbComprador;

    private final PlataformaInmobiliaria plataforma = Session.getPlataforma();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Session.setReportesController(this);

        refrescarCombos();
    }

    @FXML private void reporteTransacciones() {
        StringBuilder sb = new StringBuilder("=== INMUEBLES VENDIDOS / ARRENDADOS ===\n\n");
        List<Transaccion> txs = plataforma.getHistoricoTransacciones();
        if (txs.isEmpty()) sb.append("Sin transacciones.\n");
        else for (int i = 0; i < txs.size(); i++) {
            Transaccion t = txs.get(i);
            sb.append(i+1).append(". ").append(t.getInmueble()).append("\n   Op: ").append(t.getTipoOperacion())
                    .append(" | Valor: $").append(t.getValorFinal()).append(" | Comprador: ").append(t.getComprador().getNombre()).append("\n\n");
        }
        txtReporte.setText(sb.toString());
    }

    @FXML private void reporteCiudades() {
        StringBuilder sb = new StringBuilder("=== CIUDADES CON MAYOR DEMANDA ===\n\n");
        ArrayList<String> ciudades = new ArrayList<>(); ArrayList<Integer> conteos = new ArrayList<>();
        for (Inmueble inm : plataforma.getCatalogoInmuebles()) {
            boolean found = false;
            for (int j = 0; j < ciudades.size(); j++) if (ciudades.get(j).equalsIgnoreCase(inm.getCiudad())) { conteos.set(j, conteos.get(j)+1); found = true; break; }
            if (!found) { ciudades.add(inm.getCiudad()); conteos.add(1); }
        }
        if (ciudades.isEmpty()) sb.append("Sin inmuebles.\n");
        else for (int i = 0; i < ciudades.size(); i++) sb.append(ciudades.get(i)).append(": ").append(conteos.get(i)).append(" inmuebles\n");
        txtReporte.setText(sb.toString());
    }

    @FXML private void reporteCompradores() {
        StringBuilder sb = new StringBuilder("=== COMPRADORES MÁS ACTIVOS ===\n\n");
        for (Usuario u : plataforma.getListaUsuarios())
            if (u.getTipoUsuario() == TipoUsuario.COMPRADOR)
                sb.append(u.getNombre()).append(" | Puntos: ").append(u.getPuntosReputacion()).append(" | Rango: ").append(u.getClasificacion()).append("\n");
        txtReporte.setText(sb.toString());
    }

    @FXML private void reporteVendedores() {
        StringBuilder sb = new StringBuilder("=== VENDEDORES CON MÁS PROPIEDADES ===\n\n");
        for (Usuario u : plataforma.getListaUsuarios())
            if (u.getTipoUsuario() == TipoUsuario.VENDEDOR) {
                Vendedor v = (Vendedor) u;
                sb.append(v.getNombre()).append(" | Props: ").append(v.getPropiedadesPublicadas().size()).append(" | Rango: ").append(v.getClasificacion()).append("\n");
            }
        txtReporte.setText(sb.toString());
    }

    @FXML private void abrirRecomendaciones() {
        String sel = cmbComprador.getValue();
        if (sel == null) { txtReporte.setText("Seleccione un comprador."); return; }
        Comprador c = (Comprador) plataforma.buscarUsuarioPorId(sel.split(" - ")[0]);
        if (c == null) { txtReporte.setText("No encontrado."); return; }
        List<Inmueble> rec = plataforma.generarRecomendacionesInteligentes(c);
        StringBuilder sb = new StringBuilder("=== RECOMENDACIONES PARA " + c.getNombre().toUpperCase() + " ===\n\n");
        if (rec.isEmpty()) sb.append("Sin recomendaciones. Historial vacío o sin coincidencias.\n");
        else for (int i = 0; i < rec.size(); i++) sb.append(i+1).append(". ").append(rec.get(i)).append("\n");
        txtReporte.setText(sb.toString());
    }

    public void refrescarCombos() {
        cmbComprador.getItems().clear();
        for (var u : Session.getPlataforma().getListaUsuarios()) {
            cmbComprador.getItems().add(u.getIdentificacion());
        }
    }
}