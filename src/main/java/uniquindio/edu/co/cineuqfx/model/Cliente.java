package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

/**
 * Representa a un cliente registrado en el sistema de cine CINEMAUQ.
 */
public class Cliente {
    private String identificacion;
    private String nombre;
    private TipoCliente tipoCliente;
    private Beneficio beneficio;

    /**
     * Constructor de la clase Cliente con beneficio asignado.
     *
     * @param identificacion Número de identificación del cliente.
     * @param nombre         Nombre completo del cliente.
     * @param tipoCliente    Categoría o tipo de cliente.
     * @param beneficio      Beneficio asociado al cliente.
     */
    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente, Beneficio beneficio) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
        this.beneficio = beneficio;
    }

    /**
     * Constructor de la clase Cliente sin beneficio inicial.
     *
     * @param identificacion Número de identificación del cliente.
     * @param nombre         Nombre completo del cliente.
     * @param tipoCliente    Categoría o tipo de cliente.
     */
    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente) {
        this(identificacion, nombre, tipoCliente, null);
    }


    /**
     * Calcula el valor aplicando el beneficio del cliente si tiene uno asignado.
     *
     * @param valorBase Valor base sobre el cual se aplica el beneficio.
     * @return Valor final después de aplicar el beneficio, o el valor base si no tiene.
     */
    public double calcularValorConBeneficio(double valorBase) {
        if (beneficio != null) {
            return beneficio.aplicarBeneficio(valorBase);
        }
        return valorBase;
    }


    /**
     * Verifica si el cliente ya está registrado en la lista.
     *
     * @param clientes Lista de clientes registrados.
     * @return true si el cliente existe en la lista, false en caso contrario.
     */
    public boolean verificarCliente(List<Cliente> clientes) {
        if (clientes == null || clientes.size() == 0) {
            return false;
        }
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdentificacion().equals(this.identificacion)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registra el cliente si aún no existe; si ya está registrado lanza excepción.
     *
     * @param clientes Lista de clientes registrados.
     * @return
     */
    public boolean registrarCliente(List<Cliente> clientes) {
        if (clientes == null) {
            return false;
        }
        if (verificarCliente(clientes)) {
            throw new ClienteYaRegistradoException("El cliente con identificación " + this.identificacion + " ya está registrado.");
        }
        clientes.add(this);
        return false;
    }

    /**
     * Elimina el cliente de la lista si existe; de lo contrario lanza excepción.
     *
     * @param clientes Lista de clientes registrados.
     */
    public void eliminarCliente(List<Cliente> clientes) {
        if (clientes == null || clientes.size() == 0) {
            throw new ClienteNoEncontradoException("Cliente no encontrado: " + this.identificacion);
        }
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdentificacion().equals(this.identificacion)) {
                clientes.remove(i);
                return;
            }
        }
        throw new ClienteNoEncontradoException("Cliente no encontrado: " + this.identificacion);
    }

    /**
     * Cambia o asciende el tipo de cliente.
     *
     * @param nuevoTipo Nuevo tipo de cliente asignado.
     */
    public void ascenderTipoCliente(TipoCliente nuevoTipo) {
        if (nuevoTipo != null) {
            this.tipoCliente = nuevoTipo;
        }
    }

    // Getters y Setters

    /**
     * Obtiene la identificación del cliente.
     *
     * @return Identificación del cliente.
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * Establece la identificación del cliente.
     *
     * @param identificacion Nueva identificación del cliente.
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return Nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre Nuevo nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el tipo de cliente.
     *
     * @return Tipo de cliente.
     */
    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Establece el tipo de cliente.
     *
     * @param tipoCliente Nuevo tipo de cliente.
     */
    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    /**
     * Obtiene el beneficio asociado al cliente.
     *
     * @return Beneficio del cliente.
     */
    public Beneficio getBeneficio() {
        return beneficio;
    }

    /**
     * Establece el beneficio asociado al cliente.
     *
     * @param beneficio Nuevo beneficio del cliente.
     */
    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }

    /**
     * Devuelve una descripción legible del beneficio asignado al cliente.
     */
    public String getDescripcionBeneficio() {
        if (beneficio == null) {
            return "Ninguno";
        }
        if (beneficio instanceof Membresia m) {
            return "Membresía " + m.getNivel() + " (" + m.getPuntosAcumulados() + " pts)";
        }
        if (beneficio instanceof Convenio c) {
            return "Convenio " + c.getNombreEntidad() + " (" + String.format("%.0f%%", c.getPorcentajeDescuento()) + ")";
        }
        if (beneficio instanceof Descuento d) {
            return "Descuento " + d.getMotivo() + " (" + String.format("%.0f%%", d.getPorcentajeDescuento()) + ")";
        }
        return beneficio.getNombreBeneficio();
    }

    /**
     * Devuelve la representación en texto del cliente.
     *
     * @return Cadena con los datos del cliente.
     */
    @Override
    public String toString() {
        return nombre + " (" + identificacion + ") - " + tipoCliente;
    }
}