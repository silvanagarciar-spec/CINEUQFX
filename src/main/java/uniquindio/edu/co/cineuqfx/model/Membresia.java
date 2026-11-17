package uniquindio.edu.co.cineuqfx.model;

/**
 * Beneficio de Membresía de cliente (BRONCE: 10%, PLATA: 20%, ORO: 30% de descuento)
 * con acumulación de puntos por compras.
 */
public class Membresia extends Beneficio {
    private int puntosAcumulados;
    private NivelMembresia nivel;

    public Membresia(int codigoBeneficio, String nombreBeneficio, int puntosAcumulados, NivelMembresia nivel) {
        super(codigoBeneficio, nombreBeneficio);
        if (puntosAcumulados < 0) {
            throw new BeneficioNoValidoException("Los puntos acumulados no pueden ser negativos.");
        }
        if (nivel == null) {
            throw new BeneficioNoValidoException("Debe seleccionar un nivel de membresía.");
        }
        this.puntosAcumulados = puntosAcumulados;
        this.nivel = nivel;
    }

    public Membresia(int codigoBeneficio, String nombreBeneficio, NivelMembresia nivel) {
        this(codigoBeneficio, nombreBeneficio, 0, nivel);
    }

    @Override
    public double aplicarBeneficio(double valorBase) {
        if (valorBase <= 0) {
            throw new BeneficioNoValidoException("El valor base debe ser mayor a 0.");
        }
        if (nivel == null) {
            throw new BeneficioNoValidoException("Nivel de membresía no definido.");
        }

        double porcentaje = switch (nivel) {
            case BRONCE -> 0.10; // 10% descuento
            case PLATA -> 0.20;  // 20% descuento
            case ORO -> 0.30;    // 30% descuento
        };

        return valorBase - (valorBase * porcentaje);
    }

    /**
     * Acumula puntos a la membresía por compras de boletos o servicios.
     * @param puntos Cantidad de puntos a sumar.
     */
    public void acumularPuntos(int puntos) {
        if (puntos < 0) {
            throw new BeneficioNoValidoException("No se pueden acumular puntos negativos.");
        }
        this.puntosAcumulados += puntos;
    }

    /**
     * Canjea o descuenta puntos de la membresía.
     * @param puntos Cantidad de puntos a redimir.
     */
    public void canjearPuntos(int puntos) {
        if (puntos < 0) {
            throw new BeneficioNoValidoException("No se pueden canjear puntos negativos.");
        }
        if (puntos > this.puntosAcumulados) {
            throw new BeneficioNoValidoException("Puntos insuficientes. Disponibles: " + this.puntosAcumulados);
        }
        this.puntosAcumulados -= puntos;
    }

    /**
     * Calcula el descuento en dinero obtenido por canjear los puntos acumulados:
     * - $500 COP de descuento por cada 100 puntos.
     * - Si tiene más de 1000 puntos, se le descuentan $2.000 COP adicionales al total de la compra.
     *
     * @return Valor del descuento por puntos en pesos colombianos.
     */
    public double calcularDescuentoPorPuntos() {
        if (this.puntosAcumulados < 100) {
            return 0.0;
        }
        int bloques100 = this.puntosAcumulados / 100;
        double descuento = bloques100 * 500.0;
        if (this.puntosAcumulados > 1000) {
            descuento += 2000.0;
        }
        return descuento;
    }

    // Getters y Setters
    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        if (puntosAcumulados < 0) {
            throw new BeneficioNoValidoException("Los puntos no pueden ser negativos.");
        }
        this.puntosAcumulados = puntosAcumulados;
    }

    public NivelMembresia getNivel() {
        return nivel;
    }

    public void setNivel(NivelMembresia nivel) {
        if (nivel == null) {
            throw new BeneficioNoValidoException("El nivel de membresía no puede ser nulo.");
        }
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Membresía " + nivel + " (" + puntosAcumulados + " pts)";
    }
}
