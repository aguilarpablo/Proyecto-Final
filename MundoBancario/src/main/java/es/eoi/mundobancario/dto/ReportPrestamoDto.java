package es.eoi.mundobancario.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportPrestamoDto {
	
	private Integer id;

	private String descripcion;

	private Date fecha;

	private Double importe;

	private Integer plazos;

	private List<AmortizacionDto> amortizaciones;
	
	private ClienteDto cliente;

}
