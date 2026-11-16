package uniquindio.edu.co.cineuqfx.model;

/**
 * Excepción lanzada cuando no se encuentra un cliente registrado en el sistema.
 */
public class ClienteNoEncontradoException extends RuntimeException {

    /**
     * Constructor de la excepción ClienteNoEncontradoException.
     * @param mensaje Mensaje descriptivo del error.
     */
    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
