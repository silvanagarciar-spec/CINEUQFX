package uniquindio.edu.co.cineuqfx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ConvenioTest {

    @Test
    @DisplayName("TC-CONV-01: Aplicar descuento convenio porcentaje")
    void testAplicarDescuentoConvenio() {
        Convenio c = new Convenio(1, "Convenio UQ", "Universidad del Quindío", TipoEntidad.UNIVERSIDAD, 20.0);
        double resultado = c.aplicarBeneficio(15000.0);
        assertEquals(12000.0, resultado, 0.001);
    }

    @Test
    @DisplayName("TC-CONV-02: Validaciones de campos obligatorios")
    void testValidacionesConvenio() {
        assertThrows(BeneficioNoValidoException.class, () ->
                new Convenio(2, "Invalido", "", TipoEntidad.UNIVERSIDAD, 10.0));
        assertThrows(BeneficioNoValidoException.class, () ->
                new Convenio(3, "Invalido", "Entidad", null, 10.0));
        assertThrows(BeneficioNoValidoException.class, () ->
                new Convenio(4, "Invalido", "Entidad", TipoEntidad.CAJA_COMPENSACION, -1.0));
    }
}
