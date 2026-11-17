package uniquindio.edu.co.cineuqfx.model;


public class Convenio extends Beneficio {
    private String nombreEntidad;
    private TipoEntidad tipoEntidad;
    private double porcentajeDescuento;

    /**
     * Metodo Constructor de la clase convenio
     * @param codigoBeneficio codigo del beneficio
     * @param nombreBeneficio  nombre del beneficio
     * @param nombreEntidad nombre de la entidad
     * @param tipoEntidad tipo de entidad
     * @param porcentajeDescuento porcentage del descuento
     */
    public Convenio(int codigoBeneficio, String nombreBeneficio, String nombreEntidad, TipoEntidad tipoEntidad, double porcentajeDescuento) {
        super(codigoBeneficio, nombreBeneficio);
        if (nombreEntidad == null || nombreEntidad.trim().isEmpty()) {
            throw new BeneficioNoValidoException("El nombre de la entidad en convenio no puede estar vacío.");
        }
        if (tipoEntidad == null) {
            throw new BeneficioNoValidoException("Debe seleccionar un tipo de entidad.");
        }
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new BeneficioNoValidoException("El porcentaje de descuento debe estar entre 0% y 100%. Valor: " + porcentajeDescuento);
        }
        this.nombreEntidad = nombreEntidad;
        this.tipoEntidad = tipoEntidad;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    /**
     * Metodo para aplicar el beneficio
     * @param valorBase de la entrada.
     */
    @Override
    public double aplicarBeneficio(double valorBase) {
        if (valorBase <= 0) {
            throw new BeneficioNoValidoException("El valor base debe ser mayor a 0.");
        }
        double factor = (porcentajeDescuento > 1.0) ? (porcentajeDescuento / 100.0) : porcentajeDescuento;
        double valorFinal = valorBase - (valorBase * factor);
        return Math.max(0.0, valorFinal);
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        if (nombreEntidad == null || nombreEntidad.trim().isEmpty()) {
            throw new BeneficioNoValidoException("El nombre de la entidad no puede estar vacío.");
        }
        this.nombreEntidad = nombreEntidad;
    }

    public TipoEntidad getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(TipoEntidad tipoEntidad) {
        if (tipoEntidad == null) {
            throw new BeneficioNoValidoException("El tipo de entidad no puede ser nulo.");
        }
        this.tipoEntidad = tipoEntidad;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new BeneficioNoValidoException("El porcentaje de descuento debe estar entre 0% y 100%.");
        }
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public String toString() {
        return "Convenio " + nombreEntidad + " (" + tipoEntidad + " - " + String.format("%.0f%%", porcentajeDescuento) + ")";
    }
}
