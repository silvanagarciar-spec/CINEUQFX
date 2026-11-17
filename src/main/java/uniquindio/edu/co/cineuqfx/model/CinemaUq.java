package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

public class CinemaUq {
    public String nombreComercial;
    public int nit;
    public String direccion;

    private List<Pelicula> listCineUqPeliculas;
    private List<Sala> listCineUqSalas;
    private List<Funcion> listCineUqFunciones;
    private List<VentaEntrada> listCineUqVentaEntradas;
    private List<Cliente> listCineUqCliente;

    /**
     * Metodo constructror de Cinema uq
     * @param nombreComercial nombre completo del cine
     * @param nit nit del cine
     * @param direccion direccion del cine
     * @param listCineUqPeliculas listas del cine
     * @param listCineUqSalas listas del cine
     * @param listCineUqFunciones listas del cine
     * @param listCineUqVentaEntradas listas del cine
     * @param listCineUqCliente listas del cine
     */
    public CinemaUq(String nombreComercial, int nit, String direccion, List<Pelicula> listCineUqPeliculas, List<Sala> listCineUqSalas, List<Funcion> listCineUqFunciones, List<VentaEntrada> listCineUqVentaEntradas, List<Cliente> listCineUqCliente) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.listCineUqPeliculas = listCineUqPeliculas;
        this.listCineUqSalas = listCineUqSalas;
        this.listCineUqFunciones = listCineUqFunciones;
        this.listCineUqVentaEntradas = listCineUqVentaEntradas;
        this.listCineUqCliente = listCineUqCliente;
    }


    /**
     * Metodo para registrar pelicula
     * @param nuevaPelicula registrar nueva pelicula
     * @throws Exception
     */
    public void registrarPelicula(Pelicula nuevaPelicula) throws Exception {
        if (buscarPeliculaPorCodigo(nuevaPelicula.getCodigoPelicula()) != null) {
            throw new Exception("Ya existe una película con el código: " + nuevaPelicula.getCodigoPelicula());
        }
        this.listCineUqPeliculas.add(nuevaPelicula);
    }

    /**
     * Metodo para buscar pelicula por codigo
     * @param codigoPelicula codigo de la pelicula
     * @return
     */
    public Pelicula buscarPeliculaPorCodigo(int codigoPelicula) {
        for (Pelicula p : this.listCineUqPeliculas) {
            if (p.getCodigoPelicula() == codigoPelicula) {
                return p;
            }
        }
        return null;
    }

    /**
     * Metodo para modificar pelicula
     * @param codigoPelicula codigo de la pelicula
     * @param nombrePelicula nombre de la pelicula
     * @param genero genero de la pelicula
     * @param duracionMinutos duracion de la pelicula
     * @param clasificacionPelicula  clasificacion de la pelicula
     * @throws Exception excepcion para saber si no se encontro la pelicula con el codigo
     */
    public void modificarPelicula(int codigoPelicula, String nombrePelicula, String genero,
                                  int duracionMinutos, ClasificacionPelicula clasificacionPelicula) throws Exception {
        Pelicula pelicula = buscarPeliculaPorCodigo(codigoPelicula);
        if (pelicula == null) {
            throw new Exception("No se encontró la película con código: " + codigoPelicula);
        }
        pelicula.actualizarInformacion(nombrePelicula, genero, duracionMinutos, clasificacionPelicula);
    }

    /**
     * Metodo para deshabilitar la pelicula
     * @param codigoPelicula codigo de la pelicula
     * @throws Exception
     */
    public void deshabilitarPelicula(int codigoPelicula) throws Exception {
        Pelicula pelicula = buscarPeliculaPorCodigo(codigoPelicula);
        if (pelicula == null) {
            throw new Exception("No se encontró la película con código: " + codigoPelicula);
        }
        pelicula.deshabilitar();
    }

    /**
     * metodo para consultar la disponibilidad de la pelicula
     * @return
     */
    public List<Pelicula> consultarPeliculasDisponibles() {
        List<Pelicula> disponibles = new java.util.ArrayList<>();
        for (Pelicula p : this.listCineUqPeliculas) {
            if (p.estaDisponible()) {
                disponibles.add(p);
            }
        }
        return disponibles;
    }


    /**
     * Metodo para registrar sala
     * @param nuevaSala crear nueva sala
     * @throws Exception
     */
    public void registrarSala(Sala nuevaSala) throws Exception {
        if (buscarSalaPorCodigo(nuevaSala.getCodigoSala()) != null) {
            throw new Exception("Ya existe una sala con el código: " + nuevaSala.getCodigoSala());
        }
        this.listCineUqSalas.add(nuevaSala);
    }

    /**
     * Metodo para buscar sala
     * @param codigoSala codigo de la sala
     * @return
     */
    public Sala buscarSalaPorCodigo(int codigoSala) {
        for (Sala s : this.listCineUqSalas) {
            if (s.getCodigoSala() == codigoSala) {
                return s;
            }
        }
        return null;
    }

    /**
     * Metodo para modificar sala
     * @param codigoSala codigo de la sala
     * @param nombreSala nombre de la sala
     * @param capacidadSala  capacidad de la sala
     * @param nAsientos Numero de la sala
     * @param tipoSala tipo de la sala
     * @throws Exception si no se encuentra la sala con codigo
     */
    public void modificarSala(int codigoSala, String nombreSala, int capacidadSala, int nAsientos, TipoSala tipoSala) throws Exception {
        Sala sala = buscarSalaPorCodigo(codigoSala);
        if (sala == null) {
            throw new Exception("No se encontró la sala con código: " + codigoSala);
        }
        sala.actualizarInformacion(nombreSala, capacidadSala, nAsientos, tipoSala);
    }

    /**
     * Metodo para deshabilitar la sala
     * @param codigoSala de la sala
     * @throws Exception
     */
    public void deshabilitarSala(int codigoSala) throws Exception {
        Sala sala = buscarSalaPorCodigo(codigoSala);
        if (sala == null) {
            throw new Exception("No se encontró la sala con código: " + codigoSala);
        }
        sala.deshabilitar();
    }

    /**
     * Metodo para registrar una función validando que la película y la sala existan y estén disponibles.
     */
    public void agregarFuncion(Funcion funcion, int codigoPelicula, int codigoSala) throws Exception {
        Pelicula pelicula = buscarPeliculaPorCodigo(codigoPelicula);
        if (pelicula == null || !pelicula.estaDisponible()) {
            throw new Exception("La película no existe o no está disponible.");
        }

        Sala sala = buscarSalaPorCodigo(codigoSala);
        if (sala == null) {
            throw new Exception("La sala no existe.");
        }
        if (!sala.estaDisponible()) {
            throw new Exception("No se puede programar la función: la sala " + sala.getNombreSala() + " está FUERA DE SERVICIO.");
        }

        this.listCineUqFunciones.add(funcion);
    }

    /**
     * Metodo para agregar una venta
     * @param venta agrefar una venta
     */
    public void agregarVentaEntrada(VentaEntrada venta) {
        this.listCineUqVentaEntradas.add(venta);
    }

    public void agregarCliente(Cliente cliente) {
        if (!this.listCineUqCliente.contains(cliente)) {
            this.listCineUqCliente.add(cliente);
        }
    }



    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Pelicula> getListCineUqPeliculas() {
        return listCineUqPeliculas;
    }

    public void setListCineUqPeliculas(List<Pelicula> listCineUqPeliculas) {
        this.listCineUqPeliculas = listCineUqPeliculas;
    }

    public List<Sala> getListCineUqSalas() {
        return listCineUqSalas;
    }

    public void setListCineUqSalas(List<Sala> listCineUqSalas) {
        this.listCineUqSalas = listCineUqSalas;
    }

    public List<Funcion> getListCineUqFunciones() {
        return listCineUqFunciones;
    }

    public void setListCineUqFunciones(List<Funcion> listCineUqFunciones) {
        this.listCineUqFunciones = listCineUqFunciones;
    }

    public List<VentaEntrada> getListCineUqVentaEntradas() {
        return listCineUqVentaEntradas;
    }

    public void setListCineUqVentaEntradas(List<VentaEntrada> listCineUqVentaEntradas) {
        this.listCineUqVentaEntradas = listCineUqVentaEntradas;
    }

    public List<Cliente> getListCineUqCliente() {
        return listCineUqCliente;
    }

    public void setListCineUqCliente(List<Cliente> listCineUqCliente) {
        this.listCineUqCliente = listCineUqCliente;
    }

    @Override
    public String toString() {
        return "CinemaUq{" +
                "nombreComercial='" + nombreComercial + '\'' +
                ", nit=" + nit +
                ", direccion='" + direccion + '\'' +
                ", listCineUqPeliculas=" + listCineUqPeliculas +
                ", listCineUqSalas=" + listCineUqSalas +
                ", listCineUqFunciones=" + listCineUqFunciones +
                ", listCineUqVentaEntradas=" + listCineUqVentaEntradas +
                ", listCineUqCliente=" + listCineUqCliente +
                '}';
    }
}
