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

public class TransaccionesViewController implements Initializable {
    @FXML private ComboBox<String> cmbComprador, cmbInmueble;
    @FXML private ComboBox<TipoOperacion> cmbTipoOperacion;
    @FXML private TextField txtValorFinal, txtCodigoActualizar, txtNuevoValor;
    @FXML private Label lblMensaje;
    @FXML private TableView<Transaccion> tablaTransacciones;
    @FXML private TableColumn<Transaccion, String> colCodigo, colComprador, colVendedor, colInmueble, colTipo, colFecha;
    @FXML private TableColumn<Transaccion, Double> colValor;

    private final PlataformaInmobiliaria plataforma = Session.getPlataforma();
    private ObservableList<Transaccion> listaObservable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipoOperacion.setItems(FXCollections.observableArrayList(TipoOperacion.values()));
        cmbTipoOperacion.getSelectionModel().selectFirst();

        // CORREGIDO: Nombres exactos de los atributos de tu clase Transaccion
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoTransaccion"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorFinal"));

        // Conversión segura del Enum de TipoOperacion a texto
        colTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipoOperacion() != null ? d.getValue().getTipoOperacion().name() : ""));

        // Validaciones contra nulos para objetos anidados
        colComprador.setCellValueFactory(d -> {
            Usuario comprador = d.getValue().getComprador();
            return new SimpleStringProperty(comprador != null ? comprador.getNombre() : "Sin Comprador");
        });

        colVendedor.setCellValueFactory(d -> {
            Usuario vendedor = d.getValue().getVendedor();
            return new SimpleStringProperty(vendedor != null ? vendedor.getNombre() : "Sin Vendedor");
        });

        colInmueble.setCellValueFactory(d -> {
            Inmueble inmueble = d.getValue().getInmueble();
            return new SimpleStringProperty(inmueble != null ? inmueble.getCodigo() : "Sin Código");
        });

        // CORREGIDO: Ahora busca "fecha" para llamar a tu método getFecha() real
        colFecha.setCellValueFactory(d -> {
            Object f = d.getValue().getFecha();
            return new SimpleStringProperty(f != null ? f.toString() : "");
        });

        listaObservable = FXCollections.observableArrayList();
        tablaTransacciones.setItems(listaObservable);

        tablaTransacciones.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) txtCodigoActualizar.setText(sel.getCodigoTransaccion());
        });

        refrescarCombos();
        refrescarTabla();
        Session.setTransaccionesController(this);
    }
    @FXML private void registrarTransaccion() {
        try {
            String comprId = cmbComprador.getValue(), inmCod = cmbInmueble.getValue();
            if (comprId == null || inmCod == null) { msg("Seleccione comprador e inmueble.", false); return; }
            Comprador comprador = (Comprador) plataforma.buscarUsuarioPorId(comprId.split(" - ")[0]);
            Inmueble inmueble = plataforma.buscarInmueblePorCodigo(inmCod.split(" - ")[0]);
            if (comprador == null || inmueble == null) { msg("No válido.", false); return; }
            plataforma.comprarInmueble(inmueble, comprador, Double.parseDouble(txtValorFinal.getText().trim()), cmbTipoOperacion.getValue());
            msg("Transacción registrada.", true); refrescarTabla(); refrescarCombos(); txtValorFinal.clear();
        } catch (NumberFormatException e) { msg("Valor inválido.", false); }
    }

    @FXML private void eliminarTransaccion() {
        Transaccion sel = tablaTransacciones.getSelectionModel().getSelectedItem();
        if (sel == null) { msg("Seleccione una transacción.", false); return; }
        boolean ok = plataforma.eliminarTransaccion(sel.getCodigoTransaccion());
        msg(ok ? "Eliminada." : "No encontrada.", ok); if (ok) refrescarTabla();
    }

    @FXML private void actualizarTransaccion() {
        try {
            boolean ok = plataforma.actualizarTransaccion(txtCodigoActualizar.getText().trim(), Double.parseDouble(txtNuevoValor.getText().trim()));
            msg(ok ? "Actualizada." : "No encontrada.", ok); if (ok) refrescarTabla();
        } catch (NumberFormatException e) { msg("Valor inválido.", false); }
    }

    public void refrescarTabla() {
        var historico = plataforma.getHistoricoTransacciones();
        if (historico != null) {
            listaObservable.setAll(historico);
        }
    }

    public void refrescarCombos() {
        cmbComprador.getItems().clear();
        cmbInmueble.getItems().clear();

        if (plataforma != null) {
            if (plataforma.getListaUsuarios() != null) {
                for (Usuario u : plataforma.getListaUsuarios()) {
                    cmbComprador.getItems().add(u.getIdentificacion());
                }
            }

            if (plataforma.getCatalogoInmuebles() != null) {
                for (Inmueble inm : plataforma.getCatalogoInmuebles()) {
                    cmbInmueble.getItems().add(inm.getCodigo());
                }
            }
        }
    }
    private void msg(String m, boolean ok) { lblMensaje.setStyle(ok ? "-fx-text-fill:green;" : "-fx-text-fill:red;"); lblMensaje.setText(m); }
}