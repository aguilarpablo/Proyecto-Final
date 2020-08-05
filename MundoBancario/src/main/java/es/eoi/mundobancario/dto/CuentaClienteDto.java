package es.eoi.mundobancario.dto;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class CuentaClienteDto {
	
	//Dto para mostrar las cuentas de un cliente

	private Integer numCuenta;

	private String alias;

	private Double saldo;

}
