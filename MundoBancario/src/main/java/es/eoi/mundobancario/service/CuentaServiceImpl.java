package es.eoi.mundobancario.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.CreateCuentaDto;
import es.eoi.mundobancario.dto.CuentaDto;
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

}
