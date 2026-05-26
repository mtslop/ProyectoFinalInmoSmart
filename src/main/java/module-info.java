module co.edu.uniquindio.poo.inmobiliariaproyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports co.edu.uniquindio.poo.inmobiliariaproyecto;
    opens co.edu.uniquindio.poo.inmobiliariaproyecto to javafx.graphics, javafx.fxml;

    exports co.edu.uniquindio.poo.inmobiliariaproyecto.viewController;
    opens co.edu.uniquindio.poo.inmobiliariaproyecto.viewController to javafx.fxml;

    exports co.edu.uniquindio.poo.inmobiliariaproyecto.model;
    opens co.edu.uniquindio.poo.inmobiliariaproyecto.model to javafx.fxml, javafx.base;
}