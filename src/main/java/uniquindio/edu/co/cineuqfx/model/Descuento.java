package uniquindio.edu.co.cineuqfx.model;

public class Descuento extends Beneficio {
    private double porcentajeDescuento; // descuento propio
    private MotivoDescuento motivo; // descuento propio

    public Descuento(int codigoBeneficio, String nombreBeneficio, double porcentajeDescuento, MotivoDescuento motivo) {
        super(codigoBeneficio, nombreBeneficio); // llama al constructor padre (Beneficio)
        this.porcentajeDescuento = porcentajeDescuento; // para guardar el propio descuento de esta clase hija
        this.motivo = motivo;
    }

    @Override
    public double aplicarBeneficio(double valorBase) {
        double descuento = valorBase * (porcentajeDescuento / 100.0);
        return valorBase - descuento;
    }

    // Getters y Setters
    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public MotivoDescuento getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoDescuento motivo) {
        this.motivo = motivo;
    }
}
