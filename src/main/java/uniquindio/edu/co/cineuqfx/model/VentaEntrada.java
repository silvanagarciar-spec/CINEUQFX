package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

public class VentaEntrada {

    private String codigoVentaEntrada;
    private String fechaVenta;
    private String horaVenta;
    private double valorPagado;
    private EstadoVenta estadoVenta;
    private Funcion funcion;

    public VentaEntrada() {
        this.estadoVenta = EstadoVenta.ACTIVA;
    }

    /**
     * Metodo constructor de la clase venta
     * @param codigoVentaEntrada de la venta
     * @param fechaVenta de la venta
     * @param horaVenta de la venta
     * @param valorPagado de la venta
     */
    public VentaEntrada(String codigoVentaEntrada, String fechaVenta, String horaVenta, double valorPagado) {
        this.codigoVentaEntrada = codigoVentaEntrada;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.valorPagado = valorPagado;
        this.estadoVenta = EstadoVenta.ACTIVA;
    }

    public VentaEntrada(String codigoVentaEntrada, String fechaVenta, String horaVenta, double valorPagado,
                        EstadoVenta estadoVenta, Funcion funcion) {
        this.codigoVentaEntrada = codigoVentaEntrada;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.valorPagado = valorPagado;
        this.estadoVenta = estadoVenta;
        this.funcion = funcion;
    }

    /**
     * Metodo para buscar una venta por su código en una lista de ventas;
     */
    public static VentaEntrada buscarVenta(String codigoVenta, List<VentaEntrada> ventas) throws VentaNoEncontradaException {
        if (ventas != null && codigoVenta != null) {
            for (VentaEntrada v : ventas) {
                if (v != null && codigoVenta.equalsIgnoreCase(v.getCodigoVentaEntrada())) {
                    return v;
                }
            }
        }
        throw new VentaNoEncontradaException("Venta con código [" + codigoVenta + "] no fue encontrada en el sistema.");
    }

    public void cancelarVenta() {
        this.estadoVenta = EstadoVenta.CANCELADA;
    }

    // Getters y Setters
    public String getCodigoVentaEntrada() {
        return codigoVentaEntrada;
    }

    public void setCodigoVentaEntrada(String codigoVentaEntrada) {
        this.codigoVentaEntrada = codigoVentaEntrada;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(String horaVenta) {
        this.horaVenta = horaVenta;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public EstadoVenta getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(EstadoVenta estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    @Override
    public String toString() {
        return "VentaEntrada{" +
                "codigoVentaEntrada='" + codigoVentaEntrada + '\'' +
                ", fechaVenta='" + fechaVenta + '\'' +
                ", horaVenta='" + horaVenta + '\'' +
                ", valorPagado=" + valorPagado +
                ", estadoVenta=" + estadoVenta +
                ", funcion=" + (funcion != null ? funcion.getCodigoFuncion() : "N/A") +
                '}';
    }
}
