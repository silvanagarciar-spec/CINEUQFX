package uniquindio.edu.co.cineuqfx.model;

/**
 * Excepción lanzada cuando se intenta registrar un cliente que ya existe en el sistema.
 */
public class ClienteYaRegistradoException extends RuntimeException {

    /**
     * Constructor de la excepción ClienteYaRegistradoException.
     *
     * @param mensaje Mensaje descriptivo del error.
     */
    public ClienteYaRegistradoException(String mensaje) {
        super(mensaje);
    }
}