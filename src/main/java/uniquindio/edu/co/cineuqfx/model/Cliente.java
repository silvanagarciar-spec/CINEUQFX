package uniquindio.edu.co.cineuqfx.model;

public class Cliente {


    private Beneficio beneficio; // Puede ser un Descuento, una Membresia o un Convenio


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
            return beneficio.aplicarBeneficio(valorBase);
        }
        return valorBase;
    }
    //Getters y Setters
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
