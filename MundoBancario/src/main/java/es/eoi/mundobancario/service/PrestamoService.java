package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.dto.CreatePrestamoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.dto.ReportPrestamoDto;
import es.eoi.mundobancario.dto.ReportPrestamoVivoAmortizadoDto;
import es.eoi.mundobancario.entity.Prestamo;

public interface PrestamoService {

	public Prestamo create(CreatePrestamoDto dto);

	public ReportPrestamoDto findReportPrestamoById(Integer id);

	public List<ReportPrestamoVivoAmortizadoDto> findPrestamosVivos();

	public List<ReportPrestamoVivoAmortizadoDto> findPrestamosAmortizados();

	public List<PrestamoDto> findPrestamosVivosByNumCuenta(Integer id);

	public List<PrestamoDto> findPrestamosAmortizadosByNumCuenta(Integer id);

}
