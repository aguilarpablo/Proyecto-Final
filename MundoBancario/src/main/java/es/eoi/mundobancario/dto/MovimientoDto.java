package es.eoi.mundobancario.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimientoDto implements Comparable<MovimientoDto> {

	private Integer id;

	private String descripcion;

	private Date fecha;

	private Double importe;

	private TipoMovimientoDto tipoMovimiento;

	@Override
	public int compareTo(MovimientoDto o) {
		return getFecha().compareTo(o.getFecha());
	}

	
}
