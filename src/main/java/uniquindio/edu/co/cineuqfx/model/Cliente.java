package uniquindio.edu.co.cineuqfx.model;

import java.util.List;

public class Cliente {
    private String identificacion;
    private String nombre;
    private TipoCliente tipoCliente;
    private Beneficio beneficio;

    /**
     * Metodo constructor de la clase Cliente con beneficio asignado.
     * @param identificacion Número de identificación del cliente.
     * @param nombre         Nombre completo del cliente.
     * @param tipoCliente    Categoría o tipo de cliente.
     * @param beneficio      Beneficio asociado al cliente
     */
    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente, Beneficio beneficio) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
        this.beneficio = beneficio;
    }

    /**
     * Metodo Constructor de la clase Cliente sin beneficio inicial.
     *
     * @param identificacion Número de identificación del cliente.
     * @param nombre         Nombre completo del cliente.
     * @param tipoCliente    Categoría o tipo de cliente.
     */
    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente) {
        this(identificacion, nombre, tipoCliente, null);
    }


    /**
     * Metodo para Calcular el valor aplicando el beneficio del cliente si tiene uno asignado.
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
     * Metodo para Verificar si el cliente ya está registrado en la lista.
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
     * Metodo para Registrar el cliente si aún no existe; si ya está registrado lanza excepción.
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
     * Metodo para Eliminar el cliente de la lista si existe; de lo contrario lanza excepción.
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
     * Metodo que cambia o asciende el tipo de cliente.
     *
     * @param nuevoTipo Nuevo tipo de cliente asignado.
     */
    public void ascenderTipoCliente(TipoCliente nuevoTipo) {
        if (nuevoTipo != null) {
            this.tipoCliente = nuevoTipo;
        }
    }


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

    public Beneficio getBeneficio() {
        return beneficio;
    }


    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }


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