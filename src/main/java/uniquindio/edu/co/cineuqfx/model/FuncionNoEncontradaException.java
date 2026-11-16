package uniquindio.edu.co.cineuqfx.model;


/**
 * Excepción lanzada cuando no se encuentra una función registrada.
 */
public class FuncionNoEncontradaException extends RuntimeException {
    public FuncionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
