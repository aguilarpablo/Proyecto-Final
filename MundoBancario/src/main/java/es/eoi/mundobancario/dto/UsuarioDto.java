package es.eoi.mundobancario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
	
	//Dto para login
	
	private String usuario;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String pass;

}
