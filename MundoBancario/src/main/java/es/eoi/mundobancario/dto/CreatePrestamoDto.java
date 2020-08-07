package es.eoi.mundobancario.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePrestamoDto {
	
	@JsonIgnore
	private Integer id;
	
	private String descripcion;

	@JsonIgnore
	private Date fecha;

	private Double importe;

	private Integer plazos;
	
	@JsonIgnore
	private Integer numCuenta;
	
	@JsonIgnore
	private Boolean pagado;

}
