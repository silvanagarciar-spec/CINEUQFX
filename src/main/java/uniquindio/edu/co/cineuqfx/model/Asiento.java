package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

/**
 * Representa un asiento dentro de una sala o función de cine.
 */
public class Asiento {
    private String codigoAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    /**
     * Constructor de la clase Asiento. Inicia por defecto en estado DISPONIBLE.
     * @param codigoAsiento Código único del asiento (ej. A1).
     * @param fila Letra o identificador de la fila.
     * @param numero Número del asiento dentro de la fila.
     */
    public Asiento(String codigoAsiento, String fila, int numero) {
        this.codigoAsiento = codigoAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    /**
     * Verifica si existe al menos un asiento disponible en la lista dada.
     * @param asientos Lista de asientos de la función.
     * @return true si hay al menos un asiento disponible, false en caso contrario.
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
     * Verifica que el asiento actual esté disponible; si está ocupado lanza excepción.
     */
    public void verificarAsientoSeleccionadoDisponible() {
        if (this.estado == EstadoAsiento.OCUPADO) {
            throw new AsientoOcupadoException("El asiento " + codigoAsiento + " está ocupado.");
        }
    }

    /**
     * Valida disponibilidad y cambia el estado del asiento a OCUPADO.
     */
    public void ocuparAsiento() {
        verificarAsientoSeleccionadoDisponible();
        this.estado = EstadoAsiento.OCUPADO;
    }

    /**
     * Cambia el estado del asiento a DISPONIBLE.
     */
    public void liberarAsiento() {
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    /**
     * Obtiene el código del asiento.
     * @return Código del asiento.
     */
    public String getCodigoAsiento() {
        return codigoAsiento;
    }

    /**
     * Establece el código del asiento.
     * @param codigoAsiento Nuevo código del asiento.
     */
    public void setCodigoAsiento(String codigoAsiento) {
        this.codigoAsiento = codigoAsiento;
    }

    /**
     * Obtiene la fila del asiento.
     * @return Fila del asiento.
     */
    public String getFila() {
        return fila;
    }

    /**
     * Establece la fila del asiento.
     * @param fila Nueva fila.
     */
    public void setFila(String fila) {
        this.fila = fila;
    }

    /**
     * Obtiene el número del asiento.
     * @return Número del asiento.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Establece el número del asiento.
     * @param numero Nuevo número.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el estado actual del asiento.
     * @return Estado del asiento.
     */
    public EstadoAsiento getEstado() {
        return estado;
    }

    /**
     * Establece el estado del asiento.
     * @param estado Nuevo estado del asiento.
     */
    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    /**
     * Devuelve la representación en texto del asiento.
     * @return Código y estado del asiento.
     */
    @Override
    public String toString() {
        return codigoAsiento + " - " + estado;
    }
}