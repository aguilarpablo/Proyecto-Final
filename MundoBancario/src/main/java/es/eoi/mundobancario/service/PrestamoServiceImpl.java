package es.eoi.mundobancario.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.AmortizacionDto;
import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CreatePrestamoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.dto.ReportPrestamoDto;
import es.eoi.mundobancario.dto.ReportPrestamoVivoAmortizadoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.repository.PrestamoRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService {

	@Autowired
	PrestamoRepository repository;
	
	@Autowired
	ModelMapper mapper;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Prestamo create(CreatePrestamoDto dto) {
		return repository.save(mapper.map(dto, Prestamo.class));
	}

	@Override
	public ReportPrestamoDto findReportPrestamoById(Integer id) {
		Prestamo prestamo = repository.findById(id).get();
		ReportPrestamoDto dto = new ReportPrestamoDto();
		dto.setAmortizaciones(prestamo.getAmortizaciones().stream().
				map(e -> mapper.map(e, AmortizacionDto.class)).
				collect(Collectors.toList()));
		dto.setCliente(mapper.map(prestamo.getCuenta().getCliente(), ClienteDto.class));
		dto.setDescripcion(prestamo.getDescripcion());
		dto.setId(prestamo.getId());
		dto.setFecha(prestamo.getFecha());
		dto.setPlazos(prestamo.getPlazos());
		dto.setImporte(prestamo.getImporte());
		dto.setPagado(prestamo.getPagado());
		
		return dto;
	}

	@Override
	public List<ReportPrestamoVivoAmortizadoDto> findPrestamosVivos() {

		return repository.findByPagadoFalse().stream().
				map(e -> mapper.map(e, ReportPrestamoVivoAmortizadoDto.class)).
				collect(Collectors.toList());	
		
	}

	@Override
	public List<ReportPrestamoVivoAmortizadoDto> findPrestamosAmortizados() {
		
		return repository.findByPagadoTrue().stream().
				map(e -> mapper.map(e, ReportPrestamoVivoAmortizadoDto.class)).
				collect(Collectors.toList());	
	}

	@Override
	public List<PrestamoDto> findPrestamosVivosByNumCuenta(Integer id) {
		return repository.findByPagadoFalseAndCuentaNumCuenta(id).stream().
				map(e -> mapper.map(e, PrestamoDto.class)).
				collect(Collectors.toList());
	}

	@Override
	public List<PrestamoDto> findPrestamosAmortizadosByNumCuenta(Integer id) {
		return repository.findByPagadoTrueAndCuentaNumCuenta(id).stream().
				map(e -> mapper.map(e, PrestamoDto.class)).
				collect(Collectors.toList());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void pagado(Prestamo entity) {
		
		List<Amortizacion> amortizaciones = entity.getAmortizaciones();
		Integer amortizacionesPagadas = 0;
		for (Amortizacion amortizacion : amortizaciones) {
			if (Boolean.TRUE.equals(amortizacion.getPagado())) {
				amortizacionesPagadas++;
			}
		}
		if (amortizacionesPagadas.equals(entity.getPlazos())) {
			entity.setPagado(true);
			repository.save(entity);
		}
	}

	@Override
	public List<PrestamoDto> findPrestamosCuenta(Integer numCuenta) {
		return repository.findByCuentaNumCuenta(numCuenta).stream().
				map(e -> mapper.map(e, PrestamoDto.class)).
				collect(Collectors.toList());
	}

}
