package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import java.time.LocalDate;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;
  private Movimiento deposito;
  private Movimiento extraccion;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
    deposito = new MovimientoDeposito(LocalDate.now(), 300);
    extraccion = new MovimientoExtraccion(LocalDate.now(), 300);
  }

  @Test
  void Poner() {
    cuenta.poner(1500);
    assertEquals(1500, cuenta.getSaldo());
  }

  @Test
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    assertEquals(3856, cuenta.getSaldo());
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
      cuenta.poner(1500);
      cuenta.poner(456);
      cuenta.poner(1900);
      cuenta.poner(245);
    });
  }

  @Test
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
      cuenta.setSaldo(90);
      cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }

  @Test
  public void calcularValorConDeposito() {
    double valor = deposito.calcularValor(cuenta);
    assertEquals(300, valor);
  }

  @Test
  public void calcularValorConExtraccion() {
    double valor = extraccion.calcularValor(cuenta);
    assertEquals(-300, valor);
  }

  @Test
  public void agregarMovimientoDeposito() {
    deposito.agregateA(cuenta);
    assertEquals(300, cuenta.getSaldo());
    assertEquals(1, cuenta.getMovimientos().size());
  }

  @Test
  public void agregarMovimientoExtraccion() {
    extraccion.agregateA(cuenta);
    assertEquals(-300, cuenta.getSaldo());
    assertEquals(1, cuenta.getMovimientos().size());
  }


}