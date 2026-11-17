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
import uniquindio.edu.co.cineuqfx.model.*;

import java.util.ArrayList;

/**
 * Pantalla del administrador: gestiona películas y salas.
 * Este controlador NO tiene lógica de negocio: lee los campos, llama a CinemaUq
 * (carpeta model) y muestra el resultado.
 */
public class AdminController {

    private final CinemaUq cine = AppContext.getCine();

    @FXML private Label lblUsuario;

    // ---------- Películas ----------
    @FXML private TextField txtPelCodigo;
    @FXML private TextField txtPelNombre;
    @FXML private TextField txtPelGenero;
    @FXML private TextField txtPelDuracion;
    @FXML private ComboBox<ClasificacionPelicula> cmbPelClasificacion;
    @FXML private TableView<Pelicula> tablaPeliculas;
    @FXML private TableColumn<Pelicula, String> colPelCodigo;
    @FXML private TableColumn<Pelicula, String> colPelNombre;
    @FXML private TableColumn<Pelicula, String> colPelGenero;
    @FXML private TableColumn<Pelicula, String> colPelDuracion;
    @FXML private TableColumn<Pelicula, String> colPelClasificacion;
    @FXML private TableColumn<Pelicula, String> colPelEstado;

    // ---------- Salas ----------
    @FXML private TextField txtSalaCodigo;
    @FXML private TextField txtSalaNombre;
    @FXML private TextField txtSalaCapacidad;
    @FXML private TextField txtSalaAsientos;
    @FXML private ComboBox<TipoSala> cmbSalaTipo;
    @FXML private TableView<Sala> tablaSalas;
    @FXML private TableColumn<Sala, String> colSalaCodigo;
    @FXML private TableColumn<Sala, String> colSalaNombre;
    @FXML private TableColumn<Sala, String> colSalaCapacidad;
    @FXML private TableColumn<Sala, String> colSalaAsientos;
    @FXML private TableColumn<Sala, String> colSalaTipo;
    @FXML private TableColumn<Sala, String> colSalaEstado;

