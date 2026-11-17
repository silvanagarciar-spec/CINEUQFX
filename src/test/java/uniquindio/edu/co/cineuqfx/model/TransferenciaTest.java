package uniquindio.edu.co.cineuqfx.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

    /**
     * Pruebas unitarias para la clase: Transferencia.java
     */
    class TransferenciaTest {

        private Transferencia transferencia;

        @BeforeEach
        void setUp() {
            transferencia = new Transferencia(); // PAGO_MINIMO = 50000, recargo = 0.20
        }

        @Test
        @DisplayName("TC-TR-01: Recargo del 20% aplicado correctamente")
        void testCalcularValorFinalExitoso() throws SaldoInsuficienteException {
            double compra = 50000.0;
            double esperado = 50000.0 + (50000.0 * 0.20); // 60000.0
            assertEquals(esperado, transferencia.calcularValorFinal(compra), 0.001);
        }

        @Test
        @DisplayName("TC-TR-02: Lanza SaldoInsuficienteException si compra < 50000")
        void testCalcularValorFinalMenorAlMinimo() {
            assertThrows(SaldoInsuficienteException.class, () -> {
                transferencia.calcularValorFinal(40000.0);
            });
        }

        @Test
        @DisplayName("TC-TR-03: Pago total exitoso cuando supera el mínimo")
        void testPagarValorTotalExitoso() {
            assertDoesNotThrow(() -> {
                transferencia.pagarValorTotal(65000.0);
            });
        }

        @Test
        @DisplayName("TC-TR-04: Lanza SaldoInsuficienteException en pago menor a 50000")
        void testPagarValorTotalInsuficiente() {
            assertThrows(SaldoInsuficienteException.class, () -> {
                transferencia.pagarValorTotal(35000.0);
            });
        }
    }

