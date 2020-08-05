package es.eoi.mundobancario.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.CreateMovimientoDto;
import es.eoi.mundobancario.dto.CreatePrestamoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.repository.MovimientoRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService {
	
	@Autowired
	MovimientoRepository repository;
	
	@Autowired
	ModelMapper mapper;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void createMovimientoPrestamo(CreatePrestamoDto dto, Integer idTipoMovimiento) {
		
		CreateMovimientoDto movimientoDto = new CreateMovimientoDto();
		movimientoDto.setDescripcion(dto.getDescripcion());
		movimientoDto.setFecha(dto.getFecha());
		movimientoDto.setImporte(dto.getImporte());
		movimientoDto.setIdTipo(idTipoMovimiento);
		movimientoDto.setNumCuenta(dto.getNumCuenta());
		
		repository.save(mapper.map(movimientoDto, Movimiento.class));
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(CreateMovimientoDto dto) {
		repository.save(mapper.map(dto, Movimiento.class));
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void amortizacion(Amortizacion amortizacion, Integer tipoMovimiento) {
		CreateMovimientoDto dto = new CreateMovimientoDto();
		dto.setDescripcion("Amortización del préstamo con id:".concat((amortizacion.getPrestamo().getId().toString())));
		dto.setFecha(amortizacion.getFecha());
		dto.setImporte(amortizacion.getImporte());
		dto.setNumCuenta(amortizacion.getPrestamo().getCuenta().getNumCuenta());
		dto.setIdTipo(tipoMovimiento);
		
		repository.save(mapper.map(dto, Movimiento.class));
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void intereses(Amortizacion amortizacion, Integer tipoMovimiento) {
		CreateMovimientoDto dto = new CreateMovimientoDto();
		dto.setDescripcion("Intereses de la amortización con id:".concat((amortizacion.getId().toString())));
		dto.setFecha(amortizacion.getFecha());
		dto.setImporte(amortizacion.getImporte()*0.02);
		dto.setNumCuenta(amortizacion.getPrestamo().getCuenta().getNumCuenta());
		dto.setIdTipo(tipoMovimiento);
		
		repository.save(mapper.map(dto, Movimiento.class));
	}

}
