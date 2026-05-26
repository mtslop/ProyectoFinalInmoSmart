package co.edu.uniquindio.poo.inmobiliariaproyecto.viewController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, old, nTab) -> {
            if (nTab != null) {
                String text = nTab.getText();
                if (text.equalsIgnoreCase("Transacciones") && Session.getTransaccionesController() != null) {
                    Session.getTransaccionesController().refrescarCombos();
                    Session.getTransaccionesController().refrescarTabla();
                } else if (text.equalsIgnoreCase("Reportes") && Session.getReportesController() != null) {
                    Session.getReportesController().refrescarCombos();
                }
            }
        });
    }
}