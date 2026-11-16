package uniquindio.edu.co.cineuqfx.model;

/**
 * Excepción lanzada cuando una sala no está disponible para programar una función.
 */
public class SalaNoDisponibleException extends RuntimeException {
    public SalaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}