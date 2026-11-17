package uniquindio.edu.co.cineuqfx.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import uniquindio.edu.co.cineuqfx.Alertas;
import uniquindio.edu.co.cineuqfx.AppContext;
import uniquindio.edu.co.cineuqfx.Navegador;
import uniquindio.edu.co.cineuqfx.model.CinemaUq;
import uniquindio.edu.co.cineuqfx.model.Cliente;
import uniquindio.edu.co.cineuqfx.model.Pelicula;
import uniquindio.edu.co.cineuqfx.model.TipoCliente;

/**
 * Pantalla del operador: consulta la cartelera y gestiona clientes.
 * La venta de entradas se agrega como una pestaña más cuando la clase Funcion esté lista.
 */
public class OperadorController {

    private final CinemaUq cine = AppContext.getCine();

    @FXML private Label lblUsuario;

    /**
     * Cartelera
     */
    @FXML private TableView<Pelicula> tablaCartelera;
    @FXML private TableColumn<Pelicula, String> colCarCodigo;
    @FXML private TableColumn<Pelicula, String> colCarNombre;
    @FXML private TableColumn<Pelicula, String> colCarGenero;
    @FXML private TableColumn<Pelicula, String> colCarDuracion;
    @FXML private TableColumn<Pelicula, String> colCarClasificacion;

    /**
     * Clientes
     */
    @FXML private TextField txtCliId;
    @FXML private TextField txtCliNombre;
    @FXML private ComboBox<TipoCliente> cmbCliTipo;
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colCliId;
    @FXML private TableColumn<Cliente, String> colCliNombre;
    @FXML private TableColumn<Cliente, String> colCliTipo;

    @FXML
    private void initialize() {
        lblUsuario.setText("Sesión: " + AppContext.getUsuarioActual());

        colCarCodigo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCodigoPelicula())));
        colCarNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombrePelicula()));
        colCarGenero.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGenero()));
        colCarDuracion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getDuracionMinutos())));
        colCarClasificacion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getClasificacionPelicula())));

        colCliId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        colCliNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colCliTipo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getTipoCliente())));
        cmbCliTipo.setItems(FXCollections.observableArrayList(TipoCliente.values()));
        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, anterior, c) -> {
            if (c != null) {
                txtCliId.setText(c.getIdentificacion());
                txtCliNombre.setText(c.getNombre());
                cmbCliTipo.setValue(c.getTipoCliente());
            }
        });

        onActualizarCartelera();
        refrescarClientes();
    }

    /**
     * Cartelera
     */

    @FXML
    private void onActualizarCartelera() {
        tablaCartelera.setItems(FXCollections.observableArrayList(cine.consultarPeliculasDisponibles()));
    }

    /**
     * Clientes
     */

    @FXML
    private void onRegistrarCliente() {
        String id = txtCliId.getText().trim();
        String nombre = txtCliNombre.getText().trim();
        TipoCliente tipo = cmbCliTipo.getValue();
        if (id.isEmpty() || nombre.isEmpty() || tipo == null) {
            Alertas.advertencia("Completa identificación, nombre y tipo de cliente.");
            return;
        }
        Cliente nuevo = new Cliente(id, nombre, tipo);
        if (nuevo.registrarCliente(cine.getListCineUqCliente())) {
            refrescarClientes();
            onLimpiarCliente();
            Alertas.info("Cliente registrado correctamente.");
        } else {
            Alertas.error("Ya existe un cliente con la identificación " + id + ".");
        }
    }

    @FXML
    private void onAscenderCliente() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        TipoCliente nuevoTipo = cmbCliTipo.getValue();
        if (seleccionado == null || nuevoTipo == null) {
            Alertas.advertencia("Selecciona un cliente de la tabla y elige el nuevo tipo.");
            return;
        }
        seleccionado.ascenderTipoCliente(nuevoTipo);
        refrescarClientes();
    }

    @FXML
    private void onEliminarCliente() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alertas.advertencia("Selecciona un cliente de la tabla.");
            return;
        }
        if (Alertas.confirmar("¿Eliminar a " + seleccionado.getNombre() + "?")) {
            seleccionado.eliminarCliente(cine.getListCineUqCliente());
            refrescarClientes();
            onLimpiarCliente();
        }
    }

    @FXML
    private void onLimpiarCliente() {
        tablaClientes.getSelectionModel().clearSelection();
        txtCliId.clear();
        txtCliNombre.clear();
        cmbCliTipo.setValue(null);
    }

    private void refrescarClientes() {
        tablaClientes.setItems(FXCollections.observableArrayList(cine.getListCineUqCliente()));
        tablaClientes.refresh();
    }


    @FXML
    private void onCerrarSesion(ActionEvent evento) {
        AppContext.cerrarSesion();
        Navegador.cambiar((Node) evento.getSource(), "/login.fxml", "Cine UQ - Ingreso");
    }
}
