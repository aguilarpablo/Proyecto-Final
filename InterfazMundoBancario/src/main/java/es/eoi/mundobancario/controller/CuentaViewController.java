package es.eoi.mundobancario.controller;

import org.springframework.http.ResponseEntity;

import es.eoi.mundobancario.InterfazMundoBancarioApplication;
import es.eoi.mundobancario.dto.CuentaClienteDto;

public class CuentaViewController {
	
	private static final String URL_CUENTAS = "/cuentas";
	private static final String URL_CLIENTES = "/clientes/";
	
	public static CuentaClienteDto[] findCuentasCliente(Integer id) {
		String url = InterfazMundoBancarioApplication.URL_GATEWAY.
				concat(URL_CLIENTES).concat(id.toString()).concat(URL_CUENTAS);
		
		ResponseEntity<CuentaClienteDto[]> response = InterfazMundoBancarioApplication.restTemplate.
				getForEntity(url, CuentaClienteDto[].class);
		
		return response.getBody();
	}
	
	public static void updateAlias(Integer id, String alias) {
		String urlUpdateAlias = InterfazMundoBancarioApplication.URL_GATEWAY.
				concat(URL_CUENTAS).concat("/").concat(id.toString());
		InterfazMundoBancarioApplication.restTemplate.put(urlUpdateAlias, alias);
	}

}
