package es.eoi.mundobancario.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.CreateCuentaDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.repository.CuentaRepository;

@Service
public class CuentaServiceImpl implements CuentaService {
	
	@Autowired
	CuentaRepository repository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<CuentaDto> findAll() {
		return repository.findAll().stream().
				map(e -> mapper.map(e, CuentaDto.class)).
				collect(Collectors.toList());	
	}

	@Override
	public CuentaDto findCuentaByNumCuenta(Integer id) {
		return mapper.map(repository.findById(id).get(), CuentaDto.class);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(CreateCuentaDto dto) {
		repository.save(mapper.map(dto, Cuenta.class));
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void updateAlias(Integer id, String alias) {
		
		Cuenta cuenta = repository.findById(id).get();
		cuenta.setAlias(alias);
		
		repository.save(cuenta);
			
	}

	@Override
	public List<CuentaDto> findCuentaWithNegativeSaldo() {
		return repository.findBySaldoLessThan(0.0).stream().
				map(e -> mapper.map(e, CuentaDto.class)).
				collect(Collectors.toList());
	}

	@Override
	public List<MovimientoDto> findMovimientosCuenta(Integer id) {

		return repository.findById(id).get().getMovimientos().stream().
				map(e -> mapper.map(e, MovimientoDto.class)).
				collect(Collectors.toList());
	}

	@Override
	public List<PrestamoDto> findPrestamosCuenta(Integer id) {
		
		return repository.findById(id).get().getPrestamos().stream().
				map(e -> mapper.map(e, PrestamoDto.class)).
				collect(Collectors.toList());
	}

	@Override
	public List<PrestamoDto> findPrestamosVivosCuenta(Integer id) {
		return repository.findById(id).get().getPrestamos().
				stream().
				filter(p -> fechaMasPlazos(p.getFecha(), p.getPlazos()).after(Calendar.getInstance().getTime())).
				map(e -> mapper.map(e, PrestamoDto.class)).
				collect(Collectors.toList());
	}

	@Override
	public List<PrestamoDto> findPrestamosAmortizadosCuenta(Integer id) {
		return repository.findById(id).get().getPrestamos().
				stream().
				filter(p -> fechaMasPlazos(p.getFecha(), p.getPlazos()).before(Calendar.getInstance().getTime())).
				map(e -> mapper.map(e, PrestamoDto.class)).
				collect(Collectors.toList());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void updateSaldo(Integer id, Double importe, String tipoMovimiento) {
		Cuenta cuenta = repository.findById(id).get();
		if (tipoMovimiento.equals("Ingreso") || tipoMovimiento.equals("Préstamo")) {
			cuenta.setSaldo(cuenta.getSaldo() + importe);
		} else if (tipoMovimiento.equals("Interés")) {
			cuenta.setSaldo(cuenta.getSaldo() - importe*0.02);
		} else {
			cuenta.setSaldo(cuenta.getSaldo() - importe);
		}
		
		repository.save(cuenta);
	}
	
	private Date fechaMasPlazos(Date date, Integer plazos) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, plazos);
		return calendar.getTime();

	}

}
