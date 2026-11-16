package uniquindio.edu.co.cineuqfx.model;

public class Tarjeta implements MetodoPago, IPagarValorTotal, IGenerarFactura {

    private static final int PAGO_MINIMO = 100000;
    private double recargo = 0.30;

    public Tarjeta() {
    }

    public Tarjeta(double recargo) {
        this.recargo = recargo;
    }

    @Override
    public double calcularValorFinal(double valorCompra) throws SaldoInsuficienteException {
        if (valorCompra < PAGO_MINIMO) {
            throw new SaldoInsuficienteException(
                "Pago con tarjeta rechazado: El valor de compra ($" + valorCompra +
                ") no alcanza el monto mínimo requerido de $" + PAGO_MINIMO
            );
        }
        return valorCompra + (valorCompra * recargo);
    }

    @Override
    public void pagarValorTotal(double valor) throws SaldoInsuficienteException {
        if (valor < PAGO_MINIMO) {
            throw new SaldoInsuficienteException(
                "No se puede procesar el pago con tarjeta: El valor ($" + valor +
                ") es menor al pago mínimo de $" + PAGO_MINIMO
            );
        }
        System.out.printf("✅ [Tarjeta] Pago procesado exitosamente por: $%,.2f COP (Recargo aplicado: %.0f%%)%n",
                valor, recargo * 100);
    }

    @Override
    public double PagoInsuficienteException(double valorCompra) {
        return 0;
    }

    @Override
    public void generarFactura() {
        System.out.println("📄 [Factura - Tarjeta] Recargo: " + (recargo * 100) + "%, Pago mínimo: $" + PAGO_MINIMO);
    }

    public static int getPagoMinimo() {
        return PAGO_MINIMO;
    }

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }
}
