package uniquindio.edu.co.cineuqfx.model;

public class Tarjeta implements MetodoPago, IPagarValorTotal , IGenerarFactura{
    @Override
    public double calcularValorFinal(double valorCompra) {
        return 0;
    }

    @Override
    public double PagoInsuficienteException(double valorCompra) {
        return 0;
    }

    @Override
    public void generarFactura() {

    }

    @Override
    public void pagarValorTotal(double valor) {

    }
}
