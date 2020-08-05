package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.dto.CreateCuentaDto;
import es.eoi.mundobancario.dto.CuentaDto;

public interface CuentaService {
	
	public List<CuentaDto> findAll();
	public CuentaDto findCuentaByNumCuenta(Integer id);
	public void create(CreateCuentaDto dto);	
	public void updateAlias(Integer id, String alias);	
	public List<CuentaDto> findCuentaWithNegativeSaldo();
	public void updateSaldo(Integer id, Double importe, String tipoMovimiento);

}
