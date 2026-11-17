package uniquindio.edu.co.cineuqfx.model;

/**
 * Clase base abstracta para los beneficios que puede tener un cliente en Cinema UQ.
 */
public abstract class Beneficio {
    private int codigoBeneficio;
    private String nombreBeneficio;

    public Beneficio(int codigoBeneficio, String nombreBeneficio) {
        if (codigoBeneficio <= 0) {
            throw new BeneficioNoValidoException("El código del beneficio debe ser un número positivo.");
        }
        if (nombreBeneficio == null || nombreBeneficio.trim().isEmpty()) {
            throw new BeneficioNoValidoException("El nombre del beneficio es obligatorio.");
        }
        this.codigoBeneficio = codigoBeneficio;
        this.nombreBeneficio = nombreBeneficio;
    }

    /**
     * Aplica el beneficio sobre el valor base de la entrada.
     * @param valorBase Valor base de la entrada.
     * @return Valor con el beneficio aplicado.
     */
    public abstract double aplicarBeneficio(double valorBase);

    // Getters y Setters
    public int getCodigoBeneficio() {
        return codigoBeneficio;
    }

    public void setCodigoBeneficio(int codigoBeneficio) {
        if (codigoBeneficio <= 0) {
            throw new BeneficioNoValidoException("El código del beneficio debe ser un número positivo.");
        }
        this.codigoBeneficio = codigoBeneficio;
    }

    public String getNombreBeneficio() {
        return nombreBeneficio;
    }

    public void setNombreBeneficio(String nombreBeneficio) {
        if (nombreBeneficio == null || nombreBeneficio.trim().isEmpty()) {
            throw new BeneficioNoValidoException("El nombre del beneficio es obligatorio.");
        }
        this.nombreBeneficio = nombreBeneficio;
    }
}
