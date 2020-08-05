package es.eoi.mundobancario.service;

import java.util.Date;
import java.util.List;

import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Prestamo;

public interface AmortizacionService {

	public void create(Prestamo dto);
	
	public List<Amortizacion> findByFecha(Date fecha);
}
