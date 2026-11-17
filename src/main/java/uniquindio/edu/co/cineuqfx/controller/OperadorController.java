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
import java.util.ArrayList;
import java.util.List;

/**
 * Pantalla del operador: consulta la cartelera, gestiona clientes,
 * realiza ventas de entradas con cálculo de tarifas y cancela ventas.
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
    @FXML private ComboBox<String> cmbCliBeneficio;
    @FXML private javafx.scene.layout.VBox boxMembresia;
    @FXML private ComboBox<NivelMembresia> cmbCliNivelMembresia;
    @FXML private TextField txtCliPuntos;
    @FXML private javafx.scene.layout.VBox boxConvenio;
    @FXML private TextField txtCliEntidad;
    @FXML private ComboBox<TipoEntidad> cmbCliTipoEntidad;
    @FXML private TextField txtCliPorcentajeConvenio;
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colCliId;
    @FXML private TableColumn<Cliente, String> colCliNombre;
    @FXML private TableColumn<Cliente, String> colCliTipo;
    @FXML private TableColumn<Cliente, String> colCliBeneficio;

    /**
     * Venta de Entradas
     */
    @FXML private ComboBox<Funcion> cmbVentaFuncion;
    @FXML private ComboBox<Cliente> cmbVentaCliente;
    @FXML private Spinner<Integer> spnCantidadEntradas;
    @FXML private ComboBox<Asiento> cmbVentaAsiento;
    @FXML private Label lblAsientosAsignados;
    @FXML private javafx.scene.layout.VBox boxCanjePuntos;
    @FXML private Label lblPuntosDisponibles;
    @FXML private CheckBox chkUsarPuntos;
    @FXML private Label lblInfoCanjePuntos;
    @FXML private ComboBox<String> cmbVentaMetodoPago;
    @FXML private Label lblTarifaCliente;
    @FXML private Label lblTotalPagar;
    @FXML private Label lblInfoMetodo;

    /**
     * Historial de Ventas
     */
    @FXML private TableView<VentaEntrada> tablaVentas;
    @FXML private TableColumn<VentaEntrada, String> colVenCodigo;
    @FXML private TableColumn<VentaEntrada, String> colVenCliente;
    @FXML private TableColumn<VentaEntrada, String> colVenPelicula;
    @FXML private TableColumn<VentaEntrada, String> colVenSala;
    @FXML private TableColumn<VentaEntrada, String> colVenAsiento;
    @FXML private TableColumn<VentaEntrada, String> colVenFecha;
    @FXML private TableColumn<VentaEntrada, String> colVenHora;
    @FXML private TableColumn<VentaEntrada, String> colVenValor;
    @FXML private TableColumn<VentaEntrada, String> colVenEstado;

    @FXML
    private void initialize() {
        lblUsuario.setText("Sesión: " + AppContext.getUsuarioActual());

        // Configuración Cartelera
        colCarCodigo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCodigoPelicula())));
        colCarNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombrePelicula()));
        colCarGenero.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGenero()));
        colCarDuracion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getDuracionMinutos())));
        colCarClasificacion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getClasificacionPelicula())));

        // Configuración Clientes
        colCliId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        colCliNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colCliTipo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getTipoCliente())));
        colCliBeneficio.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcionBeneficio()));
        cmbCliTipo.setItems(FXCollections.observableArrayList(TipoCliente.values()));

        configurarFormularioBeneficios();

        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, anterior, c) -> {
            if (c != null) {
                txtCliId.setText(c.getIdentificacion());
                txtCliNombre.setText(c.getNombre());
                cmbCliTipo.setValue(c.getTipoCliente());

                Beneficio b = c.getBeneficio();
                if (b instanceof Membresia m) {
                    cmbCliBeneficio.setValue("Membresía");
                    cmbCliNivelMembresia.setValue(m.getNivel());
                    txtCliPuntos.setText(String.valueOf(m.getPuntosAcumulados()));
                } else if (b instanceof Convenio conv) {
                    cmbCliBeneficio.setValue("Convenio");
                    txtCliEntidad.setText(conv.getNombreEntidad());
                    cmbCliTipoEntidad.setValue(conv.getTipoEntidad());
                    txtCliPorcentajeConvenio.setText(String.format("%.0f", conv.getPorcentajeDescuento()));
                } else {
                    cmbCliBeneficio.setValue("Sin Beneficio");
                }
            }
        });

        // Configuración Venta de Entradas
        configurarModuloVentas();

        // Configuración Tabla de Ventas
        configurarTablaVentas();

        onActualizarCartelera();
        refrescarClientes();
        refrescarModuloVentas();
        refrescarTablaVentas();
    }

    private void configurarModuloVentas() {
        cmbVentaFuncion.setConverter(new StringConverter<>() {
            @Override
            public String toString(Funcion f) {
                if (f == null) return "";
                String pel = f.getPelicula() != null ? f.getPelicula().getNombrePelicula() : "Película";
                String sal = f.getSala() != null ? f.getSala().getNombreSala() : "Sala";
                return f.getCodigoFuncion() + " - " + pel + " (" + sal + ") " + f.getHora();
            }
            @Override
            public Funcion fromString(String string) {
                return null;
            }
        });

        cmbVentaCliente.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente c) {
                return c == null ? "" : c.getNombre() + " (CC: " + c.getIdentificacion() + " - " + c.getTipoCliente() + ")";
            }
            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });

        cmbVentaAsiento.setConverter(new StringConverter<>() {
            @Override
            public String toString(Asiento a) {
                return a == null ? "" : "Asiento " + a.getCodigoAsiento() + " (" + a.getEstado() + ")";
            }
            @Override
            public Asiento fromString(String string) {
                return null;
            }
        });

        cmbVentaMetodoPago.setItems(FXCollections.observableArrayList(
                "Efectivo (9% descuento - Mín. $15.000)",
                "Tarjeta (5% recargo - Mín. $100.000)",
                "Transferencia (7% recargo - Mín. $50.000)"
        ));

        // Inicializar panel de canje de puntos de membresía
        boxCanjePuntos.setVisible(false);
        boxCanjePuntos.setManaged(false);

        // Listener para canjear puntos de membresía
        chkUsarPuntos.selectedProperty().addListener((obs, ant, nuevo) -> {
            actualizarResumenPago();
        });

        // Configurar selector de cantidad de entradas
        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spnCantidadEntradas.setValueFactory(factory);
        spnCantidadEntradas.valueProperty().addListener((obs, ant, cant) -> {
            actualizarAsientosAsignados();
            actualizarResumenPago();
        });

        cmbVentaAsiento.getSelectionModel().selectedItemProperty().addListener((obs, ant, asiento) -> {
            actualizarAsientosAsignados();
        });

        cmbVentaFuncion.getSelectionModel().selectedItemProperty().addListener((obs, ant, fun) -> {
            cargarAsientosDeFuncion(fun);
            actualizarAsientosAsignados();
            actualizarResumenPago();
        });

        cmbVentaCliente.getSelectionModel().selectedItemProperty().addListener((obs, ant, cli) -> {
            actualizarPanelCanjePuntos(cli);
            actualizarResumenPago();
        });

        cmbVentaMetodoPago.getSelectionModel().selectedItemProperty().addListener((obs, ant, met) -> {
            actualizarResumenPago();
        });
    }

    private void actualizarPanelCanjePuntos(Cliente cliente) {
        if (cliente != null && cliente.getBeneficio() instanceof Membresia m) {
            boxCanjePuntos.setVisible(true);
            boxCanjePuntos.setManaged(true);
            int puntos = m.getPuntosAcumulados();
            lblPuntosDisponibles.setText(String.format("Puntos de Membresía: %d pts (%s)", puntos, m.getNivel()));

            if (puntos >= 100) {
                chkUsarPuntos.setDisable(false);
                int bloques = puntos / 100;
                int puntosCanjeables = bloques * 100;
                double ahorro = m.calcularDescuentoPorPuntos();
                if (puntos > 1000) {
                    lblInfoCanjePuntos.setText(String.format(
                            "Tienes %d puntos acumulados. Puedes redimir %d pts y recibir $%,.0f COP de descuento ($500 c/100 pts + $2.000 COP extra por superar 1000 pts).",
                            puntos, puntosCanjeables, ahorro));
                } else {
                    lblInfoCanjePuntos.setText(String.format(
                            "Tienes %d puntos acumulados. Puedes redimir %d pts y recibir $%,.0f COP de descuento ($500 c/100 pts).",
                            puntos, puntosCanjeables, ahorro));
                }
            } else {
                chkUsarPuntos.setSelected(false);
                chkUsarPuntos.setDisable(true);
                lblInfoCanjePuntos.setText(String.format(
                        "Tienes %d pts. Se requiere un mínimo de 100 puntos para redimir descuentos.", puntos));
            }
        } else {
            chkUsarPuntos.setSelected(false);
            boxCanjePuntos.setVisible(false);
            boxCanjePuntos.setManaged(false);
            lblInfoCanjePuntos.setText("");
        }
    }

    private void cargarAsientosDeFuncion(Funcion funcion) {
        if (funcion != null && funcion.getSala() != null) {
            List<Asiento> disponibles = new ArrayList<>();
            for (Asiento a : funcion.getSala().getListSalaAsientos()) {
                if (a.getEstado() == EstadoAsiento.DISPONIBLE) {
                    disponibles.add(a);
                }
            }
            cmbVentaAsiento.setItems(FXCollections.observableArrayList(disponibles));
            if (!disponibles.isEmpty()) {
                cmbVentaAsiento.setValue(disponibles.get(0));
            } else {
                cmbVentaAsiento.setValue(null);
            }
        } else {
            cmbVentaAsiento.setItems(FXCollections.observableArrayList());
            cmbVentaAsiento.setValue(null);
        }
    }

    private List<Asiento> obtenerAsientosParaVenta(int cantidad) {
        Funcion funcion = cmbVentaFuncion.getValue();
        if (funcion == null || funcion.getSala() == null) {
            return new ArrayList<>();
        }
        List<Asiento> disponibles = new ArrayList<>();
        for (Asiento a : funcion.getSala().getListSalaAsientos()) {
            if (a.getEstado() == EstadoAsiento.DISPONIBLE) {
                disponibles.add(a);
            }
        }
        if (disponibles.isEmpty()) {
            return new ArrayList<>();
        }

        Asiento inicio = cmbVentaAsiento.getValue();
        List<Asiento> seleccionados = new ArrayList<>();
        int indiceInicio = (inicio != null) ? disponibles.indexOf(inicio) : 0;
        if (indiceInicio < 0) indiceInicio = 0;

        for (int i = indiceInicio; i < disponibles.size() && seleccionados.size() < cantidad; i++) {
            seleccionados.add(disponibles.get(i));
        }
        // Si no alcanzaron desde el índice hacia el final, tomar desde el inicio
        if (seleccionados.size() < cantidad) {
            for (int i = 0; i < indiceInicio && seleccionados.size() < cantidad; i++) {
                if (!seleccionados.contains(disponibles.get(i))) {
                    seleccionados.add(disponibles.get(i));
                }
            }
        }
        return seleccionados;
    }

    private void actualizarAsientosAsignados() {
        int cantidad = (spnCantidadEntradas.getValue() != null) ? spnCantidadEntradas.getValue() : 1;
        List<Asiento> asignados = obtenerAsientosParaVenta(cantidad);
        if (asignados.isEmpty()) {
            lblAsientosAsignados.setText("Asientos asignados: (Ninguno disponible)");
        } else {
            StringBuilder sb = new StringBuilder("Asientos asignados (" + asignados.size() + "/" + cantidad + "): ");
            for (int i = 0; i < asignados.size(); i++) {
                sb.append(asignados.get(i).getCodigoAsiento());
                if (i < asignados.size() - 1) sb.append(", ");
            }
            if (asignados.size() < cantidad) {
                sb.append(" ⚠️ Solo hay ").append(asignados.size()).append(" disponibles");
            }
            lblAsientosAsignados.setText(sb.toString());
        }
    }

    private void configurarFormularioBeneficios() {
        cmbCliBeneficio.setItems(FXCollections.observableArrayList("Sin Beneficio", "Membresía", "Convenio"));
        cmbCliBeneficio.setValue("Sin Beneficio");

        cmbCliNivelMembresia.setItems(FXCollections.observableArrayList(NivelMembresia.values()));
        cmbCliNivelMembresia.setValue(NivelMembresia.BRONCE);

        cmbCliTipoEntidad.setItems(FXCollections.observableArrayList(TipoEntidad.values()));
        cmbCliTipoEntidad.setValue(TipoEntidad.UNIVERSIDAD);

        actualizarVisibilidadPanelesBeneficio("Sin Beneficio");

        cmbCliBeneficio.getSelectionModel().selectedItemProperty().addListener((obs, ant, nuevo) -> {
            actualizarVisibilidadPanelesBeneficio(nuevo);
        });
    }

    private void actualizarVisibilidadPanelesBeneficio(String tipoBeneficio) {
        boolean esMembresia = "Membresía".equals(tipoBeneficio);
        boolean esConvenio = "Convenio".equals(tipoBeneficio);

        boxMembresia.setVisible(esMembresia);
        boxMembresia.setManaged(esMembresia);

        boxConvenio.setVisible(esConvenio);
        boxConvenio.setManaged(esConvenio);
    }

    private void actualizarResumenPago() {
        Funcion funcion = cmbVentaFuncion.getValue();
        Cliente cliente = cmbVentaCliente.getValue();
        String metodo = cmbVentaMetodoPago.getValue();
        int cantidad = (spnCantidadEntradas.getValue() != null) ? spnCantidadEntradas.getValue() : 1;

        if (funcion == null || cliente == null) {
            lblTarifaCliente.setText("Tarifa según tipo cliente / beneficio: $0");
            lblTotalPagar.setText("Total final: $0");
            lblInfoMetodo.setText("");
            return;
        }

        // Calcular tarifa según tipo de cliente
        double valorBase = funcion.getValorEntrada();
        double tarifaCliente;
        if (cliente.getTipoCliente() == TipoCliente.ESTUDIANTE) {
            tarifaCliente = 12000.0;
        } else if (cliente.getTipoCliente() == TipoCliente.NINO) {
            tarifaCliente = 10000.0;
        } else if (cliente.getTipoCliente() == TipoCliente.ADULTO_MAYOR) {
            tarifaCliente = 11000.0;
        } else {
            tarifaCliente = valorBase;
        }

        // Aplicar beneficio si el cliente lo tiene (Membresía, Convenio o Descuento)
        double tarifaConBeneficio = cliente.calcularValorConBeneficio(tarifaCliente);
        double subtotal = tarifaConBeneficio * cantidad;

        // Descuento por canje de puntos de membresía
        double descuentoPuntos = 0.0;
        if (chkUsarPuntos.isSelected() && cliente.getBeneficio() instanceof Membresia m && m.getPuntosAcumulados() >= 100) {
            descuentoPuntos = Math.min(m.calcularDescuentoPorPuntos(), subtotal);
        }
        double subtotalFinal = Math.max(0.0, subtotal - descuentoPuntos);

        StringBuilder sbTarifa = new StringBuilder();
        if (cliente.getBeneficio() != null) {
            sbTarifa.append(String.format("%d x $%,.0f COP (%s) = Subtotal: $%,.0f COP",
                    cantidad, tarifaConBeneficio, cliente.getDescripcionBeneficio(), subtotal));
        } else {
            sbTarifa.append(String.format("%d x $%,.0f COP (%s) = Subtotal: $%,.0f COP",
                    cantidad, tarifaConBeneficio, cliente.getTipoCliente(), subtotal));
        }
        if (descuentoPuntos > 0) {
            sbTarifa.append(String.format(" | Canje Puntos: -$%,.0f COP -> Subtotal neto: $%,.0f COP",
                    descuentoPuntos, subtotalFinal));
        }
        lblTarifaCliente.setText(sbTarifa.toString());

        if (metodo == null) {
            lblTotalPagar.setText(String.format("Total a pagar: $%,.0f COP", subtotalFinal));
            lblInfoMetodo.setText("Selecciona un método de pago.");
            return;
        }

        try {
            MetodoPago mp = obtenerMetodoPago(metodo);
            double totalFinal = mp.calcularValorFinal(subtotalFinal);
            lblTotalPagar.setText(String.format("Total final a pagar: $%,.0f COP", totalFinal));
            lblInfoMetodo.setText("Condición de pago cumplida exitosamente.");
        } catch (SaldoInsuficienteException e) {
            lblTotalPagar.setText("Monto no permitido");
            lblInfoMetodo.setText("⚠️ " + e.getMessage());
        }
    }

    private MetodoPago obtenerMetodoPago(String seleccion) {
        if (seleccion.startsWith("Efectivo")) {
            return new Efectivo();
        } else if (seleccion.startsWith("Tarjeta")) {
            return new Tarjeta();
        } else {
            return new Transferencia();
        }
    }

    private void configurarTablaVentas() {
        colVenCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigoVentaEntrada()));
        colVenCliente.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getCliente() != null ? c.getValue().getCliente().getNombre() : "N/A"));
        colVenPelicula.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getFuncion() != null && c.getValue().getFuncion().getPelicula() != null
                        ? c.getValue().getFuncion().getPelicula().getNombrePelicula() : "N/A"));
        colVenSala.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getFuncion() != null && c.getValue().getFuncion().getSala() != null
                        ? c.getValue().getFuncion().getSala().getNombreSala() : "N/A"));
        colVenAsiento.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getAsiento() != null ? c.getValue().getAsiento().getCodigoAsiento() : "N/A"));
        colVenFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaVenta()));
        colVenHora.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHoraVenta()));
        colVenValor.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.0f", c.getValue().getValorPagado())));
        colVenEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstado())));
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

        try {
            Beneficio beneficio = null;
            int codBeneficio = (int) (System.currentTimeMillis() % 100000);
            String tipoBeneficio = cmbCliBeneficio.getValue();

            if ("Membresía".equals(tipoBeneficio)) {
                NivelMembresia nivel = cmbCliNivelMembresia.getValue();
                if (nivel == null) nivel = NivelMembresia.BRONCE;
                int puntos = 0;
                String puntosStr = txtCliPuntos.getText().trim();
                if (!puntosStr.isEmpty()) {
                    try {
                        puntos = Integer.parseInt(puntosStr);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Los puntos de membresía deben ser un número entero.");
                    }
                }
                beneficio = new Membresia(codBeneficio, "Membresía " + nivel, puntos, nivel);

            } else if ("Convenio".equals(tipoBeneficio)) {
                String entidad = txtCliEntidad.getText().trim();
                if (entidad.isEmpty()) {
                    throw new IllegalArgumentException("Escribe el nombre de la entidad en convenio.");
                }
                TipoEntidad tipoEnt = cmbCliTipoEntidad.getValue();
                if (tipoEnt == null) tipoEnt = TipoEntidad.UNIVERSIDAD;
                double porcentaje = 0.0;
                String pctStr = txtCliPorcentajeConvenio.getText().trim();
                try {
                    porcentaje = Double.parseDouble(pctStr);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("El porcentaje de convenio debe ser un número válido.");
                }
                beneficio = new Convenio(codBeneficio, "Convenio " + entidad, entidad, tipoEnt, porcentaje);
            }

            new Cliente(id, nombre, tipo, beneficio).registrarCliente(cine.getListCineUqCliente());
            refrescarClientes();
            refrescarModuloVentas();
            onLimpiarCliente();
            Alertas.info("Cliente registrado correctamente con su beneficio correspondiente.");
        } catch (RuntimeException e) {
            Alertas.error(e.getMessage());
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
        refrescarModuloVentas();
        actualizarResumenPago();
    }

    @FXML
    private void onEliminarCliente() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alertas.advertencia("Selecciona un cliente de la tabla.");
            return;
        }
        if (Alertas.confirmar("¿Eliminar a " + seleccionado.getNombre() + "?")) {
            try {
                seleccionado.eliminarCliente(cine.getListCineUqCliente());
                refrescarClientes();
                refrescarModuloVentas();
                onLimpiarCliente();
            } catch (RuntimeException e) {
                Alertas.error(e.getMessage());
            }
        }
    }

    @FXML
    private void onLimpiarCliente() {
        tablaClientes.getSelectionModel().clearSelection();
        txtCliId.clear();
        txtCliNombre.clear();
        cmbCliTipo.setValue(null);
        cmbCliBeneficio.setValue("Sin Beneficio");
        txtCliPuntos.clear();
        txtCliEntidad.clear();
        txtCliPorcentajeConvenio.clear();
        cmbCliNivelMembresia.setValue(NivelMembresia.BRONCE);
        cmbCliTipoEntidad.setValue(TipoEntidad.UNIVERSIDAD);
    }

    private void refrescarClientes() {
        tablaClientes.setItems(FXCollections.observableArrayList(cine.getListCineUqCliente()));
        tablaClientes.refresh();
    }

    /**
     * VENTA DE ENTRADAS
     */

    @FXML
    private void onRealizarVenta() {
        Funcion funcion = cmbVentaFuncion.getValue();
        Cliente cliente = cmbVentaCliente.getValue();
        String metodoStr = cmbVentaMetodoPago.getValue();
        int cantidad = (spnCantidadEntradas.getValue() != null) ? spnCantidadEntradas.getValue() : 1;

        if (funcion == null) {
            Alertas.advertencia("Selecciona una función programada.");
            return;
        }
        if (cliente == null) {
            Alertas.advertencia("Selecciona un cliente.");
            return;
        }
        if (cantidad <= 0) {
            Alertas.advertencia("La cantidad de entradas debe ser al menos 1.");
            return;
        }
        if (metodoStr == null) {
            Alertas.advertencia("Selecciona un método de pago.");
            return;
        }

        List<Asiento> asientosParaVender = obtenerAsientosParaVenta(cantidad);
        if (asientosParaVender.size() < cantidad) {
            Alertas.error("No hay suficientes asientos disponibles. Solo quedan " + asientosParaVender.size() + " asientos disponibles.");
            return;
        }

        try {
            // Validar que todos los asientos asignados estén disponibles
            for (Asiento a : asientosParaVender) {
                a.verificarAsientoSeleccionadoDisponible();
            }

            // Calcular tarifa base por entrada según tipo de cliente y beneficio
            double tarifaBaseUnitaria = new VentaEntrada("TEMP", cliente, asientosParaVender.get(0), funcion)
                    .calcularValorTotalEntrada(funcion.getValorEntrada());
            double tarifaConBeneficioUnitaria = cliente.calcularValorConBeneficio(tarifaBaseUnitaria);
            double subtotal = tarifaConBeneficioUnitaria * cantidad;

            // Descuento por canje de puntos de membresía
            boolean usoPuntos = chkUsarPuntos.isSelected() && cliente.getBeneficio() instanceof Membresia;
            Membresia membresiaCliente = (cliente.getBeneficio() instanceof Membresia) ? (Membresia) cliente.getBeneficio() : null;
            double descuentoPuntos = 0.0;
            int puntosCanjeados = 0;
            if (usoPuntos && membresiaCliente != null && membresiaCliente.getPuntosAcumulados() >= 100) {
                puntosCanjeados = (membresiaCliente.getPuntosAcumulados() / 100) * 100;
                descuentoPuntos = Math.min(membresiaCliente.calcularDescuentoPorPuntos(), subtotal);
            }
            double subtotalFinal = Math.max(0.0, subtotal - descuentoPuntos);

            // Método de pago aplicado al subtotal final de la compra
            MetodoPago metodoPago = obtenerMetodoPago(metodoStr);
            double totalFinal = metodoPago.calcularValorFinal(subtotalFinal);

            // Procesar pago total
            if (metodoPago instanceof IPagarValorTotal pagar) {
                pagar.pagarValorTotal(totalFinal);
            }

            // Descontar los puntos canjeados del saldo del cliente
            if (puntosCanjeados > 0 && membresiaCliente != null) {
                membresiaCliente.canjearPuntos(puntosCanjeados);
            }

            // Procesar y registrar cada venta y asiento
            String fechaHoy = LocalDate.now().toString();
            String horaActual = LocalTime.now().withNano(0).toString();
            double valorPorEntrada = totalFinal / cantidad;
            long timestampBase = System.currentTimeMillis() % 1000000;
            StringBuilder listaCodigosAsientos = new StringBuilder();

            for (int i = 0; i < asientosParaVender.size(); i++) {
                Asiento a = asientosParaVender.get(i);
                String codVenta = "V" + timestampBase + (cantidad > 1 ? ("-" + (i + 1)) : "");
                VentaEntrada venta = new VentaEntrada(codVenta, cliente, a, funcion);
                venta.procesarVenta(fechaHoy, horaActual, funcion.getValorEntrada());
                venta.setValorPagado(valorPorEntrada);

                funcion.agregarVenta(venta);
                cine.agregarVentaEntrada(venta);

                listaCodigosAsientos.append(a.getCodigoAsiento());
                if (i < asientosParaVender.size() - 1) listaCodigosAsientos.append(", ");
            }

            // Acumular puntos si el cliente tiene Membresía (10 puntos por cada entrada comprada)
            String mensajeMembresia = "";
            if (membresiaCliente != null) {
                int puntosGanados = 10 * cantidad;
                membresiaCliente.acumularPuntos(puntosGanados);
                if (puntosCanjeados > 0) {
                    mensajeMembresia = String.format("\n• Canje de Puntos: -%d pts (Ahorro: -$%,.0f COP)" +
                                    "\n• Puntos Membresía ganados hoy: +%d pts" +
                                    "\n• Saldo actual de Puntos: %d pts",
                            puntosCanjeados, descuentoPuntos, puntosGanados, membresiaCliente.getPuntosAcumulados());
                } else {
                    mensajeMembresia = String.format("\n• Puntos Membresía ganados hoy: +%d pts (Saldo actual: %d pts)",
                            puntosGanados, membresiaCliente.getPuntosAcumulados());
                }
            }

            // Factura
            if (metodoPago instanceof IGenerarFactura factura) {
                factura.generarFactura();
            }

            StringBuilder detalleFactura = new StringBuilder();
            detalleFactura.append(String.format("🎟️ ¡Venta realizada con éxito! (%d entrada(s))\n\n", cantidad));
            detalleFactura.append(String.format("• Fecha y Hora: %s %s\n", fechaHoy, horaActual));
            detalleFactura.append(String.format("• Película: %s\n", funcion.getPelicula().getNombrePelicula()));
            detalleFactura.append(String.format("• Sala: %s | Asiento(s): %s\n", funcion.getSala().getNombreSala(), listaCodigosAsientos.toString()));
            detalleFactura.append(String.format("• Cliente: %s (CC: %s, Tipo: %s)\n", cliente.getNombre(), cliente.getIdentificacion(), cliente.getTipoCliente()));
            detalleFactura.append(String.format("• Beneficio: %s\n", cliente.getDescripcionBeneficio()));
            detalleFactura.append(String.format("• Cantidad de Entradas: %d\n", cantidad));
            detalleFactura.append(String.format("• Tarifa Unitaria: $%,.0f COP\n", tarifaConBeneficioUnitaria));
            detalleFactura.append(String.format("• Subtotal: $%,.0f COP\n", subtotal));
            if (descuentoPuntos > 0) {
                detalleFactura.append(String.format("• Descuento por Puntos: -$%,.0f COP (%d pts canjeados)\n", descuentoPuntos, puntosCanjeados));
                detalleFactura.append(String.format("• Subtotal con Descuento: $%,.0f COP\n", subtotalFinal));
            }
            detalleFactura.append(String.format("• Total Pagado: $%,.0f COP\n", totalFinal));
            detalleFactura.append(String.format("• Método: %s%s", metodoStr, mensajeMembresia));

            Alertas.info(detalleFactura.toString());

            // Actualizar controles
            chkUsarPuntos.setSelected(false);
            cargarAsientosDeFuncion(funcion);
            actualizarAsientosAsignados();
            refrescarTablaVentas();
            refrescarClientes();
            actualizarPanelCanjePuntos(cliente);
            actualizarResumenPago();

        } catch (AsientoOcupadoException | SaldoInsuficienteException | BeneficioNoValidoException e) {
            Alertas.error(e.getMessage());
        } catch (Exception e) {
            Alertas.error("Error al procesar la venta: " + e.getMessage());
        }
    }

    @FXML
    private void onCancelarVenta() {
        VentaEntrada seleccionada = tablaVentas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alertas.advertencia("Selecciona una venta de la tabla para cancelar.");
            return;
        }
        if (seleccionada.getEstado() == EstadoVenta.CANCELADA) {
            Alertas.advertencia("Esta venta ya se encuentra cancelada.");
            return;
        }
        if (Alertas.confirmar("¿Deseas cancelar la venta " + seleccionada.getCodigoVentaEntrada() + " y liberar el asiento " +
                seleccionada.getAsiento().getCodigoAsiento() + "?")) {
            try {
                VentaEntrada.cancelarVenta(cine.getListCineUqVentaEntradas(), seleccionada.getCodigoVentaEntrada());
                refrescarTablaVentas();
                if (cmbVentaFuncion.getValue() != null) {
                    cargarAsientosDeFuncion(cmbVentaFuncion.getValue());
                }
                Alertas.info("Venta cancelada y asiento " + seleccionada.getAsiento().getCodigoAsiento() + " liberado correctamente.");
            } catch (VentaNoEncontradaException e) {
                Alertas.error(e.getMessage());
            }
        }
    }

    @FXML
    private void onActualizarVentas() {
        refrescarTablaVentas();
    }

    private void refrescarModuloVentas() {
        cmbVentaFuncion.setItems(FXCollections.observableArrayList(cine.getListCineUqFunciones()));
        cmbVentaCliente.setItems(FXCollections.observableArrayList(cine.getListCineUqCliente()));
        if (!cine.getListCineUqFunciones().isEmpty() && cmbVentaFuncion.getValue() == null) {
            cmbVentaFuncion.setValue(cine.getListCineUqFunciones().get(0));
        }
        if (!cine.getListCineUqCliente().isEmpty() && cmbVentaCliente.getValue() == null) {
            cmbVentaCliente.setValue(cine.getListCineUqCliente().get(0));
        }
        actualizarPanelCanjePuntos(cmbVentaCliente.getValue());
    }

    private void refrescarTablaVentas() {
        tablaVentas.setItems(FXCollections.observableArrayList(cine.getListCineUqVentaEntradas()));
        tablaVentas.refresh();
    }

    @FXML
    private void onCerrarSesion(ActionEvent evento) {
        AppContext.cerrarSesion();
        Navegador.cambiar((Node) evento.getSource(), "/login.fxml", "Cine UQ - Ingreso");
    }
}
