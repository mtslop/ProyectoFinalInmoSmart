package co.edu.uniquindio.poo.inmobiliariaproyecto.viewController;

import co.edu.uniquindio.poo.inmobiliariaproyecto.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuariosViewController implements Initializable {
    @FXML private TextField txtId, txtNombre, txtIdentificacion, txtTelefono, txtCorreo;
    @FXML private ComboBox<TipoUsuario> cmbTipo;
    @FXML private Label lblMensaje;
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario,String> colId, colNombre, colIdentificacion, colTelefono, colCorreo, colTipo, colClasificacion;
    @FXML private TableColumn<Usuario,Integer> colPuntos;

    private final PlataformaInmobiliaria plataforma = Session.getPlataforma();
    private ObservableList<Usuario> listaObservable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipo.setItems(FXCollections.observableArrayList(TipoUsuario.values()));
        cmbTipo.getSelectionModel().selectFirst();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colIdentificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));
        colPuntos.setCellValueFactory(new PropertyValueFactory<>("puntosReputacion"));
        colClasificacion.setCellValueFactory(new PropertyValueFactory<>("clasificacion"));
        listaObservable = FXCollections.observableArrayList();
        tablaUsuarios.setItems(listaObservable);
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> { if (sel != null) cargarEnFormulario(sel); });
        refrescarTabla();
    }

    @FXML private void registrarUsuario() {
        String id = txtId.getText().trim(), nombre = txtNombre.getText().trim(),
                ident = txtIdentificacion.getText().trim(), tel = txtTelefono.getText().trim(),
                correo = txtCorreo.getText().trim();
        if (id.isEmpty() || nombre.isEmpty() || ident.isEmpty()) { msg("Complete los campos obligatorios.", false); return; }
        TipoUsuario tipo = cmbTipo.getValue();
        Usuario u = tipo == TipoUsuario.COMPRADOR
                ? new Comprador(id, nombre, ident, tel, correo)
                : new Vendedor(id, nombre, ident, tel, correo);
        boolean ok = plataforma.registrarUsuario(u);
        msg(ok ? "Usuario registrado." : "Ya existe esa identificación.", ok);
        if (ok) {
            refrescarTabla();
            limpiarFormulario();

            if (tipo == TipoUsuario.VENDEDOR && Session.getInmueblesController() != null) {
                Session.getInmueblesController().refrescarVendedores();
            }
            if (tipo == TipoUsuario.COMPRADOR && Session.getOfertasController() != null) {
                Session.getOfertasController().refrescarCompradores();
            }
        }
    }
    @FXML private void actualizarUsuario() {
        Usuario sel = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (sel == null) { msg("Seleccione un usuario.", false); return; }
        TipoUsuario tipo = cmbTipo.getValue();
        Usuario act = tipo == TipoUsuario.COMPRADOR
                ? new Comprador(txtId.getText(), txtNombre.getText(), sel.getIdentificacion(), txtTelefono.getText(), txtCorreo.getText())
                : new Vendedor(txtId.getText(), txtNombre.getText(), sel.getIdentificacion(), txtTelefono.getText(), txtCorreo.getText());
        boolean ok = plataforma.actualizarUsuario(sel.getIdentificacion(), act);
        msg(ok ? "Actualizado." : "No encontrado.", ok);
        if (ok) refrescarTabla();
    }

    @FXML private void eliminarUsuario() {
        Usuario sel = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (sel == null) { msg("Seleccione un usuario.", false); return; }
        boolean ok = plataforma.eliminarUsuario(sel.getIdentificacion());
        msg(ok ? "Eliminado." : "No encontrado.", ok);
        if (ok) { refrescarTabla(); limpiarFormulario(); }
    }

    @FXML private void limpiarFormulario() {
        txtId.clear(); txtNombre.clear(); txtIdentificacion.clear();
        txtTelefono.clear(); txtCorreo.clear();
        cmbTipo.getSelectionModel().selectFirst();
        lblMensaje.setText("");
        tablaUsuarios.getSelectionModel().clearSelection();
    }

    private void cargarEnFormulario(Usuario u) {
        txtId.setText(u.getId()); txtNombre.setText(u.getNombre());
        txtIdentificacion.setText(u.getIdentificacion()); txtTelefono.setText(u.getTelefono());
        txtCorreo.setText(u.getCorreo()); cmbTipo.setValue(u.getTipoUsuario());
    }

    private void refrescarTabla() { listaObservable.setAll(plataforma.obtenerTodosLosUsuarios()); }
    private void msg(String m, boolean ok) { lblMensaje.setStyle(ok ? "-fx-text-fill:green;" : "-fx-text-fill:red;"); lblMensaje.setText(m); }
}