package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

/**
 * Representa a un cliente registrado en el sistema de cine CINEMAUQ.
 */
public class Cliente {
    private String identificacion;
    private String nombre;
    private TipoCliente tipoCliente;

    /**
     * Constructor de la clase Cliente.
     * @param identificacion Número de identificación del cliente.
     * @param nombre Nombre completo del cliente.
     * @param tipoCliente Categoría o tipo de cliente.
     */
    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
    }

    /**
     * Verifica si el cliente ya existe en la lista de clientes registrados.
     * @param clientes Lista de clientes del sistema.
     * @return true si el cliente está registrado, false en caso contrario.
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
     * Registra el cliente en la lista si aún no se encuentra almacenado.
     * @param clientes Lista de clientes del sistema.
     */
    public void registrarCliente(List<Cliente> clientes) {
        if (clientes != null && !verificarCliente(clientes)) {
            clientes.add(this);
        }
    }

    /**
     * Elimina el cliente de la lista si existe; de lo contrario lanza una excepción.
     * @param clientes Lista de clientes del sistema.
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
     * Actualiza o asciende la categoría del cliente.
     * @param nuevoTipo Nuevo tipo de cliente asignado.
     */
    public void ascenderTipoCliente(TipoCliente nuevoTipo) {
        if (nuevoTipo != null) {
            this.tipoCliente = nuevoTipo;
        }
    }

    /**
     * Obtiene la identificación del cliente.
     * @return Identificación del cliente.
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * Establece la identificación del cliente.
     * @param identificacion Nueva identificación.
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return Nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el tipo de cliente.
     * @return Tipo de cliente.
     */
    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Establece el tipo de cliente.
     * @param tipoCliente Nuevo tipo de cliente.
     */
    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    /**
     * Devuelve la representación en texto del cliente.
     * @return Cadena con los datos del cliente.
     */
    @Override
    public String toString() {
        return nombre + " (" + identificacion + ") - " + tipoCliente;
    }
}