package uniquindio.edu.co.cineuqfx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/** Ventanitas de aviso reutilizables por todos los controladores. */
public final class Alertas {

    private Alertas() {
    }

    public static void info(String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, "Información", mensaje);
    }

    public static void error(String mensaje) {
        mostrar(Alert.AlertType.ERROR, "Error", mensaje);
    }

    public static void advertencia(String mensaje) {
        mostrar(Alert.AlertType.WARNING, "Atención", mensaje);
    }

    public static boolean confirmar(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText(null);
        alerta.setTitle("Confirmar");
        return alerta.showAndWait().filter(b -> b == ButtonType.YES).isPresent();
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo, mensaje, ButtonType.OK);
        alerta.setHeaderText(null);
        alerta.setTitle(titulo);
        alerta.showAndWait();
    }
}