    /** JavaFX llama a este método solo, una vez cargado el FXML. */
    @FXML
    private void initialize() {
        lblUsuario.setText("Sesión: " + AppContext.getUsuarioActual());

        // --- Tabla de películas ---
        colPelCodigo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCodigoPelicula())));
        colPelNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombrePelicula()));
        colPelGenero.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGenero()));
        colPelDuracion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getDuracionMinutos())));
        colPelClasificacion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getClasificacionPelicula())));
        colPelEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstadoPelicula())));
        cmbPelClasificacion.setItems(FXCollections.observableArrayList(ClasificacionPelicula.values()));
        tablaPeliculas.getSelectionModel().selectedItemProperty().addListener((obs, anterior, p) -> {
            if (p != null) {
                txtPelCodigo.setText(String.valueOf(p.getCodigoPelicula()));
                txtPelCodigo.setDisable(true); // el código no se modifica
                txtPelNombre.setText(p.getNombrePelicula());
                txtPelGenero.setText(p.getGenero());
                txtPelDuracion.setText(String.valueOf(p.getDuracionMinutos()));
                cmbPelClasificacion.setValue(p.getClasificacionPelicula());
            }
        });

        // --- Tabla de salas ---
        colSalaCodigo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCodigoSala())));
        colSalaNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombreSala()));
        colSalaCapacidad.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCapacidadSala())));
        colSalaAsientos.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getnAsientos())));
        colSalaTipo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getTipoSala())));
        colSalaEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstadoSala())));
        cmbSalaTipo.setItems(FXCollections.observableArrayList(TipoSala.values()));
        tablaSalas.getSelectionModel().selectedItemProperty().addListener((obs, anterior, s) -> {
            if (s != null) {
                txtSalaCodigo.setText(String.valueOf(s.getCodigoSala()));
                txtSalaCodigo.setDisable(true);
                txtSalaNombre.setText(s.getNombreSala());
                txtSalaCapacidad.setText(String.valueOf(s.getCapacidadSala()));
                txtSalaAsientos.setText(String.valueOf(s.getnAsientos()));
                cmbSalaTipo.setValue(s.getTipoSala());
            }
        });

        refrescarPeliculas();
        refrescarSalas();
    }

    // =====================================================================
    //  PELÍCULAS
    // =====================================================================

    @FXML
    private void onRegistrarPelicula() {
        try {
            int codigo = leerEntero(txtPelCodigo, "El código");
            int duracion = leerEntero(txtPelDuracion, "La duración");
            String nombre = leerTexto(txtPelNombre, "El nombre");
            String genero = leerTexto(txtPelGenero, "El género");
            ClasificacionPelicula clasif = cmbPelClasificacion.getValue();
            if (clasif == null) {
                throw new IllegalArgumentException("Selecciona una clasificación.");
            }
            cine.registrarPelicula(new Pelicula(nombre, codigo, genero, duracion,
                    EstadoPelicula.DISPONIBLE, clasif, new ArrayList<>()));
            refrescarPeliculas();
            onLimpiarPelicula();
            Alertas.info("Película registrada correctamente.");
        } catch (Exception e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onModificarPelicula() {
        try {
            int codigo = leerEntero(txtPelCodigo, "El código");
            int duracion = leerEntero(txtPelDuracion, "La duración");
            String nombre = leerTexto(txtPelNombre, "El nombre");
            String genero = leerTexto(txtPelGenero, "El género");
            ClasificacionPelicula clasif = cmbPelClasificacion.getValue();
            if (clasif == null) {
                throw new IllegalArgumentException("Selecciona una clasificación.");
            }
            cine.modificarPelicula(codigo, nombre, genero, duracion, clasif);
            refrescarPeliculas();
            Alertas.info("Película actualizada.");
        } catch (Exception e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onDeshabilitarPelicula() {
        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alertas.advertencia("Selecciona una película de la tabla.");
            return;
        }
        try {
            cine.deshabilitarPelicula(seleccionada.getCodigoPelicula());
            refrescarPeliculas();
        } catch (Exception e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onHabilitarPelicula() {
        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alertas.advertencia("Selecciona una película de la tabla.");
            return;
        }
        seleccionada.habilitar();
        refrescarPeliculas();
    }

    @FXML
    private void onLimpiarPelicula() {
        tablaPeliculas.getSelectionModel().clearSelection();
        txtPelCodigo.clear();
        txtPelCodigo.setDisable(false);
        txtPelNombre.clear();
        txtPelGenero.clear();
        txtPelDuracion.clear();
        cmbPelClasificacion.setValue(null);
    }

    private void refrescarPeliculas() {
        tablaPeliculas.setItems(FXCollections.observableArrayList(cine.getListCineUqPeliculas()));
        tablaPeliculas.refresh();
    }

    // =====================================================================
    //  SALAS
    // =====================================================================

    @FXML
    private void onRegistrarSala() {
        try {
            int codigo = leerEntero(txtSalaCodigo, "El código");
            int capacidad = leerEntero(txtSalaCapacidad, "La capacidad");
            int asientos = leerEntero(txtSalaAsientos, "El número de asientos");
            String nombre = leerTexto(txtSalaNombre, "El nombre");
            TipoSala tipo = cmbSalaTipo.getValue();
            if (tipo == null) {
                throw new IllegalArgumentException("Selecciona un tipo de sala.");
            }
            cine.registrarSala(new Sala(codigo, nombre, capacidad, asientos,
                    EstadoSala.DISPONIBLE, tipo, new ArrayList<>()));
            refrescarSalas();
            onLimpiarSala();
            Alertas.info("Sala registrada correctamente.");
        } catch (Exception e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onModificarSala() {
        try {
            int codigo = leerEntero(txtSalaCodigo, "El código");
            int capacidad = leerEntero(txtSalaCapacidad, "La capacidad");
            int asientos = leerEntero(txtSalaAsientos, "El número de asientos");
            String nombre = leerTexto(txtSalaNombre, "El nombre");
            TipoSala tipo = cmbSalaTipo.getValue();
            if (tipo == null) {
                throw new IllegalArgumentException("Selecciona un tipo de sala.");
            }
            cine.modificarSala(codigo, nombre, capacidad, asientos, tipo);
            refrescarSalas();
            Alertas.info("Sala actualizada.");
        } catch (Exception e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onDeshabilitarSala() {
        Sala seleccionada = tablaSalas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alertas.advertencia("Selecciona una sala de la tabla.");
            return;
        }
        try {
            cine.deshabilitarSala(seleccionada.getCodigoSala());
            refrescarSalas();
        } catch (Exception e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onHabilitarSala() {
        Sala seleccionada = tablaSalas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alertas.advertencia("Selecciona una sala de la tabla.");
            return;
        }
        seleccionada.habilitar();
        refrescarSalas();
    }

    @FXML
    private void onLimpiarSala() {
        tablaSalas.getSelectionModel().clearSelection();
        txtSalaCodigo.clear();
        txtSalaCodigo.setDisable(false);
        txtSalaNombre.clear();
        txtSalaCapacidad.clear();
        txtSalaAsientos.clear();
        cmbSalaTipo.setValue(null);
    }

    private void refrescarSalas() {
        tablaSalas.setItems(FXCollections.observableArrayList(cine.getListCineUqSalas()));
        tablaSalas.refresh();
    }

    // =====================================================================
    //  Sesión y utilidades
    // =====================================================================

    @FXML
    private void onCerrarSesion(ActionEvent evento) {
        AppContext.cerrarSesion();
        Navegador.cambiar((Node) evento.getSource(), "/login.fxml", "Cine UQ - Ingreso");
    }

    private int leerEntero(TextField campo, String nombreCampo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " es obligatorio.");
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(nombreCampo + " debe ser un número entero.");
        }
    }

    private String leerTexto(TextField campo, String nombreCampo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " es obligatorio.");
        }
        return texto;
    }
}
