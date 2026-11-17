package uniquindio.edu.co.cineuqfx;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Cine UQ - Ingreso");
        stage.setScene(Navegador.crearEscena("/login.fxml"));
        stage.show();
    }
}