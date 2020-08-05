package es.eoi.mundobancario;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MundoBancarioApplication.class)
class MundoBancarioApplicationTests {
	
	@Autowired
	ClienteRepository clienteRepository;

	@Test
	void createCliente() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Yo");
		cliente.setUsuario("usuario");
		cliente.setPass("pass");
		clienteRepository.save(cliente);
		System.out.println();
	}
	
	@Test
	void testFindUserById() {		
		assertEquals("Yo",clienteRepository.findById(1).get().getNombre());		
	}
	
	@Test
	void testFindUser() {		
		Cliente cliente = clienteRepository.findByUsuarioAndPass("usuario", "pass");
		System.out.println(cliente.getNombre());
		System.out.println(cliente.getUsuario());
	}
	

}
