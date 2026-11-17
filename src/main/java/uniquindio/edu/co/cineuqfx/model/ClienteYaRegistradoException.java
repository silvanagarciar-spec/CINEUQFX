package uniquindio.edu.co.cineuqfx.model;


public class ClienteYaRegistradoException extends RuntimeException {

    /**
     * Metodo Constructor de la excepción ClienteYaRegistradoException.
     *
     * @param mensaje Mensaje descriptivo del error.
     */
    public ClienteYaRegistradoException(String mensaje) {
        super(mensaje);
    }
}