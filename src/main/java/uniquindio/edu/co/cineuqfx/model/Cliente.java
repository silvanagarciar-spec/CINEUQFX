package uniquindio.edu.co.cineuqfx.model;


import java.util.List;

public class Cliente {
    private String identificacion;
    private String nombre;
    private TipoCliente tipoCliente;

    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
    }

    /**
     *  Verifica si el cliente ya está registrado en la lista
     * @param clientes
     * @return
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
     * Registra el cliente si aún no existe
     * @param clientes
     * @return
     */

    public boolean registrarCliente(List<Cliente> clientes) {
        if (clientes == null) {
            return false;
        }
        if (verificarCliente(clientes)) {
            return false;
        }
        clientes.add(this);
        return true;
    }

    // Elimina el cliente de la lista si existe
    public boolean eliminarCliente(List<Cliente> clientes) {
        if (clientes == null || clientes.size() == 0) {
            return false;
        }
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdentificacion().equals(this.identificacion)) {
                clientes.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Cambia o asciende el tipo de cliente
     * @param nuevoTipo
     */

    public void ascenderTipoCliente(TipoCliente nuevoTipo) {
        if (nuevoTipo != null) {
            this.tipoCliente = nuevoTipo;
        }
    }

    /**
     * setters y getters
     * @return
     */

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}