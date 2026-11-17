package uniquindio.edu.co.cineuqfx.model;

public class Tarjeta implements MetodoPago, IPagarValorTotal, IGenerarFactura {
    /**
     * Constantes para la interface
     */
    private static final int PAGO_MINIMO = 100000;
    private double recargo = 0.30;

    public Tarjeta() {
    }

    /**
     * metodo constructor
     * @param recargo
     */
    public Tarjeta(double recargo) {
        this.recargo = recargo;
    }

    /**
     * Metodo para calcula el valor final
     * @param valorCompra
     * @return valor total de la compra mas el recargo
     * @throws SaldoInsuficienteException
     */
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

    /**
     * Metodo para verificar el pago dedl valor total
     * @param valor
     * @throws SaldoInsuficienteException
     */
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

    /**
     * Metodo para generar la factura
     */
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
