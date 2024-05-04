package dds.monedero.model;

import java.time.LocalDate;

public abstract class Movimiento {
  private final LocalDate fecha;
  // Nota: En ningún lenguaje de programación usen jamás doubles (es decir, números con punto flotante) para modelar dinero en el mundo real.
  // En su lugar siempre usen numeros de precision arbitraria o punto fijo, como BigDecimal en Java y similares
  // De todas formas, NO es necesario modificar ésto como parte de este ejercicio. 
  private final double monto;
//  private boolean esDeposito;

  /*public Movimiento(LocalDate fecha, double monto, boolean esDeposito) {
    this.fecha = fecha;
    this.monto = monto;
    this.esDeposito = esDeposito;
  }*/
  public Movimiento(LocalDate fecha, double monto) {
    this.fecha = fecha;
    this.monto = monto;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  /*public boolean fueDepositado(LocalDate fecha) {
    return isDeposito() && esDeLaFecha(fecha);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  }*/

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
//    cuenta.agregarMovimiento(fecha, monto, esDeposito);
    cuenta.agregarMovimiento(this);
  }

  /*
  Este es un code smells
  public double calcularValor(Cuenta cuenta) {
    if (esDeposito) {
      return cuenta.getSaldo() + getMonto();
    } else {
      return cuenta.getSaldo() - getMonto();
    }
  }
  public double calcularValor(Cuenta cuenta) {
    double montoARestar = ((esDeposito) ? getMonto() : (-1) * getMonto());
    return cuenta.getSaldo() + montoARestar;
  }
   */

  public double calcularValor(Cuenta cuenta) {
    return cuenta.getSaldo() + sumarValor(cuenta.getSaldo());
  }

  public abstract double sumarValor(double saldo);

}
