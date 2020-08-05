package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.dto.CreateCuentaDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;

public interface CuentaService {
	
	public List<CuentaDto> findAll();
	public CuentaDto findCuentaByNumCuenta(Integer id);
	public void create(CreateCuentaDto dto);	
	public void updateAlias(Integer id, String alias);	
	public List<CuentaDto> findCuentaWithNegativeSaldo();
	public List<MovimientoDto> findMovimientosCuenta(Integer id);
	public List<PrestamoDto> findPrestamosCuenta(Integer id);
	public List<PrestamoDto> findPrestamosVivosCuenta(Integer id);
	public List<PrestamoDto> findPrestamosAmortizadosCuenta(Integer id);
	public void updateSaldo(Integer id, Double importe, String tipoMovimiento);

}
