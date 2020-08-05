package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
	
	public List<Prestamo> findByPagadoTrue();
	
	public List<Prestamo> findByPagadoTrueAndCuentaNumCuenta(Integer id);
	
	public List<Prestamo> findByPagadoFalse();
	
	public List<Prestamo> findByPagadoFalseAndCuentaNumCuenta(Integer id);
	
	public List<Prestamo> findByCuentaNumCuenta(Integer id);

}
