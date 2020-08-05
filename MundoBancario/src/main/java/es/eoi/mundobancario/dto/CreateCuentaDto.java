package es.eoi.mundobancario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCuentaDto {
	
	//Dto para crear cuenta

	private String alias;

	private Double saldo;

	private Integer idCliente;

}
