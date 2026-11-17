package uniquindio.edu.co.cineuqfx.model;

public class VentaNoEncontradaException extends Exception {

    public VentaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    public VentaNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
