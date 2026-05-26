package co.edu.uniquindio.poo.inmobiliariaproyecto.viewController;

import co.edu.uniquindio.poo.inmobiliariaproyecto.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InmueblesViewController implements Initializable {
    @FXML private TextField txtCodigo, txtDireccion, txtCiudad, txtArea, txtPrecio;
    @FXML private TextField txtBusCiudad, txtBusPrecioMax, txtBusAreaMin;
    @FXML private ComboBox<TipoInmueble> cmbTipoInmueble;
    @FXML private ComboBox<String> cmbVendedor, cmbBusTipo;
    @FXML private VBox vboxEspecifico;
    @FXML private Label lblMensaje;
    @FXML private TableView<Inmueble> tablaInmuebles;
    @FXML private TableColumn<Inmueble, String> colCodigo, colTipo, colDireccion, colCiudad, colEstado, colVendedor;
    @FXML private TableColumn<Inmueble, Double> colArea, colPrecio;

    private TextField txtPisoUbicacion, txtValorAdmin, txtNroPisos, txtNroBanos, txtTipoSuelo;
    private CheckBox chkTienePatio, chkTieneVitrina;

    private final PlataformaInmobiliaria plataforma = Session.getPlataforma();
    private ObservableList<Inmueble> listaObservable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipoInmueble.setItems(FXCollections.observableArrayList(TipoInmueble.values()));
        cmbTipoInmueble.getSelectionModel().selectFirst();
        List<String> bustipos = new ArrayList<>();
        bustipos.add("TODOS");
        for (TipoInmueble t : TipoInmueble.values()) bustipos.add(t.name());
        cmbBusTipo.setItems(FXCollections.observableArrayList(bustipos));
        cmbBusTipo.getSelectionModel().selectFirst();

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipo() != null ? d.getValue().getTipo().name() : ""));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado() != null ? d.getValue().getEstado().name() : ""));

        colVendedor.setCellValueFactory(d -> {
            String nombre = (d.getValue().getVendedor() != null) ? d.getValue().getVendedor().getNombre() : "";
            return new SimpleStringProperty(nombre);
        });

        listaObservable = FXCollections.observableArrayList();
        tablaInmuebles.setItems(listaObservable);

        tablaInmuebles.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) cargarEnFormulario(sel);
        });

        mostrarCamposEspecificos();
        refrescarTabla();
        refrescarVendedores();
        Session.setInmueblesController(this);
    }

    @FXML public void mostrarCamposEspecificos() {
        vboxEspecifico.getChildren().clear();
        TipoInmueble tipo = cmbTipoInmueble.getValue();
        if (tipo == null) return;
        switch (tipo) {
            case CASA:
                txtNroPisos = new TextField(); txtNroPisos.setPromptText("Nro. Pisos");
                chkTienePatio = new CheckBox("Tiene Patio");
                vboxEspecifico.getChildren().addAll(new Label("Nro. Pisos:"), txtNroPisos, chkTienePatio);
                break;
            case APARTAMENTO:
                txtPisoUbicacion = new TextField(); txtPisoUbicacion.setPromptText("Piso");
                txtValorAdmin = new TextField(); txtValorAdmin.setPromptText("Valor Administración");
                vboxEspecifico.getChildren().addAll(new Label("Piso:"), txtPisoUbicacion, new Label("Valor Administración:"), txtValorAdmin);
                break;
            case LOCAL_COMERCIAL:
                chkTieneVitrina = new CheckBox("Tiene Vitrina");
                txtNroBanos = new TextField(); txtNroBanos.setPromptText("Nro. Baños");
                vboxEspecifico.getChildren().addAll(chkTieneVitrina, new Label("Nro. Baños:"), txtNroBanos);
                break;
            case TERRENO:
                txtTipoSuelo = new TextField(); txtTipoSuelo.setPromptText("Tipo de Suelo");
                vboxEspecifico.getChildren().addAll(new Label("Tipo de Suelo:"), txtTipoSuelo);
                break;
        }
    }

    @FXML private void publicarInmueble() {
        try {
            String codigo = txtCodigo.getText().trim(), vendId = cmbVendedor.getValue();
            if (codigo.isEmpty() || vendId == null) { msg("Complete todos los campos.", false); return; }
            double area = Double.parseDouble(txtArea.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            Vendedor vendedor = (Vendedor) plataforma.buscarUsuarioPorId(vendId.split(" - ")[0]);
            if (vendedor == null) { msg("Vendedor no encontrado.", false); return; }
            TipoInmueble tipo = cmbTipoInmueble.getValue();
            String dir = txtDireccion.getText().trim(), ciudad = txtCiudad.getText().trim();
            Inmueble inm;
            switch (tipo) {
                case CASA:
                    inm = new Casa(codigo, dir, ciudad, area, precio, vendedor,
                            txtNroPisos != null && !txtNroPisos.getText().isEmpty() ? Integer.parseInt(txtNroPisos.getText()) : 1,
                            chkTienePatio != null && chkTienePatio.isSelected());
                    break;
                case APARTAMENTO:
                    inm = new Apartamento(codigo, dir, ciudad, area, precio, vendedor,
                            txtPisoUbicacion != null && !txtPisoUbicacion.getText().isEmpty() ? Integer.parseInt(txtPisoUbicacion.getText()) : 1,
                            txtValorAdmin != null && !txtValorAdmin.getText().isEmpty() ? Double.parseDouble(txtValorAdmin.getText()) : 0);
                    break;
                case LOCAL_COMERCIAL:
                    inm = new LocalComercial(codigo, dir, ciudad, area, precio, vendedor,
                            chkTieneVitrina != null && chkTieneVitrina.isSelected(),
                            txtNroBanos != null && !txtNroBanos.getText().isEmpty() ? Integer.parseInt(txtNroBanos.getText()) : 0);
                    break;
                default:
                    inm = new Terreno(codigo, dir, ciudad, area, precio, vendedor,
                            txtTipoSuelo != null ? txtTipoSuelo.getText().trim() : "");
            }
            plataforma.publicarInmueble(inm);
            msg("Inmueble publicado.", true);
            refrescarTabla();
            limpiarFormulario();
            if (Session.getOfertasController() != null) {
                Session.getOfertasController().refrescarInmuebles();
            }
        } catch (NumberFormatException e) {
            msg("Verifique los campos numéricos.", false);
        }
    }

    @FXML private void actualizarInmueble() {
        Inmueble sel = tablaInmuebles.getSelectionModel().getSelectedItem();
        if (sel == null) { msg("Seleccione un inmueble.", false); return; }
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            boolean ok = plataforma.actualizarInmueble(sel.getCodigo(),
                    new Inmueble(sel.getCodigo(), sel.getTipo(), sel.getDireccion(), sel.getCiudad(), sel.getArea(), precio, sel.getVendedor()));
            msg(ok ? "Actualizado." : "No encontrado.", ok);
            if (ok) refrescarTabla();
        } catch (NumberFormatException e) { msg("Precio inválido.", false); }
    }

    @FXML private void eliminarInmueble() {
        Inmueble sel = tablaInmuebles.getSelectionModel().getSelectedItem();
        if (sel == null) { msg("Seleccione un inmueble.", false); return; }
        boolean ok = plataforma.eliminarInmueble(sel.getCodigo());
        msg(ok ? "Eliminado." : "No encontrado.", ok);
        if (ok) { refrescarTabla(); limpiarFormulario(); }
    }

    @FXML private void buscarInmuebles() {
        String ciudad = txtBusCiudad.getText().trim();
        String tipoStr = cmbBusTipo.getValue();
        TipoInmueble tipo = (tipoStr == null || tipoStr.equals("TODOS")) ? null : TipoInmueble.valueOf(tipoStr);
        double precioMax = 0, areaMin = 0;
        try { precioMax = Double.parseDouble(txtBusPrecioMax.getText().trim()); } catch (Exception ignored) {}
        try { areaMin = Double.parseDouble(txtBusAreaMin.getText().trim()); } catch (Exception ignored) {}
        List<Inmueble> res = plataforma.buscarInmuebles(ciudad.isEmpty() ? null : ciudad, tipo, precioMax, areaMin);
        listaObservable.setAll(res);
        msg("Resultados: " + res.size(), true);
    }

    @FXML private void limpiarFormulario() {
        txtCodigo.clear(); txtDireccion.clear(); txtCiudad.clear(); txtArea.clear(); txtPrecio.clear();
        cmbTipoInmueble.getSelectionModel().selectFirst(); cmbVendedor.getSelectionModel().clearSelection();
        lblMensaje.setText(""); mostrarCamposEspecificos(); tablaInmuebles.getSelectionModel().clearSelection();
    }

    private void cargarEnFormulario(Inmueble inm) {
        txtCodigo.setText(inm.getCodigo()); txtDireccion.setText(inm.getDireccion());
        txtCiudad.setText(inm.getCiudad()); txtArea.setText(String.valueOf(inm.getArea()));
        txtPrecio.setText(String.valueOf(inm.getPrecio())); cmbTipoInmueble.setValue(inm.getTipo());
        if (inm.getVendedor() != null)
            cmbVendedor.setValue(inm.getVendedor().getIdentificacion() + " - " + inm.getVendedor().getNombre());
        mostrarCamposEspecificos();
    }

    public void refrescarTabla() { listaObservable.setAll(plataforma.getCatalogoInmuebles()); }

    public void refrescarVendedores() {
        ObservableList<String> v = FXCollections.observableArrayList();
        var usuariosEnPlataforma = plataforma.obtenerTodosLosUsuarios();
        if (usuariosEnPlataforma != null) {
            for (Usuario u : usuariosEnPlataforma) {
                if (u.getTipoUsuario() == TipoUsuario.VENDEDOR) {
                    v.add(u.getIdentificacion() + " - " + u.getNombre());
                }
            }
        }
        cmbVendedor.setItems(v);
    }

    private void msg(String m, boolean ok) { lblMensaje.setStyle(ok ? "-fx-text-fill:green;" : "-fx-text-fill:red;"); lblMensaje.setText(m); }
}