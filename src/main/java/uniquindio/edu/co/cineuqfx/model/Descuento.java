package uniquindio.edu.co.cineuqfx.model;

/**
 * Beneficio de tipo Descuento por promociones especiales (miércoles de cine, martes estudiante, cumpleaños, etc.).
 */
public class Descuento extends Beneficio {
    private double porcentajeDescuento;
    private MotivoDescuento motivo;

    public Descuento(int codigoBeneficio, String nombreBeneficio, double porcentajeDescuento, MotivoDescuento motivo) {
        super(codigoBeneficio, nombreBeneficio);
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new BeneficioNoValidoException("El porcentaje de descuento debe estar entre 0% y 100%. Valor recibido: " + porcentajeDescuento);
        }
        if (motivo == null) {
            throw new BeneficioNoValidoException("Debe especificar un motivo de descuento válido.");
        }
        this.porcentajeDescuento = porcentajeDescuento;
        this.motivo = motivo;
    }

    /**
     * Constructor con porcentaje por defecto según el motivo.
     */
    public Descuento(int codigoBeneficio, String nombreBeneficio, MotivoDescuento motivo) {
        this(codigoBeneficio, nombreBeneficio, obtenerPorcentajePorDefecto(motivo), motivo);
    }

    private static double obtenerPorcentajePorDefecto(MotivoDescuento motivo) {
        if (motivo == null) {
            throw new BeneficioNoValidoException("El motivo no puede ser nulo.");
        }
        return switch (motivo) {
            case MIERCOLES_CINE -> 20.0;
            case MARTES_ESTUDIANTE -> 25.0;
            case CUMPLEANOS -> 50.0;
        };
    }

    @Override
    public double aplicarBeneficio(double valorBase) {
        if (valorBase <= 0) {
            throw new BeneficioNoValidoException("El valor base debe ser mayor a 0.");
        }
        double factor = (porcentajeDescuento > 1.0) ? (porcentajeDescuento / 100.0) : porcentajeDescuento;
        double valorFinal = valorBase - (valorBase * factor);
        return Math.max(0.0, valorFinal);
    }

    // Getters y Setters
    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new BeneficioNoValidoException("El porcentaje de descuento debe estar entre 0% y 100%.");
        }
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public MotivoDescuento getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoDescuento motivo) {
        if (motivo == null) {
            throw new BeneficioNoValidoException("Debe especificar un motivo de descuento.");
        }
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Descuento{" +
                "nombre='" + getNombreBeneficio() + '\'' +
                ", motivo=" + motivo +
                ", porcentaje=" + porcentajeDescuento + "%" +
                '}';
    }
}
