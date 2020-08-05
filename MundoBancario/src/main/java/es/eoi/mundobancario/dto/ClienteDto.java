package es.eoi.mundobancario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto {
	
	//Dto para visualizar cliente sin contraseña
	
	private Integer id;
	
	private String usuario;

	private String nombre;

	private String email;

}
