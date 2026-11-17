package uniquindio.edu.co.cineuqfx.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import uniquindio.edu.co.cineuqfx.Alertas;
import uniquindio.edu.co.cineuqfx.AppContext;
import uniquindio.edu.co.cineuqfx.Navegador;
import uniquindio.edu.co.cineuqfx.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Pantalla del administrador: gestiona películas, salas y funciones.
 * Este controlador NO tiene lógica de negocio: lee los campos, llama a CinemaUq
 * (carpeta model) y muestra el resultado.
 */
public class AdminController {

    private final CinemaUq cine = AppContext.getCine();

    @FXML private Label lblUsuario;

    /**
     * Peliculas
     */
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

    /**
     * Salas
     */
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

    /**
     * Funciones
     */
    @FXML private TextField txtFunCodigo;
    @FXML private ComboBox<Pelicula> cmbFunPelicula;
    @FXML private ComboBox<Sala> cmbFunSala;
    @FXML private DatePicker dpFunFecha;
    @FXML private TextField txtFunHora;
    @FXML private TextField txtFunPrecio;
    @FXML private ComboBox<EstadoFuncion> cmbFunEstado;
    @FXML private TableView<Funcion> tablaFunciones;
    @FXML private TableColumn<Funcion, String> colFunCodigo;
    @FXML private TableColumn<Funcion, String> colFunPelicula;
    @FXML private TableColumn<Funcion, String> colFunSala;
    @FXML private TableColumn<Funcion, String> colFunFecha;
    @FXML private TableColumn<Funcion, String> colFunHora;
    @FXML private TableColumn<Funcion, String> colFunPrecio;
    @FXML private TableColumn<Funcion, String> colFunEstado;

