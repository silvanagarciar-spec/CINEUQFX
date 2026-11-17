package uniquindio.edu.co.cineuqfx.model;


public abstract class Beneficio {
    private int codigoBeneficio;
    private String nombreBeneficio;

    /**
     * Metodo constructor de la clase beneficio
     * @param codigoBeneficio codigo del beneficio
     * @param nombreBeneficio codigo del beneficio
     */
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
     * Metodo para aplicar el beneficio sobre el valor base de la entrada.
     * @param valorBase de la entrada.
     * @return
     */
    public abstract double aplicarBeneficio(double valorBase);


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
