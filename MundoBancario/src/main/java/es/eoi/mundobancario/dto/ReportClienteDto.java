package es.eoi.mundobancario.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportClienteDto {
	
	private Integer id;

	private String usuario;

	private String nombre;

	private String email;
	
	private List<ReportCuentaDto> cuentas;

}
