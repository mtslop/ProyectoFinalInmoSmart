package co.edu.uniquindio.poo.inmobiliariaproyecto.viewController;

import co.edu.uniquindio.poo.inmobiliariaproyecto.model.PlataformaInmobiliaria;
import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.ActionEvent;

public class PlataformaController {

    private PlataformaInmobiliaria plataforma;

    public void initialize() {
        this.plataforma = new PlataformaInmobiliaria("Mi Inmobiliaria");
    }

    @FXML
    private Button btnRegistrarUsuario;

    @FXML
    void onRegistrarUsuarioClick(ActionEvent event) {
        System.out.println("Clic detectado");
    }
}

