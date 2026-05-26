package co.edu.uniquindio.poo.inmobiliariaproyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InmobiliariaApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/inmobiliariaproyecto/MainView.fxml"));        Scene scene = new Scene(loader.load(), 900, 620);
        stage.setTitle("InmoSmart - Plataforma Inmobiliaria Digital");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}