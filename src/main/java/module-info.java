module uniquindio.edu.co.cineuqfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens uniquindio.edu.co.cineuqfx to javafx.fxml;
    exports uniquindio.edu.co.cineuqfx;
    exports uniquindio.edu.co.cineuqfx.model;
}