package uniquindio.edu.co.cineuqfx.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

    /**
     * Pruebas unitarias para la clase: Efectivo.java
     */
    class EfectivoTest {

        private Efectivo efectivo;

        @BeforeEach
        void setUp() {
            efectivo = new Efectivo(); // PAGO_MINIMO = 15000, Descuento = 0.09
        }

        @Test
        @DisplayName("TC-EF-01: Descuento del 9% aplicado correctamente en compra válida")
        void testCalcularValorFinalExitoso() throws SaldoInsuficienteException {
            double compra = 20000.0;
            double esperado = 20000.0 - (20000.0 * 0.09); // 18200.0
            assertEquals(esperado, efectivo.calcularValorFinal(compra), 0.001);
        }

        @Test
        @DisplayName("TC-EF-02: Lanza SaldoInsuficienteException si compra < 15000")
        void testCalcularValorFinalMenorAlMinimo() {
            assertThrows(SaldoInsuficienteException.class, () -> {
                efectivo.calcularValorFinal(12000.0);
            });
        }

        @Test
        @DisplayName("TC-EF-03: Procesar pago exitoso con monto mínimo permitido")
        void testPagarValorTotalExitoso() {
            assertDoesNotThrow(() -> {
                efectivo.pagarValorTotal(15000.0);
            });
        }

        @Test
        @DisplayName("TC-EF-04: Lanza SaldoInsuficienteException al intentar pagar menos del mínimo")
        void testPagarValorTotalInsuficiente() {
            assertThrows(SaldoInsuficienteException.class, () -> {
                efectivo.pagarValorTotal(8000.0);
            });
        }
    }

