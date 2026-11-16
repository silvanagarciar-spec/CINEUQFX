package uniquindio.edu.co.cineuqfx.model;

public class Transferencia implements MetodoPago , IGenerarFactura , IPagarValorTotal {

    @Override
    public double calcularValorFinal(double valorCompra) {
        return valorCompra * 0.1;
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
