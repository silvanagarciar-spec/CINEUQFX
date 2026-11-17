package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

/**
 * Representa la venta de una entrada para una función, asociada a un cliente y un asiento.
 */
public class VentaEntrada {
    private String codigoVentaEntrada;
    private String fechaVenta;
    private String horaVenta;
    private double valorPagado;
    private EstadoVenta estado;
    private Cliente cliente;
    private Asiento asiento;
    private Funcion funcion;

    /**
     * Constructor de la clase VentaEntrada.
     * @param codigoVentaEntrada Código único de la venta.
     * @param cliente Cliente que realiza la compra.
     * @param asiento Asiento asignado a la entrada.
     * @param funcion Función de cine seleccionada.
     */
    public VentaEntrada(String codigoVentaEntrada, Cliente cliente, Asiento asiento, Funcion funcion) {
        this.codigoVentaEntrada = codigoVentaEntrada;
        this.cliente = cliente;
        this.asiento = asiento;
        this.funcion = funcion;
        this.estado = EstadoVenta.ACTIVA;
    }

    /**
     * Registra la fecha en que se realiza la venta.
     * @param fechaVenta Fecha de la venta.
     */
    public void registrarFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    /**
     * Registra la hora en que se realiza la venta.
     * @param horaVenta Hora de la venta.
     */
    public void registrarHoraVenta(String horaVenta) {
        this.horaVenta = horaVenta;
    }

    /**
     * Calcula y asigna el valor total de la entrada según el tipo de cliente.
     * @param valorBase Tarifa base de la función.
     * @return Valor final pagado por la entrada.
     */
    public double calcularValorTotalEntrada(double valorBase) {
        if (cliente.getTipoCliente() == TipoCliente.ESTUDIANTE) {
            valorPagado = 12000;
        } else if (cliente.getTipoCliente() == TipoCliente.NINO) {
            valorPagado = 10000;
        } else if (cliente.getTipoCliente() == TipoCliente.ADULTO_MAYOR) {
            valorPagado = 11000;
        } else {
            valorPagado = valorBase;
        }
        return valorPagado;
    }

    /**
     * Procesa la venta ocupando el asiento, registrando fecha/hora y calculando el valor.
     * @param fecha Fecha de la venta.
     * @param hora Hora de la venta.
     * @param valorBase Tarifa base de la función.
     */
    public void procesarVenta(String fecha, String hora, double valorBase) {
        this.asiento.ocuparAsiento();
        registrarFechaVenta(fecha);
        registrarHoraVenta(hora);
        calcularValorTotalEntrada(valorBase);
        this.estado = EstadoVenta.ACTIVA;
    }

    /**
     * Busca una venta por código, la marca como CANCELADA y libera su asiento.
     * @param ventas Lista de ventas registradas.
     * @param codigoVenta Código de la venta a cancelar.
     */
    public static void cancelarVenta(List<VentaEntrada> ventas, String codigoVenta) {
        if (ventas == null || ventas.size() == 0) {
            throw new VentaNoEncontradaException("No existe la venta: " + codigoVenta);
        }
        for (int i = 0; i < ventas.size(); i++) {
            VentaEntrada v = ventas.get(i);
            if (v.getCodigoVentaEntrada().equals(codigoVenta)) {
                v.setEstado(EstadoVenta.CANCELADA);
                v.getAsiento().liberarAsiento();
                return;
            }
        }
        throw new VentaNoEncontradaException("No existe la venta: " + codigoVenta);
    }

    /**
     * Obtiene el código de la venta.
     * @return Código de la venta.
     */
    public String getCodigoVentaEntrada() {
        return codigoVentaEntrada;
    }

    /**
     * Establece el código de la venta.
     * @param codigoVentaEntrada Nuevo código de venta.
     */
    public void setCodigoVentaEntrada(String codigoVentaEntrada) {
        this.codigoVentaEntrada = codigoVentaEntrada;
    }

    /**
     * Obtiene la fecha de la venta.
     * @return Fecha de la venta.
     */
    public String getFechaVenta() {
        return fechaVenta;
    }

    /**
     * Establece la fecha de la venta.
     * @param fechaVenta Nueva fecha de venta.
     */
    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    /**
     * Obtiene la hora de la venta.
     * @return Hora de la venta.
     */
    public String getHoraVenta() {
        return horaVenta;
    }

    /**
     * Establece la hora de la venta.
     * @param horaVenta Nueva hora de venta.
     */
    public void setHoraVenta(String horaVenta) {
        this.horaVenta = horaVenta;
    }

    /**
     * Obtiene el valor pagado por la entrada.
     * @return Valor pagado.
     */
    public double getValorPagado() {
        return valorPagado;
    }

    /**
     * Establece el valor pagado por la entrada.
     * @param valorPagado Nuevo valor pagado.
     */
    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    /**
     * Obtiene el estado actual de la venta.
     * @return Estado de la venta.
     */
    public EstadoVenta getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la venta.
     * @param estado Nuevo estado de la venta.
     */
    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el cliente asociado a la venta.
     * @return Cliente de la venta.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado a la venta.
     * @param cliente Nuevo cliente.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el asiento asignado a la venta.
     * @return Asiento de la venta.
     */
    public Asiento getAsiento() {
        return asiento;
    }

    /**
     * Establece el asiento asignado a la venta.
     * @param asiento Nuevo asiento.
     */
    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    /**
     * Obtiene la función asociada a la venta.
     * @return Función de la venta.
     */
    public Funcion getFuncion() {
        return funcion;
    }

    /**
     * Establece la función asociada a la venta.
     * @param funcion Nueva función.
     */
    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    /**
     * Devuelve el resumen en texto de la venta de entrada.
     * @return Cadena con los detalles principales de la venta.
     */
    @Override
    public String toString() {
        return "Venta " + codigoVentaEntrada + " | " + cliente.getNombre() +
                " | Asiento: " + asiento.getCodigoAsiento() + " | $" + valorPagado + " | " + estado;
    }
}
