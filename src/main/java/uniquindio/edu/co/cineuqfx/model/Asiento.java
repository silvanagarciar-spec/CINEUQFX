package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

public class Asiento {
    private String codigoAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    /**
     * Metodo constructor de la clase Asiento
     * @param codigoAsiento codigo del asiento
     * @param fila fila del asiento.
     * @param numero numero del asiento
     */
    public Asiento(String codigoAsiento, String fila, int numero) {
        this.codigoAsiento = codigoAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    /**
     * Metodo para verificar si existe al menos un asiento disponible
     * @param asientos Lista de asientos
     */
    public static boolean verificarQueExistanAsientosDisponibles(List<Asiento> asientos) {
        if (asientos == null || asientos.size() == 0) {
            return false;
        }
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).getEstado() == EstadoAsiento.DISPONIBLE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para verificar que el asiento actual esté disponible
     */
    public void verificarAsientoSeleccionadoDisponible() {
        if (this.estado == EstadoAsiento.OCUPADO) {
            throw new AsientoOcupadoException("El asiento " + codigoAsiento + " está ocupado.");
        }
    }

    /**
     * Metodo para validar disponibilidad y cambiar el estado del asiento a OCUPADO.
     */
    public void ocuparAsiento() {
        verificarAsientoSeleccionadoDisponible();
        this.estado = EstadoAsiento.OCUPADO;
    }


    public void liberarAsiento() {
        this.estado = EstadoAsiento.DISPONIBLE;
    }


    public String getCodigoAsiento() {
        return codigoAsiento;
    }


    public void setCodigoAsiento(String codigoAsiento) {
        this.codigoAsiento = codigoAsiento;
    }


    public String getFila() {
        return fila;
    }


    public void setFila(String fila) {
        this.fila = fila;
    }


    public int getNumero() {
        return numero;
    }


    public void setNumero(int numero) {
        this.numero = numero;
    }


    public EstadoAsiento getEstado() {
        return estado;
    }


    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return codigoAsiento + " - " + estado;
    }
}