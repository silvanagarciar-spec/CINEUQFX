package uniquindio.edu.co.cineuqfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import uniquindio.edu.co.cineuqfx.AppContext;
import uniquindio.edu.co.cineuqfx.Navegador;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtClave;
    @FXML private Label lblError;
    @FXML private Button btnIngresar;

    // ---------------------------------------------------------------------
    // TEMPORAL: su diagrama UML no tiene (todavía) las clases Administrador /
    // Operador, así que las credenciales están aquí. Cuando existan esas
    // clases, reemplacen autenticar() por una llamada a su lógica.
    // ---------------------------------------------------------------------
    private AppContext.Rol autenticar(String usuario, String clave) {
        if (usuario.equals("admin") && clave.equals("admin123")) {
            return AppContext.Rol.ADMINISTRADOR;
        }
        if (usuario.equals("operador") && clave.equals("oper123")) {
            return AppContext.Rol.OPERADOR;
        }
        return null;
    }

    @FXML
    private void onIngresar() {
        String usuario = txtUsuario.getText().trim();
        String clave = txtClave.getText();

        if (usuario.isEmpty() || clave.isEmpty()) {
            lblError.setText("Escribe tu usuario y tu contraseña.");
            return;
        }

        AppContext.Rol rol = autenticar(usuario, clave);
        if (rol == null) {
            lblError.setText("Usuario o contraseña incorrectos.");
            txtClave.clear();
            return;
        }

        AppContext.iniciarSesion(usuario, rol);
        if (rol == AppContext.Rol.ADMINISTRADOR) {
            Navegador.cambiar(btnIngresar, "/admin.fxml", "Cine UQ - Administrador");
        } else {
            Navegador.cambiar(btnIngresar, "/operador.fxml", "Cine UQ - Operador");
        }
    }
}
