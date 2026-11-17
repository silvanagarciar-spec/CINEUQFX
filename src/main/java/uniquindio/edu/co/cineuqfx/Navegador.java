package uniquindio.edu.co.cineuqfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Cambia de pantalla (de un FXML a otro) en la misma ventana.
 * Uso desde un controlador:  Navegador.cambiar(unNodoCualquiera, "/admin.fxml", "Título");
 */
public final class Navegador {

    private Navegador() {
    }

    /** Crea una escena a partir de un FXML ubicado en resources, con el estilo del cine. */
    public static Scene crearEscena(String rutaFxml) throws IOException {
        Parent raiz = FXMLLoader.load(Navegador.class.getResource(rutaFxml));
        Scene escena = new Scene(raiz);
        escena.getStylesheets().add(Navegador.class.getResource("/style.css").toExternalForm());
        return escena;
    }

    /** Reemplaza la pantalla actual por otra. */
    public static void cambiar(Node origen, String rutaFxml, String titulo) {
        try {
            Stage ventana = (Stage) origen.getScene().getWindow();
            ventana.setScene(crearEscena(rutaFxml));
            ventana.setTitle(titulo);
            ventana.centerOnScreen();
        } catch (IOException e) {
            Alertas.error("No se pudo abrir la pantalla " + rutaFxml + ": " + e.getMessage());
        }
    }
}
