package uniquindio.edu.co.cineuqfx;

import uniquindio.edu.co.cineuqfx.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

            Sala sala1 = new Sala(1, "Sala 1", 80, 0, EstadoSala.DISPONIBLE,
                    TipoSala.TRADICIONAL, new ArrayList<>());
            generarAsientos(sala1, 4, 10);
            cine.registrarSala(sala1);

            Sala salaVip = new Sala(2, "Sala VIP", 30, 0, EstadoSala.DISPONIBLE,
                    TipoSala.VIP, new ArrayList<>());
            generarAsientos(salaVip, 3, 6);
            cine.registrarSala(salaVip);

            // Funciones de prueba
            Pelicula p1 = cine.buscarPeliculaPorCodigo(1);
            Pelicula p2 = cine.buscarPeliculaPorCodigo(2);
            Funcion f1 = new Funcion("F01", p1, sala1, LocalDate.now(), LocalTime.of(18, 30), 15000.0, "Estándar");
            cine.agregarFuncion(f1, 1, 1);

            Funcion f2 = new Funcion("F02", p2, salaVip, LocalDate.now(), LocalTime.of(16, 0), 22000.0, "VIP");
            cine.agregarFuncion(f2, 2, 2);

        } catch (Exception e) {
            throw new IllegalStateException("Error cargando datos de prueba", e);
        }
        try {
            new Cliente("1001", "Ana Gómez", TipoCliente.ESTUDIANTE).registrarCliente(cine.getListCineUqCliente());
            new Cliente("1002", "Carlos Pérez", TipoCliente.GENERAL).registrarCliente(cine.getListCineUqCliente());
            new Cliente("1003", "Elena Ruiz", TipoCliente.ADULTO_MAYOR).registrarCliente(cine.getListCineUqCliente());
        } catch (RuntimeException e) {
            throw new IllegalStateException("Error cargando datos de prueba", e);
        }
        return cine;
    }

    /**
     * Genera asientos con filas identificadas por letras (A, B, C...) y números.
     */
    public static void generarAsientos(Sala sala, int filas, int columnas) {
        char letra = 'A';
        for (int i = 0; i < filas; i++) {
            String filaStr = String.valueOf((char) (letra + i));
            for (int j = 1; j <= columnas; j++) {
                String codigo = filaStr + j;
                sala.agregarAsiento(new Asiento(codigo, filaStr, j));
            }
        }
    }
}
