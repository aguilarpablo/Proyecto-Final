package es.eoi.mundobancario.service;

import es.eoi.mundobancario.dto.CreateMovimientoDto;
import es.eoi.mundobancario.dto.CreatePrestamoDto;
import es.eoi.mundobancario.entity.Amortizacion;

public interface MovimientoService {
	
	public void createMovimientoPrestamo(CreatePrestamoDto dto, Integer idTipoMovimiento);
	
	public void create(CreateMovimientoDto dto);
	
	public void amortizacion(Amortizacion amortizacion, Integer tipoMovimiento);
	
	public void intereses(Amortizacion amortizacion, Integer tipoMovimiento);

}
