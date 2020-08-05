package es.eoi.mundobancario.controller;

import org.springframework.http.ResponseEntity;

import es.eoi.mundobancario.InterfazMundoBancarioApplication;
import es.eoi.mundobancario.dto.ClienteDto;

public class ClienteViewController {
	
	private static final String URL_CLIENTES = "/clientes/";
	
	
	public static ClienteDto buscarCliente(Integer id) {
		String urlBuscarCliente = InterfazMundoBancarioApplication.URL_GATEWAY.
				concat(URL_CLIENTES).concat(id.toString());
		ResponseEntity<ClienteDto> response = InterfazMundoBancarioApplication.restTemplate.
				getForEntity(urlBuscarCliente, ClienteDto.class);
		return response.getBody();
	}
	
	public static void updateEmail(Integer id, String email) {
		String urlUpdateEmail = InterfazMundoBancarioApplication.URL_GATEWAY.
				concat(URL_CLIENTES).concat(id.toString());
		InterfazMundoBancarioApplication.restTemplate.put(urlUpdateEmail, email);
		
	}
	


}
