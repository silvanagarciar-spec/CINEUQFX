package uniquindio.edu.co.cineuqfx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MembresiaTest {

    @Test
    @DisplayName("TC-MEM-01: Descuento correcto por nivel BRONCE, PLATA y ORO")
    void testDescuentoNivel() {
        Membresia bronce = new Membresia(1, "Bronce", 0, NivelMembresia.BRONCE);
        Membresia plata = new Membresia(2, "Plata", 0, NivelMembresia.PLATA);
        Membresia oro = new Membresia(3, "Oro", 0, NivelMembresia.ORO);

        assertEquals(18000.0, bronce.aplicarBeneficio(20000.0), 0.001); // 10%
        assertEquals(16000.0, plata.aplicarBeneficio(20000.0), 0.001);  // 20%
        assertEquals(14000.0, oro.aplicarBeneficio(20000.0), 0.001);    // 30%
    }

    @Test
    @DisplayName("TC-MEM-02: Acumular puntos suma correctamente")
    void testAcumularPuntos() {
        Membresia m = new Membresia(4, "Oro", 50, NivelMembresia.ORO);
        m.acumularPuntos(20);
        assertEquals(70, m.getPuntosAcumulados());
    }

    @Test
    @DisplayName("TC-MEM-03: Puntos negativos lanzan BeneficioNoValidoException")
    void testPuntosNegativos() {
        assertThrows(BeneficioNoValidoException.class, () ->
                new Membresia(5, "Invalido", -10, NivelMembresia.BRONCE));

        Membresia m = new Membresia(6, "Valido", 10, NivelMembresia.BRONCE);
        assertThrows(BeneficioNoValidoException.class, () ->
                m.acumularPuntos(-5));
    }

    @Test
    @DisplayName("TC-MEM-04: Canje de puntos y cálculo de descuento (500 por cada 100, y +2000 si > 1000)")
    void testDescuentoYCanjePuntos() {
        // Menos de 100 puntos no da descuento
        Membresia mPocos = new Membresia(7, "Pocos", 80, NivelMembresia.BRONCE);
        assertEquals(0.0, mPocos.calcularDescuentoPorPuntos(), 0.001);

        // 250 puntos -> 2 bloques de 100 = $1.000 COP
        Membresia m200 = new Membresia(8, "Media", 250, NivelMembresia.PLATA);
        assertEquals(1000.0, m200.calcularDescuentoPorPuntos(), 0.001);

        // 1200 puntos -> 12 bloques de 100 ($6.000) + $2.000 extra por superar 1000 = $8.000 COP
        Membresia m1200 = new Membresia(9, "Muchas", 1200, NivelMembresia.ORO);
        assertEquals(8000.0, m1200.calcularDescuentoPorPuntos(), 0.001);

        // Canje de puntos descuenta del saldo
        m1200.canjearPuntos(1200);
        assertEquals(0, m1200.getPuntosAcumulados());

        // Intentar canjear más puntos de los que tiene lanza excepción
        assertThrows(BeneficioNoValidoException.class, () -> m1200.canjearPuntos(50));
    }
}
