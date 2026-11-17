package uniquindio.edu.co.cineuqfx.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase: Funcion.java
 */
public class FuncionTest {

    private Funcion funcion;

    @BeforeEach
    void setUp() {
        funcion = new Funcion("F01", null, null, LocalDate.now(), LocalTime.of(19, 0), 12000.0, "B5");
    }

    @Test
    @DisplayName("TC-FN-01: Verificar función existente en lista retorna true")
    void testVerificarFuncionExisteTrue() {
        List<Funcion> funciones = new ArrayList<>();
        funciones.add(funcion);

        assertTrue(Funcion.verificarFuncionExiste("F01", funciones));
    }

    @Test
    @DisplayName("TC-FN-02: Verificar función que no existe en lista retorna false")
    void testVerificarFuncionExisteFalse() {
        List<Funcion> funciones = new ArrayList<>();
        funciones.add(funcion);

        assertFalse(Funcion.verificarFuncionExiste("F99", funciones));
    }

    @Test
    @DisplayName("TC-FN-03: Agregar una venta a la función en memoria")
    void testAgregarVenta() {
        Cliente cliente = new Cliente("1001", "Ana Torres", TipoCliente.GENERAL);
        Asiento asiento = new Asiento("B5", "B", 5);
        VentaEntrada venta = new VentaEntrada("V01", cliente, asiento, funcion);

        funcion.agregarVenta(venta);

        assertEquals(1, funcion.getListFuncionVentaEntradas().size());
        assertEquals("V01", funcion.getListFuncionVentaEntradas().get(0).getCodigoVentaEntrada());
    }

    @Test
    @DisplayName("TC-FN-04: Verificar que función de instancia tiene código y estado programado")
    void testVerificarFuncionInstancia() {
        assertTrue(funcion.verificarFuncionExiste());
        assertEquals(EstadoFuncion.PROGRAMADA, funcion.getEstadoFuncion());
    }

    @Test
    @DisplayName("TC-FN-05: Verificar función en lista nula o con código nulo retorna false")
    void testVerificarFuncionExisteListaNulaOCodigoNulo() {
        assertFalse(Funcion.verificarFuncionExiste("F01", null));
        assertFalse(Funcion.verificarFuncionExiste(null, new ArrayList<>(List.of(funcion))));
    }

    @Test
    @DisplayName("TC-FN-06: Función sin código no se considera existente")
    void testVerificarFuncionInstanciaSinCodigo() {
        Funcion vacia = new Funcion();
        assertFalse(vacia.verificarFuncionExiste());
    }
}