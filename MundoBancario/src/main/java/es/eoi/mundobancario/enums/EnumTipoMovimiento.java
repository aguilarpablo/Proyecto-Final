package es.eoi.mundobancario.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTipoMovimiento {
	
	INGRESO("Ingreso"),
	PRESTAMO("Préstamo"),
	PAGO("Pago"),
	AMORTIZACION("Amortización"),
	INTERES("Interés");
	
	private final String valor;

}
