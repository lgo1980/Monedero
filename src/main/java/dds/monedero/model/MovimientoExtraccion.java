package dds.monedero.model;

import java.time.LocalDate;

public class MovimientoExtraccion extends Movimiento {


  public MovimientoExtraccion(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return esDeLaFecha(fecha);
  }

  @Override
  public double sumarValor(double saldo) {
    return getMonto() * (-1);
  }
}
