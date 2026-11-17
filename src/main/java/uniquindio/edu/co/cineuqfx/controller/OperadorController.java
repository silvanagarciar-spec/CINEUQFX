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
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colCliId;
    @FXML private TableColumn<Cliente, String> colCliNombre;
    @FXML private TableColumn<Cliente, String> colCliTipo;

    /**
     * Venta de Entradas
     */
    @FXML private ComboBox<Funcion> cmbVentaFuncion;
    @FXML private ComboBox<Cliente> cmbVentaCliente;
    @FXML private ComboBox<Asiento> cmbVentaAsiento;
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
        cmbCliTipo.setItems(FXCollections.observableArrayList(TipoCliente.values()));
        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, anterior, c) -> {
            if (c != null) {
                txtCliId.setText(c.getIdentificacion());
                txtCliNombre.setText(c.getNombre());
                cmbCliTipo.setValue(c.getTipoCliente());
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
                "Efectivo (15% descuento - Mín. $15.000)",
                "Tarjeta (30% recargo - Mín. $100.000)",
                "Transferencia (20% recargo - Mín. $50.000)"
        ));

        cmbVentaFuncion.getSelectionModel().selectedItemProperty().addListener((obs, ant, fun) -> {
            cargarAsientosDeFuncion(fun);
            actualizarResumenPago();
        });

        cmbVentaCliente.getSelectionModel().selectedItemProperty().addListener((obs, ant, cli) -> {
            actualizarResumenPago();
        });

        cmbVentaMetodoPago.getSelectionModel().selectedItemProperty().addListener((obs, ant, met) -> {
            actualizarResumenPago();
        });
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

    private void actualizarResumenPago() {
        Funcion funcion = cmbVentaFuncion.getValue();
        Cliente cliente = cmbVentaCliente.getValue();
        String metodo = cmbVentaMetodoPago.getValue();

        if (funcion == null || cliente == null) {
            lblTarifaCliente.setText("Tarifa según tipo cliente: $0");
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
        lblTarifaCliente.setText(String.format("Tarifa cliente (%s): $%,.0f COP", cliente.getTipoCliente(), tarifaCliente));

        if (metodo == null) {
            lblTotalPagar.setText(String.format("Total a pagar: $%,.0f COP", tarifaCliente));
            lblInfoMetodo.setText("Selecciona un método de pago.");
            return;
        }

        try {
            MetodoPago mp = obtenerMetodoPago(metodo);
            double totalFinal = mp.calcularValorFinal(tarifaCliente);
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
            new Cliente(id, nombre, tipo).registrarCliente(cine.getListCineUqCliente());
            refrescarClientes();
            refrescarModuloVentas();
            onLimpiarCliente();
            Alertas.info("Cliente registrado correctamente.");
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
        Asiento asiento = cmbVentaAsiento.getValue();
        String metodoStr = cmbVentaMetodoPago.getValue();

        if (funcion == null) {
            Alertas.advertencia("Selecciona una función programada.");
            return;
        }
        if (cliente == null) {
            Alertas.advertencia("Selecciona un cliente.");
            return;
        }
        if (asiento == null) {
            Alertas.advertencia("Selecciona un asiento disponible.");
            return;
        }
        if (metodoStr == null) {
            Alertas.advertencia("Selecciona un método de pago.");
            return;
        }

        try {
            // Validar asiento disponible
            asiento.verificarAsientoSeleccionadoDisponible();

            // Código único de venta
            String codVenta = "V" + (System.currentTimeMillis() % 1000000);
            VentaEntrada venta = new VentaEntrada(codVenta, cliente, asiento, funcion);

            // Calcular tarifa base y según método de pago
            double tarifaCliente = venta.calcularValorTotalEntrada(funcion.getValorEntrada());
            MetodoPago metodoPago = obtenerMetodoPago(metodoStr);
            double totalFinal = metodoPago.calcularValorFinal(tarifaCliente);

            // Procesar pago
            if (metodoPago instanceof IPagarValorTotal pagar) {
                pagar.pagarValorTotal(totalFinal);
            }

            // Procesar y registrar venta
            String fechaHoy = LocalDate.now().toString();
            String horaActual = LocalTime.now().withNano(0).toString();
            venta.procesarVenta(fechaHoy, horaActual, funcion.getValorEntrada());
            venta.setValorPagado(totalFinal);

            funcion.agregarVenta(venta);
            cine.agregarVentaEntrada(venta);

            // Factura
            if (metodoPago instanceof IGenerarFactura factura) {
                factura.generarFactura();
            }

            String detalleFactura = String.format(
                    "🎟️ ¡Venta realizada con éxito!\n\n" +
                    "• Código de Venta: %s\n" +
                    "• Fecha y Hora: %s %s\n" +
                    "• Película: %s\n" +
                    "• Sala: %s | Asiento: %s\n" +
                    "• Cliente: %s (CC: %s, Tipo: %s)\n" +
                    "• Valor Pagado: $%,.0f COP\n" +
                    "• Método: %s",
                    venta.getCodigoVentaEntrada(),
                    fechaHoy, horaActual,
                    funcion.getPelicula().getNombrePelicula(),
                    funcion.getSala().getNombreSala(), asiento.getCodigoAsiento(),
                    cliente.getNombre(), cliente.getIdentificacion(), cliente.getTipoCliente(),
                    totalFinal, metodoStr
            );
            Alertas.info(detalleFactura);

            // Actualizar controles
            cargarAsientosDeFuncion(funcion);
            refrescarTablaVentas();
            actualizarResumenPago();

        } catch (AsientoOcupadoException | SaldoInsuficienteException e) {
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
