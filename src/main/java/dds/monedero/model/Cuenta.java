package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  /*
  Esto creo que es un code smells tanto el nombre del método como del parametro debería llamarse un poco
  más expresivo como ingresar dinero y cuanto → cantidadDinero y la validación tal vez hacerla en una
  función aparte para que el código sea más escalable.
   */
  public void poner(double cuanto) {
    validarQueSeaPositivo(cuanto);

    validarQueNoSePaseLos3Depositos();

    new Movimiento(LocalDate.now(), cuanto, true).agregateA(this);
  }

  private void validarQueNoSePaseLos3Depositos() {
    if (getMovimientos().stream().filter(Movimiento::isDeposito).count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  private static void validarQueSeaPositivo(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  /*
  Esto creo que es un code smells tanto el nombre del método como del parametro debería llamarse un poco
  más expresivo como extraer dinero y cuanto → cantidadDinero y la validación tal vez hacerla en una
  función aparte para que el código sea más escalable.
   */
  public void sacar(double cuanto) {
    validarQueSeaPositivo(cuanto);
    validarQueNoSePuedeSacarMasDineroDelQueHay(cuanto);
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    validarQueNoExedaElLimiteDeDinero(cuanto, limite);
    new Movimiento(LocalDate.now(), cuanto, false).agregateA(this);
  }

  private static void validarQueNoExedaElLimiteDeDinero(double cuanto, double limite) {
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
  }

  private void validarQueNoSePuedeSacarMasDineroDelQueHay(double cuanto) {
    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

  /*
  Esto es un code smells
   */
  /*public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }*/

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
