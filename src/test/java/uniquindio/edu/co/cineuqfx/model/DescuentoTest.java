package uniquindio.edu.co.cineuqfx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DescuentoTest {


    @Test
    @DisplayName("TC-DESC-01: Aplicar descuento con porcentaje personalizado")
    void testAplicarDescuentoPersonalizado() {
        Descuento d = new Descuento(1, "Descuento Especial", 20.0, MotivoDescuento.MIERCOLES_CINE);
        double resultado = d.aplicarBeneficio(15000.0);
        assertEquals(12000.0, resultado, 0.001);
    }

    @Test
    @DisplayName("TC-DESC-02: Constructor por defecto según motivo")
    void testDescuentoPorDefecto() {
        Descuento dCumple = new Descuento(2, "Descuento Cumple", MotivoDescuento.CUMPLEANOS);
        assertEquals(50.0, dCumple.getPorcentajeDescuento(), 0.001);
        assertEquals(10000.0, dCumple.aplicarBeneficio(20000.0), 0.001);

        Descuento dEstudiante = new Descuento(3, "Martes Estudiante", MotivoDescuento.MARTES_ESTUDIANTE);
        assertEquals(25.0, dEstudiante.getPorcentajeDescuento(), 0.001);
        assertEquals(15000.0, dEstudiante.aplicarBeneficio(20000.0), 0.001);
    }

    @Test
    @DisplayName("TC-DESC-03: Porcentaje negativo o mayor a 100 lanza BeneficioNoValidoException")
    void testPorcentajeInvalido() {
        assertThrows(BeneficioNoValidoException.class, () ->
                new Descuento(4, "Invalido", -5.0, MotivoDescuento.MIERCOLES_CINE));
        assertThrows(BeneficioNoValidoException.class, () ->
                new Descuento(5, "Invalido", 105.0, MotivoDescuento.MIERCOLES_CINE));
    }

    @Test
    @DisplayName("TC-DESC-04: Motivo nulo lanza BeneficioNoValidoException")
    void testMotivoNulo() {
        assertThrows(BeneficioNoValidoException.class, () ->
                new Descuento(6, "Invalido", 10.0, null));
    }
}
