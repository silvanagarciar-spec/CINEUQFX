package uniquindio.edu.co.cineuqfx.model;

public class Transferencia implements MetodoPago, IGenerarFactura, IPagarValorTotal {

    private static final int PAGO_MINIMO = 50000;
    private double recargo = 0.20;

    public Transferencia() {
    }

    public Transferencia(double recargo) {
        this.recargo = recargo;
    }

    @Override
    public double calcularValorFinal(double valorCompra) throws SaldoInsuficienteException {
        if (valorCompra < PAGO_MINIMO) {
            throw new SaldoInsuficienteException(
                "Transferencia rechazada: El valor de compra ($" + valorCompra +
                ") no alcanza el monto mínimo requerido de $" + PAGO_MINIMO
            );
        }
        return valorCompra + (valorCompra * recargo);
    }

    @Override
    public void pagarValorTotal(double valor) throws SaldoInsuficienteException {
        if (valor < PAGO_MINIMO) {
            throw new SaldoInsuficienteException(
                "No se puede procesar la transferencia: El valor ($" + valor +
                ") es menor al pago mínimo de $" + PAGO_MINIMO
            );
        }
        System.out.printf("✅ [Transferencia] Pago procesado exitosamente por: $%,.2f COP (Recargo aplicado: %.0f%%)%n",
                valor, recargo * 100);
    }

    @Override
    public void generarFactura() {
        System.out.println("📄 [Factura - Transferencia] Recargo: " + (recargo * 100) + "%, Pago mínimo: $" + PAGO_MINIMO);
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
