package uniquindio.edu.co.cineuqfx.model;

public class Efectivo implements MetodoPago, IPagarValorTotal , IGenerarFactura{

    private static final int PAGO_MINIMO = 15000;
    private double Descuento = 0.15;

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
