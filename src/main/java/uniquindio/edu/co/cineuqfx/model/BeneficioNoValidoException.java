package uniquindio.edu.co.cineuqfx.model;

/**
 * Sirve para cuando un beneficio tiene valores inconsistentes o no validos
 */
public class BeneficioNoValidoException extends RuntimeException {
    public BeneficioNoValidoException(String message) {
        super(message);
    }
}
