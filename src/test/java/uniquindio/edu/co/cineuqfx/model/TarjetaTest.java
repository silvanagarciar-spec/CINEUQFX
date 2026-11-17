package uniquindio.edu.co.cineuqfx.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TarjetaTest {

        /**
         * Pruebas unitarias para la clase: Tarjeta.java
         */
        private Tarjeta tarjeta;

        @BeforeEach
        void setUp() {
            tarjeta = new Tarjeta(); // PAGO_MINIMO = 100000, recargo = 0.05
        }

        @Test
        @DisplayName("TC-TJ-01: Recargo del 5% aplicado correctamente")
        void testCalcularValorFinalExitoso() throws SaldoInsuficienteException {
            double compra = 100000.0;
            double esperado = 100000.0 + (100000.0 * 0.05); // 105000.0
            assertEquals(esperado, tarjeta.calcularValorFinal(compra), 0.001);
        }

        @Test
        @DisplayName("TC-TJ-02: Lanza SaldoInsuficienteException si compra < 100000")
        void testCalcularValorFinalMenorAlMinimo() {
            assertThrows(SaldoInsuficienteException.class, () -> {
                tarjeta.calcularValorFinal(75000.0);
            });
        }

        @Test
        @DisplayName("TC-TJ-03: Pago total exitoso cuando supera el mínimo")
        void testPagarValorTotalExitoso() {
            assertDoesNotThrow(() -> {
                tarjeta.pagarValorTotal(120000.0);
            });
        }

        @Test
        @DisplayName("TC-TJ-04: Lanza SaldoInsuficienteException en pago menor a 100000")
        void testPagarValorTotalInsuficiente() {
            assertThrows(SaldoInsuficienteException.class, () -> {
                tarjeta.pagarValorTotal(95000.0);
            });
        }
    }

