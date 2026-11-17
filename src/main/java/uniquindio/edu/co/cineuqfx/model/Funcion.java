package uniquindio.edu.co.cineuqfx.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Funcion {

    private String codigoFuncion;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate fecha;
    private LocalTime hora;
    private double valorEntrada;
    private String asiento;
    private EstadoFuncion estadoFuncion;
    private List<VentaEntrada> listFuncionVentaEntradas;

    public Funcion() {
        this.listFuncionVentaEntradas = new ArrayList<>();
        this.estadoFuncion = EstadoFuncion.PROGRAMADA;
    }

    /**
     * Metodo constructor de la clase funcion
     * @param codigoFuncion codigo de la clase funcion
     * @param pelicula pelicula de la clase funcion
     * @param sala sala de la clase funcion
     * @param fecha fecha de la clase funcion
     * @param hora hora de la clase funcion
     * @param valorEntrada  valor de entrada de la clase funcion
     * @param asiento asiento de la clase duncion
     */
    public Funcion(String codigoFuncion, Pelicula pelicula, Sala sala, LocalDate fecha,
                   LocalTime hora, double valorEntrada, String asiento) {
        this.codigoFuncion = codigoFuncion;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.valorEntrada = valorEntrada;
        this.asiento = asiento;
        this.estadoFuncion = EstadoFuncion.PROGRAMADA;
        this.listFuncionVentaEntradas = new ArrayList<>();
    }

    /**
     * Metodo para verificar si una función con el código dado existe
     */
    public static boolean verificarFuncionExiste(String codigoFuncion, List<Funcion> funciones) {
        if (funciones == null || codigoFuncion == null) {
            return false;
        }
        for (Funcion f : funciones) {
            if (f != null && codigoFuncion.equalsIgnoreCase(f.getCodigoFuncion())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para verificar que la función exista y esté programada.
     */
    public boolean verificarFuncionExiste() {
        return this.codigoFuncion != null && !this.codigoFuncion.trim().isEmpty() && this.estadoFuncion != null;
    }

    /**
     * Metodo para agregaar una venta
     * @param venta agregar una venta
     */
    public void agregarVenta(VentaEntrada venta) {
        if (this.listFuncionVentaEntradas == null) {
            this.listFuncionVentaEntradas = new ArrayList<>();
        }
        this.listFuncionVentaEntradas.add(venta);
    }

    public String getCodigoFuncion() {
        return codigoFuncion;
    }

    public void setCodigoFuncion(String codigoFuncion) {
        this.codigoFuncion = codigoFuncion;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public EstadoFuncion getEstadoFuncion() {
        return estadoFuncion;
    }

    public void setEstadoFuncion(EstadoFuncion estadoFuncion) {
        this.estadoFuncion = estadoFuncion;
    }

    public List<VentaEntrada> getListFuncionVentaEntradas() {
        return listFuncionVentaEntradas;
    }

    public void setListFuncionVentaEntradas(List<VentaEntrada> listFuncionVentaEntradas) {
        this.listFuncionVentaEntradas = listFuncionVentaEntradas;
    }

    @Override
    public String toString() {
        return "Funcion{" +
                "codigoFuncion='" + codigoFuncion + '\'' +
                ", pelicula=" + pelicula +
                ", sala=" + sala +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", valorEntrada=" + valorEntrada +
                ", asiento='" + asiento + '\'' +
                ", estadoFuncion=" + estadoFuncion +
                '}';
    }
}
