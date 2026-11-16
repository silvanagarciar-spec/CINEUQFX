package uniquindio.edu.co.cineuqfx.model;

public class VentaEntrada {
    private String codigoVentaEntrada;
    private String fechaVenta;
    private String horaVenta;
    private double valorPagado;
    private EstadoVenta estado;

    private Cliente cliente;
    private Asiento asiento;
    private Funcion funcion;

    public VentaEntrada(String codigoVentaEntrada, Cliente cliente, Asiento asiento, Funcion funcion) {
        this.codigoVentaEntrada = codigoVentaEntrada;
        this.cliente = cliente;
        this.asiento = asiento;
        this.funcion = funcion;
        this.estado = EstadoVenta.ACTIVA;
        this.valorPagado = 0.0;
    }

    /**
     * Registra la fecha de la venta
     * @param fechaVenta
     */

    public void registrarFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    /**
     * Registra la hora de la venta
     * @param horaVenta
     */

    public void registrarHoraVenta(String horaVenta) {
        this.horaVenta = horaVenta;
    }

    /**
     * Registra la hora de la venta
     * @param valorBaseFuncion
     * @return
     */

    public double calcularValorTotalEntrada(double valorBaseFuncion) {
        double total = valorBaseFuncion;

        if (cliente != null && cliente.getTipoCliente() != null) {
            if (cliente.getTipoCliente() == TipoCliente.ESTUDIANTE) {
                total = 12000.0;
            } else if (cliente.getTipoCliente() == TipoCliente.NINO) {
                total = 10000.0;
            } else if (cliente.getTipoCliente() == TipoCliente.ADULTO_MAYOR) {
                total = 11000.0;
            } else {
                total = valorBaseFuncion;
            }
        }

        this.valorPagado = total;
        return this.valorPagado;
    }

    /**
     * Cancela una venta activa y libera el asiento asignado
     * @return
     */

    public boolean cancelarVenta() {
        if (this.estado == EstadoVenta.CANCELADA) {
            return false;
        }
        this.estado = EstadoVenta.CANCELADA;
        if (this.asiento != null) {
            this.asiento.liberarAsiento();
        }
        return true;
    }

    /**
     * Muestra un resumen sencillo de la entrada vendida
     * @return
     */

    public String generarResumenEntrada() {
        String nombreCliente = (cliente != null) ? cliente.getNombre() : "Sin cliente";
        String codAsiento = (asiento != null) ? asiento.getCodigoAsiento() : "Sin asiento";
        return "Venta: " + codigoVentaEntrada +
                " | Cliente: " + nombreCliente +
                " | Asiento: " + codAsiento +
                " | Fecha: " + fechaVenta +
                " | Hora: " + horaVenta +
                " | Valor: $" + valorPagado +
                " | Estado: " + estado;
    }

    /**
     * Getters y Setters
     * @return
     */

    public String getCodigoVentaEntrada() {
        return codigoVentaEntrada;
    }

    public void setCodigoVentaEntrada(String codigoVentaEntrada) {
        this.codigoVentaEntrada = codigoVentaEntrada;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public String getHoraVenta() {
        return horaVenta;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }
}
