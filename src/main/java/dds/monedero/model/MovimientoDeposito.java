package dds.monedero.model;

import java.time.LocalDate;

public class MovimientoDeposito extends Movimiento {

  public MovimientoDeposito(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  @Override
  public double sumarValor(double saldo) {
    return getMonto();
  }

  public boolean fueDepositado(LocalDate fecha) {
    return esDeLaFecha(fecha);
  }

}
