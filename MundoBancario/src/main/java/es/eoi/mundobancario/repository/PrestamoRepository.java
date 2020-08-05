package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
	
	@Query(value = "SELECT DISTINCT P.ID, P.DESCRIPCION, P.FECHA, P.IMPORTE, P.PLAZOS, P.ID_CUENTA FROM PRESTAMOS P "
			+ "INNER JOIN AMORTIZACIONES A ON P.ID = A.ID_PRESTAMO "
			+ "WHERE A.FECHA > curdate()",
			nativeQuery = true)
	public List<Prestamo> findPrestamosVivos();
	
	@Query(value = "SELECT DISTINCT P.ID, P.DESCRIPCION, P.FECHA, P.IMPORTE, P.PLAZOS, P.ID_CUENTA FROM PRESTAMOS P "
			+ "INNER JOIN AMORTIZACIONES A ON P.ID = A.ID_PRESTAMO "
			+ "WHERE A.FECHA < curdate() AND P.ID NOT IN("
			+ "SELECT DISTINCT P.ID FROM PRESTAMOS P "
			+ "INNER JOIN AMORTIZACIONES A ON P.ID = A.ID_PRESTAMO "
			+ "WHERE A.FECHA > curdate())",
			nativeQuery = true)
	public List<Prestamo> findPrestamosAmortizados();
	
	@Query(value = "SELECT DISTINCT P.ID, P.DESCRIPCION, P.FECHA, P.IMPORTE, P.PLAZOS, P.ID_CUENTA FROM PRESTAMOS P "
			+ "INNER JOIN AMORTIZACIONES A ON P.ID = A.ID_PRESTAMO "
			+ "WHERE A.FECHA > curdate() AND P.ID_CUENTA = :id",
			nativeQuery = true)
	public List<Prestamo> findPrestamosVivosByNumCuenta(@Param("id") Integer id);
	
	@Query(value = "SELECT DISTINCT P.ID, P.DESCRIPCION, P.FECHA, P.IMPORTE, P.PLAZOS, P.ID_CUENTA FROM PRESTAMOS P "
			+ "INNER JOIN AMORTIZACIONES A ON P.ID = A.ID_PRESTAMO "
			+ "WHERE A.FECHA < curdate() AND P.ID NOT IN("
			+ "SELECT DISTINCT P.ID FROM PRESTAMOS P "
			+ "INNER JOIN AMORTIZACIONES A ON P.ID = A.ID_PRESTAMO "
			+ "WHERE A.FECHA > curdate()) AND P.ID_CUENTA = :id",
			nativeQuery = true)
	public List<Prestamo> findPrestamosAmortizadosByNumCuenta(@Param("id") Integer id);
	

}
