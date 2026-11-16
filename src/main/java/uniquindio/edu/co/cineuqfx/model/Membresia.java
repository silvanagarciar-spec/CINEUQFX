package uniquindio.edu.co.cineuqfx.model;

public class Membresia extends Beneficio {
    private int puntosAcumulados;
    private NivelMembresia nivel;

    public Membresia(int codigoBeneficio, String nombreBeneficio, int puntosAcumulados, NivelMembresia nivel) {
        super(codigoBeneficio, nombreBeneficio);
        this.puntosAcumulados = puntosAcumulados;
        this.nivel = nivel;
    }

    @Override
    public double aplicarBeneficio(double valorBase) {
        double porcentaje = 0.0;

        switch (nivel) {
            case BRONCE:
                porcentaje = valorBase / 0.10;
                break;
            case PLATA:
                porcentaje = valorBase / 0.20;
                break;
            case ORO:
                porcentaje = valorBase / 0.30;
                break;
        }
        return valorBase - (valorBase * porcentaje);
    }
    //Metodo para acumular puntos por compra
    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    //Getter y Setters
    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public NivelMembresia getNivel() {
        return nivel;
    }

    public void setNivel(NivelMembresia nivel) {
        this.nivel = nivel;
    }


}
