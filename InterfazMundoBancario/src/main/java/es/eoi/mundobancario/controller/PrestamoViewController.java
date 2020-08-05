package es.eoi.mundobancario.controller;

import org.springframework.http.ResponseEntity;

import es.eoi.mundobancario.InterfazMundoBancarioApplication;
import es.eoi.mundobancario.dto.CreatePrestamoDto;
import es.eoi.mundobancario.dto.PrestamoDto;

public class PrestamoViewController {
	
	private static final String URL_CUENTAS = "/cuentas/";
	private static final String URL_PRESTAMOS = "/prestamos";
	private static final String URL_PRESTAMOS_VIVOS = "/prestamosVivos";
	
	public static PrestamoDto[] findPrestamosVivosByNumCuenta(Integer id) {
		
		String url = InterfazMundoBancarioApplication.URL_GATEWAY.concat(URL_CUENTAS).
				concat(id.toString()).concat(URL_PRESTAMOS_VIVOS);
		
		ResponseEntity<PrestamoDto[]> response = InterfazMundoBancarioApplication.restTemplate.
				getForEntity(url, PrestamoDto[].class);
		
		return response.getBody();
		
	}
	
	public static void createPrestamo(CreatePrestamoDto dto, Integer id) {
		
		String url = InterfazMundoBancarioApplication.URL_GATEWAY.concat(URL_CUENTAS).
				concat(id.toString()).concat(URL_PRESTAMOS);
		
		InterfazMundoBancarioApplication.restTemplate.postForEntity(url, dto, String.class);
	}

}
