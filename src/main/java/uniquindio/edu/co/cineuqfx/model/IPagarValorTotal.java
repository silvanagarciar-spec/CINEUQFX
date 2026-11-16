package uniquindio.edu.co.cineuqfx.model;

public interface IPagarValorTotal {
    void pagarValorTotal(double valor) throws SaldoInsuficienteException;
}
