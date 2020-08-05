package es.eoi.mundobancario.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMovimientoDto {
	
	@JsonIgnore
	private Integer id;

	private String descripcion;

	@JsonIgnore
	private Date fecha;

	private Double importe;
	
	@JsonIgnore
	private Integer numCuenta;
	
	@JsonIgnore
	private Integer idTipo;

}
