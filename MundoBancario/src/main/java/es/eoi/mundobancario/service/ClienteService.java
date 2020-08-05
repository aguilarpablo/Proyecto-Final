package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CreateClienteDto;
import es.eoi.mundobancario.dto.CuentaClienteDto;
import es.eoi.mundobancario.dto.ReportClienteDto;

public interface ClienteService {
	
	public List<ClienteDto> findAll();
	public ReportClienteDto findById(Integer id);
	public ClienteDto findClienteById(Integer id);
	public void create(CreateClienteDto dto);	
	public void updateEmail(String email, Integer id);
	public List<CuentaClienteDto> findCuentasClienteById(Integer id);
	public ClienteDto login(String usuario, String pass);

}
