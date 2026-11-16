package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

public class Sala {
    public int codigoSala;
    public String nombreSala;
    public int capacidadSala;
    public int nAsientos;
    private EstadoSala estadoSala;
    private TipoSala tipoSala;
    private List<Asiento> listSalaAsientos;

    /**
     * Metodo constructor de sala
     * @param codigoSala
     * @param nombreSala
     * @param capacidadSala
     * @param nAsientos
     * @param estadoSala
     * @param tipoSala
     * @param listSalaAsientos
     */
    public Sala(int codigoSala, String nombreSala, int capacidadSala, int nAsientos, EstadoSala estadoSala, TipoSala tipoSala, List<Asiento> listSalaAsientos) {
        this.codigoSala = codigoSala;
        this.nombreSala = nombreSala;
        this.capacidadSala = capacidadSala;
        this.nAsientos = nAsientos;
        this.estadoSala = estadoSala;
        this.tipoSala = tipoSala;
        this.listSalaAsientos = listSalaAsientos;
    }

    /**
     * Metodos para la clase sala
     */

    /**
     * Modifica los atributos propios de la sala.
     */
    public void actualizarInformacion(String nombreSala, int capacidadSala, int nAsientos, TipoSala tipoSala) {
        this.nombreSala = nombreSala;
        this.capacidadSala = capacidadSala;
        this.nAsientos = nAsientos;
        this.tipoSala = tipoSala;
    }

    /**
     * Cambia el estado de la sala a FUERA_DE_SERVICIO.
     */
    public void deshabilitar() {
        this.estadoSala = EstadoSala.FUERA_DE_SERVICIO;
    }

    /**
     * Cambia el estado de la sala a DISPONIBLE.
     */
    public void habilitar() {
        this.estadoSala = EstadoSala.DISPONIBLE;
    }

    /**
     * Verifica si la sala está disponible para programar funciones.
     */
    public boolean estaDisponible() {
        return this.estadoSala == EstadoSala.DISPONIBLE;
    }

    /**
     * Agrega un asiento ya construido a la lista de asientos de la sala
     * y actualiza el contador nAsientos.
     */
    public void agregarAsiento(Asiento asiento) {
        if (this.listSalaAsientos == null) {
            this.listSalaAsientos = new java.util.ArrayList<>();
        }
        this.listSalaAsientos.add(asiento);
        this.nAsientos = this.listSalaAsientos.size();
    }

    /**
     * getters y setters de sala
     * @return
     */
    public int getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(int codigoSala) {
        this.codigoSala = codigoSala;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public int getCapacidadSala() {
        return capacidadSala;
    }

    public void setCapacidadSala(int capacidadSala) {
        this.capacidadSala = capacidadSala;
    }

    public int getnAsientos() {
        return nAsientos;
    }

    public void setnAsientos(int nAsientos) {
        this.nAsientos = nAsientos;
    }

    public EstadoSala getEstadoSala() {
        return estadoSala;
    }

    public void setEstadoSala(EstadoSala estadoSala) {
        this.estadoSala = estadoSala;
    }

    public TipoSala getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(TipoSala tipoSala) {
        this.tipoSala = tipoSala;
    }

    public List<Asiento> getListSalaAsientos() {
        return listSalaAsientos;
    }

    public void setListSalaAsientos(List<Asiento> listSalaAsientos) {
        this.listSalaAsientos = listSalaAsientos;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "codigoSala=" + codigoSala +
                ", nombreSala='" + nombreSala + '\'' +
                ", capacidadSala=" + capacidadSala +
                ", nAsientos=" + nAsientos +
                ", estadoSala=" + estadoSala +
                ", tipoSala=" + tipoSala +
                ", listSalaAsientos=" + listSalaAsientos +
                '}';
    }
}
