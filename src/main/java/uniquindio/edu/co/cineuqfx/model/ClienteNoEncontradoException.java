package uniquindio.edu.co.cineuqfx.model;

public class ClienteNoEncontradoException extends RuntimeException {

    /**
     * Metodo Constructor de la excepción ClienteNoEncontradoException.
     * @param mensaje Mensaje descriptivo del error.
     */
    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
