package es.eoi.mundobancario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
		
	@Query(value = "SELECT new Cliente(c.id,c.usuario,c.nombre,c.email) FROM Cliente c WHERE c.usuario = :usuario AND c.pass = :pass")
	public Cliente findByUsuarioAndPass(@Param("usuario") String usuario, @Param("pass") String pass);
	
	@Query(value = "SELECT new Cliente(c.id,c.usuario,c.nombre,c.email) FROM Cliente c")
	public List<Cliente> findAllWithoutPass();
	
	@Query(value = "SELECT new Cliente(c.id,c.usuario,c.nombre,c.email) FROM Cliente c WHERE c.id = :id")
	public Optional<Cliente> findClienteById(@Param("id") Integer id);
	
}
