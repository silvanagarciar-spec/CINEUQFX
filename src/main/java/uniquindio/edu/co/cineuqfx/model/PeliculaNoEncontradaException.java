
package uniquindio.edu.co.cineuqfx.model;

/**
 * Excepción lanzada cuando no se encuentra una película registrada.
 */
public class PeliculaNoEncontradaException extends RuntimeException {
    public PeliculaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
