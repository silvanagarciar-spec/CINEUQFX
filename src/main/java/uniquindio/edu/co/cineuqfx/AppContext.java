package uniquindio.edu.co.cineuqfx;

import uniquindio.edu.co.cineuqfx.model.*;

import java.util.ArrayList;

/**
 * Guarda el objeto CinemaUq que usan TODAS las pantallas (una sola instancia)
 * y quién inició sesión. Así los controladores no crean datos propios:
 * siempre llaman a la lógica de la carpeta model.
 */
public final class AppContext {

    public enum Rol { ADMINISTRADOR, OPERADOR }

    private static final CinemaUq CINE = crearCineConDatosDePrueba();
    private static String usuarioActual;
    private static Rol rolActual;

    private AppContext() {
    }

    public static CinemaUq getCine() {
        return CINE;
    }

    public static void iniciarSesion(String usuario, Rol rol) {
        usuarioActual = usuario;
        rolActual = rol;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
        rolActual = null;
    }

    public static String getUsuarioActual() {
        return usuarioActual;
    }

    public static Rol getRolActual() {
        return rolActual;
    }

    /**
     * Datos de ejemplo para poder probar la interfaz.
     * Cuando tengan persistencia (archivos o base de datos) se reemplaza este método.
     */
    private static CinemaUq crearCineConDatosDePrueba() {
        CinemaUq cine = new CinemaUq("Cinema UQ", 900123456, "Armenia, Quindío",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        try {
            cine.registrarPelicula(new Pelicula("Oppenheimer", 1, "Drama", 180,
                    ClasificacionPelicula.MAYORES_15));
            cine.registrarPelicula(new Pelicula("Intensamente 2", 2, "Animación", 96,
                    ClasificacionPelicula.TODOS));
            cine.registrarPelicula(new Pelicula("Dune: Parte Dos", 3, "Ciencia ficción", 166,
                    ClasificacionPelicula.MAYORES_12));
            cine.deshabilitarPelicula(3); // la película nace DISPONIBLE; esta la dejamos NO_DISPONIBLE de ejemplo

            cine.registrarSala(new Sala(1, "Sala 1", 80, 80, EstadoSala.DISPONIBLE,
                    TipoSala.TRADICIONAL, new ArrayList<>()));
            cine.registrarSala(new Sala(2, "Sala VIP", 30, 30, EstadoSala.DISPONIBLE,
                    TipoSala.VIP, new ArrayList<>()));
        } catch (Exception e) {
            throw new IllegalStateException("Error cargando datos de prueba", e);
        }
        try {
            new Cliente("1001", "Ana Gómez", TipoCliente.ESTUDIANTE).registrarCliente(cine.getListCineUqCliente());
        } catch (RuntimeException e) {
            throw new IllegalStateException("Error cargando datos de prueba", e);
        }
        return cine;
    }
}
