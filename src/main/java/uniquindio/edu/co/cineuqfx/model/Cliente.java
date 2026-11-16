package uniquindio.edu.co.cineuqfx.model;

public class Cliente {
    private String identificacion;
    private String nombre;
    private TipoCliente tipoCliente;

    private Beneficio beneficio; // Puede ser Descuento, Membresia, Convenio o null

    // Constructor completo (con beneficio)
    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente, Beneficio beneficio) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
        this.beneficio = beneficio;
    }

    // Constructor sin beneficio (por si es un cliente nuevo sin descuentos)
    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente) {
        this(identificacion, nombre, tipoCliente, null);
    }

    /**
     * Metodo clave: Calcula el valor a pagar aplicando el beneficio si lo tiene
     * Si no tiene beneficio, paga el valor base completo.
     */
    public double calcularValorConBeneficio(double valorBase) {
        if (beneficio != null) {
            // ¡Aquí ocurre la magia del polimorfismo!
            // Java llama automáticamente al aplicarBeneficio() del hijo que corresponda
            return beneficio.aplicarBeneficio(valorBase);
        }
        return valorBase;
    }

    //Getters y Setters
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

    // Para mostrarlo bonito en la interfaz gráfica (JavaFX)
    @Override
    public String toString() {
        return nombre + " (" + identificacion + ") - " + tipoCliente;
    }
}
