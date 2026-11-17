package uniquindio.edu.co.cineuqfx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase: Sala.java
 */
public class SalaTest {

    @Test
    @DisplayName("TC-SA-01: Deshabilitar sala cambia estado a FUERA_DE_SERVICIO")
    void testDeshabilitarSala() {
        Sala sala = new Sala(1, "Sala 1", 50, 0, EstadoSala.DISPONIBLE, TipoSala.TRADICIONAL, new ArrayList<>());
        assertTrue(sala.estaDisponible());

        sala.deshabilitar();
        assertFalse(sala.estaDisponible());
        assertEquals(EstadoSala.FUERA_DE_SERVICIO, sala.getEstadoSala());
    }

    @Test
    @DisplayName("TC-SA-02: Habilitar sala cambia estado a DISPONIBLE")
    void testHabilitarSala() {
        Sala sala = new Sala(1, "Sala 1", 50, 0, EstadoSala.FUERA_DE_SERVICIO, TipoSala.TRADICIONAL, new ArrayList<>());
        assertFalse(sala.estaDisponible());

        sala.habilitar();
        assertTrue(sala.estaDisponible());
        assertEquals(EstadoSala.DISPONIBLE, sala.getEstadoSala());
    }

    @Test
    @DisplayName("TC-SA-03: Actualizar información de la sala")
    void testActualizarInformacion() {
        Sala sala = new Sala(1, "Sala 1", 50, 0, EstadoSala.DISPONIBLE, TipoSala.TRADICIONAL, new ArrayList<>());
        sala.actualizarInformacion("Sala VIP", 30, 30, TipoSala.VIP);

        assertEquals("Sala VIP", sala.getNombreSala());
        assertEquals(30, sala.getCapacidadSala());
        assertEquals(TipoSala.VIP, sala.getTipoSala());
    }

    @Test
    @DisplayName("TC-SA-04: Agregar asiento incrementa contador nAsientos")
    void testAgregarAsiento() {
        Sala sala = new Sala(1, "Sala 1", 50, 0, EstadoSala.DISPONIBLE, TipoSala.TRADICIONAL, new ArrayList<>());
        Asiento asiento = new Asiento("A1", "A", 1);

        sala.agregarAsiento(asiento);
        assertEquals(1, sala.getnAsientos());
        assertEquals(1, sala.getListSalaAsientos().size());
    }
}