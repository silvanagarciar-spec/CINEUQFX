package uniquindio.edu.co.cineuqfx.model;
import java.util.List;

public class Asiento {
    private String codigoAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(String codigoAsiento, String fila, int numero) {
        this.codigoAsiento = codigoAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }
/**
 * verifica que este asiento selecionado disponible

 */

    public boolean verificarAsientoSeleccionadoDisponible() {
        return this.estado == EstadoAsiento.DISPONIBLE;
    }

    /**
     * Verifica que exista al menos un asiento disponible en la colección
     * @param asientos
     * @return
     */

    public static boolean verificarAsientosDisponibles(List<Asiento> asientos) {
        if (asientos == null || asientos.size() == 0) {
            return false;
        }
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).verificarAsientoSeleccionadoDisponible()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cambia el estado del asiento a OCUPADO al vender la entrada
     */

    public void ocuparAsiento() {
        this.estado = EstadoAsiento.OCUPADO;
    }

    /**
     *  Libera el asiento dejándolo DISPONIBLE (por ejemplo, al cancelar una venta)
     */

    public void liberarAsiento() {
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    /**
     *  Getters y Setters
     * @return
     */

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
}
