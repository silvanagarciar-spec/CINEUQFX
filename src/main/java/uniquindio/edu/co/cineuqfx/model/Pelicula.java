package uniquindio.edu.co.cineuqfx.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una película dentro del catálogo de CINEMAUQ.
 */
public class Pelicula {
    private String nombrePelicula;
    private int codigoPelicula;
    private String genero;
    private int duracionMinutos;
    private EstadoPelicula estadoPelicula;
    private ClasificacionPelicula clasificacionPelicula;
    private List<Sala> listSalaPelicula;

    /**
     * Constructor de la clase Pelicula.
     */
    public Pelicula(String nombrePelicula, int codigoPelicula, String genero, int duracionMinutos,
                    ClasificacionPelicula clasificacionPelicula) {
        this.nombrePelicula = nombrePelicula;
        this.codigoPelicula = codigoPelicula;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacionPelicula = clasificacionPelicula;
        this.estadoPelicula = EstadoPelicula.DISPONIBLE;
        this.listSalaPelicula = new ArrayList<>();
    }

    /**
     * Filtra y retorna la lista de películas que tienen estado DISPONIBLE.
     *
     * @param peliculas Lista completa de películas.
     * @return Lista de películas disponibles.
     */
    public static List<Pelicula> visualizarPeliculasDisponibles(List<Pelicula> peliculas) {
        List<Pelicula> disponibles = new ArrayList<>();
        if (peliculas == null || peliculas.size() == 0) {
            return disponibles;
        }
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getEstadoPelicula() == EstadoPelicula.DISPONIBLE) {
                disponibles.add(peliculas.get(i));
            }
        }
        return disponibles;
    }

    // Getters y Setters
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

    public List<Sala> getListSalaPelicula() {
        return listSalaPelicula;
    }

    public void setListSalaPelicula(List<Sala> listSalaPelicula) {
        this.listSalaPelicula = listSalaPelicula;
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
                ", listSalaPeliculas=" + listSalaPelicula +
                '}';
    }
}
