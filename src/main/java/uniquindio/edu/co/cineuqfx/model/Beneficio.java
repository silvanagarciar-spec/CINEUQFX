package uniquindio.edu.co.cineuqfx.model;

public abstract class Beneficio {
    private int codigoBeneficio;
    private String nombreBeneficio;

    public Beneficio(int codigoBeneficio, String nombreBeneficio) {
        this.codigoBeneficio = codigoBeneficio;
        this.nombreBeneficio = nombreBeneficio;
    }
    // METODO ABSTRACT: no tiene llaves porque cada hijo calcula el precio a su manera
    public abstract double aplicarBeneficio(double valorBase);


    //Getters y Setters
    public int getCodigoBeneficio() {
        return codigoBeneficio;
    }

    public void setCodigoBeneficio(int codigoBeneficio) {
        this.codigoBeneficio = codigoBeneficio;
    }
    public String getNombreBeneficio() {
        return nombreBeneficio;
    }
    public void setNombreBeneficio(String nombreBeneficio) {
        this.nombreBeneficio = nombreBeneficio;
    }
}
