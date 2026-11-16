package uniquindio.edu.co.cineuqfx.model;

public class Convenio extends Beneficio {
    private String nombreEntidad;
    private TipoEntidad tipoEntidad;
    private double porcentajeDescuento;

    public Convenio(int codigoBeneficio, String nombreBeneficio, String nombreEntidad, TipoEntidad tipoEntidad, double porcentajeDescuento) {
        super(codigoBeneficio, nombreBeneficio);
        this.nombreEntidad = nombreEntidad;
        this.tipoEntidad = tipoEntidad;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double aplicarBeneficio(double valorBase) {
        double descuento = valorBase * (porcentajeDescuento / 100.0);
        return valorBase - descuento;
    }
    //Getters y Setters
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public TipoEntidad getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(TipoEntidad tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}
