package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CreateClienteDto;
import es.eoi.mundobancario.dto.CuentaClienteDto;
import es.eoi.mundobancario.dto.UsuarioDto;
import es.eoi.mundobancario.service.ClienteService;

@RestController
public class ClientesController {

	@Autowired
	ClienteService service;

	@GetMapping("/clientes")
	public ResponseEntity<List<ClienteDto>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<ClienteDto> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findClienteById(id));
	}

	@GetMapping("/clientes/{id}/cuentas")
	public ResponseEntity<List<CuentaClienteDto>> findCuentasClienteById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findCuentasClienteById(id));
	}

	@PostMapping("/clientes")
	public ResponseEntity<ClienteDto> create(@RequestBody CreateClienteDto dto) {
		service.create(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/clientes/login")
	public ResponseEntity<ClienteDto> login(@RequestBody UsuarioDto dto) {
		String usuario = dto.getUsuario();
		String pass = dto.getPass();
		return ResponseEntity.ok(service.login(usuario, pass));
	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody String email) {
		service.updateEmail(email, id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}

}