    /** JavaFX llama a este método solo, una vez cargado el FXML. */
    @FXML
    private void initialize() {
        lblUsuario.setText("Sesión: " + AppContext.getUsuarioActual());

        /**
         * Tabla pelicula
         */
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

        /**
         * tabla de sala
         */
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

        /**
         * Tabla de funciones
         */
        colFunCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigoFuncion()));
        colFunPelicula.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getPelicula() != null ? c.getValue().getPelicula().getNombrePelicula() : "N/A"));
        colFunSala.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getSala() != null ? c.getValue().getSala().getNombreSala() : "N/A"));
        colFunFecha.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getFecha() != null ? c.getValue().getFecha().toString() : ""));
        colFunHora.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getHora() != null ? c.getValue().getHora().toString() : ""));
        colFunPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.0f", c.getValue().getValorEntrada())));
        colFunEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstadoFuncion())));

        configurarCombosFuncion();

        tablaFunciones.getSelectionModel().selectedItemProperty().addListener((obs, anterior, f) -> {
            if (f != null) {
                txtFunCodigo.setText(f.getCodigoFuncion());
                txtFunCodigo.setDisable(true);
                cmbFunPelicula.setValue(f.getPelicula());
                cmbFunSala.setValue(f.getSala());
                dpFunFecha.setValue(f.getFecha());
                txtFunHora.setText(f.getHora() != null ? f.getHora().toString() : "");
                txtFunPrecio.setText(String.valueOf(f.getValorEntrada()));
                cmbFunEstado.setValue(f.getEstadoFuncion());
            }
        });

        refrescarPeliculas();
        refrescarSalas();
        refrescarFunciones();
    }

    private void configurarCombosFuncion() {
        cmbFunEstado.setItems(FXCollections.observableArrayList(EstadoFuncion.values()));
        cmbFunEstado.setValue(EstadoFuncion.PROGRAMADA);

        cmbFunPelicula.setConverter(new StringConverter<>() {
            @Override
            public String toString(Pelicula p) {
                return p == null ? "" : p.getNombrePelicula() + " [" + p.getGenero() + "]";
            }
            @Override
            public Pelicula fromString(String string) {
                return null;
            }
        });

        cmbFunSala.setConverter(new StringConverter<>() {
            @Override
            public String toString(Sala s) {
                return s == null ? "" : s.getNombreSala() + " (" + s.getTipoSala() + ")";
            }
            @Override
            public Sala fromString(String string) {
                return null;
            }
        });
    }

    /**
     * PELICULAS
     */

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
            cine.registrarPelicula(new Pelicula(nombre, codigo, genero, duracion, clasif));
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
        cmbFunPelicula.setItems(FXCollections.observableArrayList(cine.consultarPeliculasDisponibles()));
    }

    /**
     * SALAS
     */

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
            Sala nuevaSala = new Sala(codigo, nombre, capacidad, 0,
                    EstadoSala.DISPONIBLE, tipo, new ArrayList<>());
            // Generar los asientos de la sala automáticamente según capacidad o filas
            int filas = Math.max(1, asientos / 10);
            int cols = Math.max(1, (int) Math.ceil((double) asientos / filas));
            AppContext.generarAsientos(nuevaSala, filas, cols);

            cine.registrarSala(nuevaSala);
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
        cmbFunSala.setItems(FXCollections.observableArrayList(cine.getListCineUqSalas()));
    }

    /**
     * FUNCIONES
     */

    @FXML
    private void onRegistrarFuncion() {
        try {
            String codigo = leerTexto(txtFunCodigo, "El código de la función");
            if (Funcion.verificarFuncionExiste(codigo, cine.getListCineUqFunciones())) {
                throw new IllegalArgumentException("Ya existe una función con el código: " + codigo);
            }
            Pelicula pelicula = cmbFunPelicula.getValue();
            if (pelicula == null) {
                throw new IllegalArgumentException("Selecciona una película disponible.");
            }
            Sala sala = cmbFunSala.getValue();
            if (sala == null) {
                throw new IllegalArgumentException("Selecciona una sala.");
            }
            LocalDate fecha = dpFunFecha.getValue();
            if (fecha == null) {
                throw new IllegalArgumentException("Selecciona una fecha.");
            }
            LocalTime hora = leerHora(txtFunHora, "La hora (formato HH:mm, ej. 18:30)");
            double precio = leerDecimal(txtFunPrecio, "El valor de entrada");

            Funcion funcion = new Funcion(codigo, pelicula, sala, fecha, hora, precio, "Estándar");
            if (cmbFunEstado.getValue() != null) {
                funcion.setEstadoFuncion(cmbFunEstado.getValue());
            }

            cine.agregarFuncion(funcion, pelicula.getCodigoPelicula(), sala.getCodigoSala());
            refrescarFunciones();
            onLimpiarFuncion();
            Alertas.info("Función programada correctamente.");
        } catch (Exception e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onCambiarEstadoFuncion() {
        Funcion seleccionada = tablaFunciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alertas.advertencia("Selecciona una función de la tabla.");
            return;
        }
        EstadoFuncion nuevoEstado = cmbFunEstado.getValue();
        if (nuevoEstado == null) {
            Alertas.advertencia("Selecciona un estado en el selector.");
            return;
        }
        seleccionada.setEstadoFuncion(nuevoEstado);
        refrescarFunciones();
        Alertas.info("Estado de la función actualizado a: " + nuevoEstado);
    }

    @FXML
    private void onLimpiarFuncion() {
        tablaFunciones.getSelectionModel().clearSelection();
        txtFunCodigo.clear();
        txtFunCodigo.setDisable(false);
        cmbFunPelicula.setValue(null);
        cmbFunSala.setValue(null);
        dpFunFecha.setValue(null);
        txtFunHora.clear();
        txtFunPrecio.clear();
        cmbFunEstado.setValue(EstadoFuncion.PROGRAMADA);
    }

    private void refrescarFunciones() {
        tablaFunciones.setItems(FXCollections.observableArrayList(cine.getListCineUqFunciones()));
        tablaFunciones.refresh();
    }

    /**
     * sesion y utilidades
     * @param evento
     */
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

    private double leerDecimal(TextField campo, String nombreCampo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " es obligatorio.");
        }
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(nombreCampo + " debe ser un número decimal válido.");
        }
    }

    private LocalTime leerHora(TextField campo, String nombreCampo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " es obligatorio.");
        }
        try {
            return LocalTime.parse(texto);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(nombreCampo + " no tiene un formato válido (HH:mm, ej. 14:30 o 20:00).");
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
