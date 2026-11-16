package uniquindio.edu.co.cineuqfx.model;

/**
 * Excepción lanzada cuando no se encuentra una venta registrada con el código indicado.
 */
public class VentaNoEncontradaException extends RuntimeException {

    /**
     * Constructor de la excepción VentaNoEncontradaException.
     * @param mensaje Mensaje descriptivo del error.
     */
    public VentaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}

