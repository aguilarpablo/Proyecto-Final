package es.eoi.mundobancario.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrestamoDto {
	
	private Integer id;

	private String descripcion;

	private Date fecha;

	private Double importe;

	private Integer plazos;
	
	private Boolean pagado;

	private List<AmortizacionDto> amortizaciones;

}
