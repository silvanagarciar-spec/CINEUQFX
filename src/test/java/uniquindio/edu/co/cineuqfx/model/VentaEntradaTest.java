package uniquindio.edu.co.cineuqfx.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class VentaEntradaTest {

    private Funcion funcion;
    private Asiento asiento1;
    private Asiento asiento2;
    private Cliente clienteGeneral;
    private Cliente clienteEstudiante;
    private List<VentaEntrada> ventas;

    @BeforeEach
    void setUp() {
        funcion = new Funcion();
        asiento1 = new Asiento("A1", "A", 1);
        asiento2 = new Asiento("A2", "A", 2);
        clienteGeneral = new Cliente("1001", "Ana Torres", TipoCliente.GENERAL);
        clienteEstudiante = new Cliente("1002", "Luis Pérez", TipoCliente.ESTUDIANTE);
        ventas = new ArrayList<>();
        ventas.add(new VentaEntrada("V01", clienteGeneral, asiento1, funcion));
        ventas.add(new VentaEntrada("V02", clienteEstudiante, asiento2, funcion));
    }

    @Test
    @DisplayName("TC-VE-01: Una venta nueva inicia en estado ACTIVA")
    void testVentaNuevaEstaActiva() {
        VentaEntrada venta = ventas.get(0);
        assertEquals("V01", venta.getCodigoVentaEntrada());
        assertEquals(EstadoVenta.ACTIVA, venta.getEstado());
    }

    @Test
    @DisplayName("TC-VE-02: Cliente GENERAL paga el valor base")
    void testCalcularValorClienteGeneral() {
        double valor = ventas.get(0).calcularValorTotalEntrada(15000.0);
        assertEquals(15000.0, valor, 0.001);
        assertEquals(15000.0, ventas.get(0).getValorPagado(), 0.001);
    }

    @Test
    @DisplayName("TC-VE-03: Cliente ESTUDIANTE paga tarifa de 12000")
    void testCalcularValorClienteEstudiante() {
        double valor = ventas.get(1).calcularValorTotalEntrada(15000.0);
        assertEquals(12000.0, valor, 0.001);
    }

    @Test
    @DisplayName("TC-VE-04: Procesar venta ocupa el asiento y registra fecha, hora y valor")
    void testProcesarVenta() {
        VentaEntrada venta = ventas.get(0);
        venta.procesarVenta("2026-11-16", "18:00", 15000.0);

        assertEquals(EstadoAsiento.OCUPADO, asiento1.getEstado());
        assertEquals("2026-11-16", venta.getFechaVenta());
        assertEquals("18:00", venta.getHoraVenta());
        assertEquals(15000.0, venta.getValorPagado(), 0.001);
        assertEquals(EstadoVenta.ACTIVA, venta.getEstado());
    }

    @Test
    @DisplayName("TC-VE-05: Procesar venta con asiento ocupado lanza AsientoOcupadoException")
    void testProcesarVentaAsientoOcupado() {
        asiento1.ocuparAsiento();
        assertThrows(AsientoOcupadoException.class,
                () -> ventas.get(0).procesarVenta("2026-11-16", "18:00", 15000.0));
    }

    @Test
    @DisplayName("TC-VE-06: Cancelar venta existente cambia estado a CANCELADA y libera el asiento")
    void testCancelarVentaExitosa() {
        ventas.get(0).procesarVenta("2026-11-16", "18:00", 15000.0);
        assertEquals(EstadoAsiento.OCUPADO, asiento1.getEstado());

        VentaEntrada.cancelarVenta(ventas, "V01");

        assertEquals(EstadoVenta.CANCELADA, ventas.get(0).getEstado());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento1.getEstado());
    }

    @Test
    @DisplayName("TC-VE-07: Cancelar venta inexistente lanza VentaNoEncontradaException")
    void testCancelarVentaInexistente() {
        assertThrows(VentaNoEncontradaException.class,
                () -> VentaEntrada.cancelarVenta(ventas, "V99"));
    }

    @Test
    @DisplayName("TC-VE-08: Cancelar en lista nula lanza VentaNoEncontradaException")
    void testCancelarVentaListaNula() {
        assertThrows(VentaNoEncontradaException.class,
                () -> VentaEntrada.cancelarVenta(null, "V01"));
    }

    @Test
    @DisplayName("TC-VE-09: Cancelar en lista vacía lanza VentaNoEncontradaException")
    void testCancelarVentaListaVacia() {
        assertThrows(VentaNoEncontradaException.class,
                () -> VentaEntrada.cancelarVenta(new ArrayList<>(), "V01"));
    }
}