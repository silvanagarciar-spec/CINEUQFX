package uniquindio.edu.co.cineuqfx.model;

public interface MetodoPago {
    double calcularValorFinal(double valorCompra);
    double PagoInsuficienteException(double valorCompra);
}
