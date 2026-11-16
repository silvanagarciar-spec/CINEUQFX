package uniquindio.edu.co.cineuqfx.model;

/**
 * Excepción lanzada cuando se intenta seleccionar u ocupar un asiento que ya está ocupado.
 */
public class AsientoOcupadoException extends RuntimeException {

    /**
     * Constructor de la excepción AsientoOcupadoException.
     * @param mensaje Mensaje descriptivo del error.
     */
    public AsientoOcupadoException(String mensaje) {
        super(mensaje);
    }
}