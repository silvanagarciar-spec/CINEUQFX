package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

public class Pelicula {
    public String nombrePelicula;
    public int codigoPelicula;
    public String genero;
    public int duracionMinutos;
    private EstadoPelicula estadoPelicula;
    private ClasificacionPelicula clasificacionPelicula;
    private List<Pelicula> listSalaPeliculas;

    /**
     * Metodo constructor de pelicula
     * @param nombrePelicula
     * @param codigoPelicula
     * @param genero
     * @param duracionMinutos
     * @param estadoPelicula
     * @param clasificacionPelicula
     * @param listSalaPeliculas
     */
    public Pelicula(String nombrePelicula, int codigoPelicula, String genero, int duracionMinutos, EstadoPelicula estadoPelicula, ClasificacionPelicula clasificacionPelicula, List<Pelicula> listSalaPeliculas) {
        this.nombrePelicula = nombrePelicula;
        this.codigoPelicula = codigoPelicula;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.estadoPelicula = estadoPelicula;
        this.clasificacionPelicula = clasificacionPelicula;
        this.listSalaPeliculas = listSalaPeliculas;
    }

    /**
     * Actualiza la información editable de la película usando sus variables exactas.
     */
    public void actualizarInformacion(String nombrePelicula, String genero, int duracionMinutos, ClasificacionPelicula clasificacionPelicula) {
        this.nombrePelicula = nombrePelicula;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacionPelicula = clasificacionPelicula;
    }

    /**
     * Cambia el estado de la película a NO_DISPONIBLE.
     */
    public void deshabilitar() {
        this.estadoPelicula = EstadoPelicula.NO_DISPONIBLE;
    }

    /**
     * Cambia el estado de la película a DISPONIBLE.
     */
    public void habilitar() {
        this.estadoPelicula = EstadoPelicula.DISPONIBLE;
    }

    /**
     * Verifica si la película está disponible.
     */
    public boolean estaDisponible() {
        return this.estadoPelicula == EstadoPelicula.DISPONIBLE;
    }

    /**
     * getters y setters de clase pelicula
     * @return
     */
    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public int getCodigoPelicula() {
        return codigoPelicula;
    }

    public void setCodigoPelicula(int codigoPelicula) {
        this.codigoPelicula = codigoPelicula;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public EstadoPelicula getEstadoPelicula() {
        return estadoPelicula;
    }

    public void setEstadoPelicula(EstadoPelicula estadoPelicula) {
        this.estadoPelicula = estadoPelicula;
    }

    public ClasificacionPelicula getClasificacionPelicula() {
        return clasificacionPelicula;
    }

    public void setClasificacionPelicula(ClasificacionPelicula clasificacionPelicula) {
        this.clasificacionPelicula = clasificacionPelicula;
    }

    public List<Pelicula> getListSalaPeliculas() {
        return listSalaPeliculas;
    }

    public void setListSalaPeliculas(List<Pelicula> listSalaPeliculas) {
        this.listSalaPeliculas = listSalaPeliculas;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "nombrePelicula='" + nombrePelicula + '\'' +
                ", codigoPelicula=" + codigoPelicula +
                ", genero='" + genero + '\'' +
                ", duracionMinutos=" + duracionMinutos +
                ", estadoPelicula=" + estadoPelicula +
                ", clasificacionPelicula=" + clasificacionPelicula +
                ", listSalaPeliculas=" + listSalaPeliculas +
                '}';
    }
}
