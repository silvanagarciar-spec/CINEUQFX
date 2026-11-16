package uniquindio.edu.co.cineuqfx.model;

public class Efectivo implements MetodoPago, IPagarValorTotal, IGenerarFactura {

    private static final int PAGO_MINIMO = 15000;
    private double Descuento = 0.15;

    public Efectivo() {
    }

    public Efectivo(double descuento) {
        this.Descuento = descuento;
    }

    @Override
    public double calcularValorFinal(double valorCompra) throws SaldoInsuficienteException {
        if (valorCompra < PAGO_MINIMO) {
            throw new SaldoInsuficienteException(
                "Pago en efectivo rechazado: El valor de compra ($" + valorCompra +
                ") no alcanza el monto mínimo requerido de $" + PAGO_MINIMO
            );
        }
        return valorCompra - (valorCompra * Descuento);
    }

    @Override
    public void pagarValorTotal(double valor) throws SaldoInsuficienteException {
        if (valor < PAGO_MINIMO) {
            throw new SaldoInsuficienteException(
                "No se puede procesar el pago en efectivo: El valor ($" + valor +
                ") es menor al pago mínimo de $" + PAGO_MINIMO
            );
        }
        System.out.printf("✅ [Efectivo] Pago procesado exitosamente por: $%,.2f COP (Descuento aplicado: %.0f%%)%n",
                valor, Descuento * 100);
    }

    @Override
    public double PagoInsuficienteException(double valorCompra) {
        return 0;
    }

    @Override
    public void generarFactura() {
        System.out.println("📄 [Factura - Efectivo] Descuento: " + (Descuento * 100) + "%, Pago mínimo: $" + PAGO_MINIMO);
    }

    public static int getPagoMinimo() {
        return PAGO_MINIMO;
    }

    public double getDescuento() {
        return Descuento;
    }

    public void setDescuento(double descuento) {
        this.Descuento = descuento;
    }
}
