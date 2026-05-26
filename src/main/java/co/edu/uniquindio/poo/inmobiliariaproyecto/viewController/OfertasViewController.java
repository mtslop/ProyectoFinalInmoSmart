package co.edu.uniquindio.poo.inmobiliariaproyecto.viewController;

import co.edu.uniquindio.poo.inmobiliariaproyecto.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class OfertasViewController implements Initializable {
    @FXML private TextField txtCodigoOferta, txtValorOferta, txtCodigoResponder;
    @FXML private ComboBox<String> cmbComprador, cmbInmueble;
    @FXML private Label lblMensaje;
    @FXML private TableView<Oferta> tablaOfertas;
    @FXML private TableColumn<Oferta, String> colCodigo, colComprador, colInmueble, colEstado, colFecha;
    @FXML private TableColumn<Oferta, String> colValor;

    private final PlataformaInmobiliaria plataforma = Session.getPlataforma();
    private ObservableList<Oferta> listaObservable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Session.setOfertasController(this);

        // Configuración limpia sin tipos genéricos complejos
        colCodigo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().codigoOferta));
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().estado != null ? d.getValue().estado.name() : ""));
        colComprador.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getComprador() != null ? d.getValue().getComprador().getNombre() : ""));
        colInmueble.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getInmueble() != null ? d.getValue().getInmueble().getCodigo() : ""));
        colValor.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().valorOferta)));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().fechaOferta != null ? d.getValue().fechaOferta.toString() : ""));

        listaObservable = FXCollections.observableArrayList();
        tablaOfertas.setItems(listaObservable);

        refrescarTabla();
    }
    public void refrescarCompradores() {
        ObservableList<String> compradores = FXCollections.observableArrayList();
        var listaUsuarios = plataforma.obtenerTodosLosUsuarios();
        if (listaUsuarios != null) {
            for (Usuario u : listaUsuarios) {
                if (u.getTipoUsuario() == TipoUsuario.COMPRADOR) {
                    compradores.add(u.getIdentificacion() + " - " + u.getNombre());
                }
            }
        }
        cmbComprador.setItems(compradores);
    }

    public void refrescarInmuebles() {
        ObservableList<String> inmuebles = FXCollections.observableArrayList();
        var catalogo = plataforma.getCatalogoInmuebles();
        if (catalogo != null) {
            for (Inmueble i : catalogo) {
                inmuebles.add(i.getCodigo() + " - " + i.getCiudad() + " ($" + i.getPrecio() + ")");
            }
        }
        cmbInmueble.setItems(inmuebles);
    }

    public void refrescarTabla() {
        if (plataforma.getListaOffers() != null) {
            listaObservable.setAll(plataforma.getListaOffers());
        }
    }

    @FXML private void registrarOferta() {
        try {
            String cod = txtCodigoOferta.getText().trim(), comprId = cmbComprador.getValue(), inmCod = cmbInmueble.getValue();
            if (cod.isEmpty() || comprId == null || inmCod == null) { msg("Complete todos los campos.", false); return; }

            Comprador comprador = (Comprador) plataforma.buscarUsuarioPorId(comprId.split(" - ")[0]);
            Inmueble inmueble = plataforma.buscarInmueblePorCodigo(inmCod.split(" - ")[0]);
            if (comprador == null || inmueble == null) { msg("Comprador o inmueble no válido.", false); return; }

            plataforma.realizarOferta(new Oferta(cod, comprador, inmueble, Double.parseDouble(txtValorOferta.getText().trim())));
            msg("Oferta registrada.", true);
            refrescarTabla();
            txtCodigoOferta.clear();
            txtValorOferta.clear();
        } catch (NumberFormatException e) {
            msg("Valor inválido.", false);
        }
    }

    @FXML private void eliminarOferta() {
        Oferta sel = tablaOfertas.getSelectionModel().getSelectedItem();
        if (sel == null) { msg("Seleccione una oferta.", false); return; }
        boolean ok = plataforma.eliminarOferta(sel.getCodigoOferta());
        msg(ok ? "Eliminada." : "No encontrada.", ok);
        if (ok) refrescarTabla();
    }

    @FXML private void aceptarOferta() {
        String cod = txtCodigoResponder.getText().trim();
        if (cod.isEmpty()) { msg("Ingrese el código.", false); return; }
        plataforma.responderOferta(cod, true);
        msg("Oferta aceptada.", true);
        refrescarTabla();
    }

    @FXML private void rechazarOferta() {
        String cod = txtCodigoResponder.getText().trim();
        if (cod.isEmpty()) { msg("Ingrese el código.", false); return; }
        plataforma.responderOferta(cod, false);
        msg("Oferta rechazada.", true);
        refrescarTabla();
    }

    public void refrescarCombos() {
        ObservableList<String> compradores = FXCollections.observableArrayList(), inmuebles = FXCollections.observableArrayList();
        var usuarios = plataforma.obtenerTodosLosUsuarios();
        if (usuarios != null) {
            for (Usuario u : usuarios) {
                if (u.getTipoUsuario() == TipoUsuario.COMPRADOR) compradores.add(u.getIdentificacion() + " - " + u.getNombre());
            }
        }
        var catalogo = plataforma.getCatalogoInmuebles();
        if (catalogo != null) {
            for (Inmueble i : catalogo) {
                if (i.getEstado() == EstadoInmueble.DISPONIBLE) inmuebles.add(i.getCodigo() + " - " + i.getCiudad() + " $" + i.getPrecio());
            }
        }
        cmbComprador.setItems(compradores);
        cmbInmueble.setItems(inmuebles);
    }

    private void msg(String m, boolean ok) { lblMensaje.setStyle(ok ? "-fx-text-fill:green;" : "-fx-text-fill:red;"); lblMensaje.setText(m); }
}