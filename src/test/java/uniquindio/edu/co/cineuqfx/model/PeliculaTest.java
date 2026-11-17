package uniquindio.edu.co.cineuqfx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



    /**
     * Pruebas unitarias para la clase: Pelicula.java
     */
    public class PeliculaTest {

        @Test
        @DisplayName("TC-PL-01: Deshabilitar película cambia su estado a NO_DISPONIBLE")
        void testDeshabilitarPelicula() {
            Pelicula pelicula = new Pelicula("Batman", 101, "Acción", 140, ClasificacionPelicula.MAYORES_15);
            assertTrue(pelicula.estaDisponible());

            pelicula.deshabilitar();
            assertFalse(pelicula.estaDisponible());
            assertEquals(EstadoPelicula.NO_DISPONIBLE, pelicula.getEstadoPelicula());
        }

        @Test
        @DisplayName("TC-PL-02: Habilitar película cambia su estado a DISPONIBLE")
        void testHabilitarPelicula() {
            Pelicula pelicula = new Pelicula("Batman", 101, "Acción", 140, ClasificacionPelicula.MAYORES_15);
            pelicula.deshabilitar();
            assertFalse(pelicula.estaDisponible());

            pelicula.habilitar();
            assertTrue(pelicula.estaDisponible());
            assertEquals(EstadoPelicula.DISPONIBLE, pelicula.getEstadoPelicula());
        }

        @Test
        @DisplayName("TC-PL-03: Actualizar información de la película en memoria")
        void testActualizarInformacion() {
            Pelicula pelicula = new Pelicula("Original", 101, "Drama", 90, ClasificacionPelicula.TODOS);
            pelicula.actualizarInformacion("Modificada", "Comedia", 110, ClasificacionPelicula.MAYORES_12);

            assertEquals("Modificada", pelicula.getNombrePelicula());
            assertEquals("Comedia", pelicula.getGenero());
            assertEquals(110, pelicula.getDuracionMinutos());
            assertEquals(ClasificacionPelicula.MAYORES_12, pelicula.getClasificacionPelicula());
        }

        @Test
        @DisplayName("TC-PL-04: Filtrar solo películas disponibles")
        void testVisualizarPeliculasDisponibles() {
            List<Pelicula> catalogo = new ArrayList<>();
            Pelicula p1 = new Pelicula("P1", 1, "Acción", 100, ClasificacionPelicula.TODOS);
            Pelicula p2 = new Pelicula("P2", 2, "Terror", 90, ClasificacionPelicula.MAYORES_18);
            p2.deshabilitar();

            catalogo.add(p1);
            catalogo.add(p2);

            List<Pelicula> disponibles = Pelicula.visualizarPeliculasDisponibles(catalogo);
            assertEquals(1, disponibles.size());
            assertEquals("P1", disponibles.get(0).getNombrePelicula());
        }
    }
