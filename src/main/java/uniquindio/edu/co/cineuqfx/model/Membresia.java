package uniquindio.edu.co.cineuqfx.model;


public class Membresia extends Beneficio {
    private int puntosAcumulados;
    private NivelMembresia nivel;

    /**
     * Metodo constructor
     * @param codigoBeneficio codigo del beneficio
     * @param nombreBeneficio nombre del beneficio
     * @param puntosAcumulados puntos acumulados
     * @param nivel de la membresia
     */
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

    /**
     * Metodo para aplicar el beneficio
     * @param valorBase de la entrada.
     * @return
     */
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
     * Metodo para Acumular puntos a la membresía por compras de boletos o servicios.
     * @param puntos Cantidad de puntos a sumar.
     */
    public void acumularPuntos(int puntos) {
        if (puntos < 0) {
            throw new BeneficioNoValidoException("No se pueden acumular puntos negativos.");
        }
        this.puntosAcumulados += puntos;
    }

    /**metodo para canjear puntos
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
     * Metodo para Calcular el descuento en dinero obtenido por canjear los puntos acumulados:
     *
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
