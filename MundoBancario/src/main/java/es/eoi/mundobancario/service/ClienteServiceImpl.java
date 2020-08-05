package es.eoi.mundobancario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CreateClienteDto;
import es.eoi.mundobancario.dto.CuentaClienteDto;
import es.eoi.mundobancario.dto.ReportClienteDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	ClienteRepository repository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<ClienteDto> findAll() {
		return repository.findAllWithoutPass().stream().
				map(e -> mapper.map(e, ClienteDto.class)).
				collect(Collectors.toList());
	}

	@Override
	public ClienteDto findClienteById(Integer id) {
		return mapper.map(repository.findClienteById(id).get(), ClienteDto.class);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(CreateClienteDto dto) {
		repository.save(mapper.map(dto, Cliente.class));
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void updateEmail(String email, Integer id) {
		
		Cliente cliente = repository.findById(id).get();
		cliente.setEmail(email);
		repository.save(cliente);
		
	}

	@Override
	public List<CuentaClienteDto> findCuentasClienteById(Integer id) {
		
		List<Cuenta> cuentas = repository.findById(id).get().getCuentas();
		List<CuentaClienteDto> dtos = new ArrayList<>();
		
		for (Cuenta cuenta : cuentas) {
			CuentaClienteDto dto = new CuentaClienteDto();
			BeanUtils.copyProperties(cuenta, dto);
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public ClienteDto login(String usuario, String pass) {
		return mapper.map(repository.findByUsuarioAndPass(usuario, pass), ClienteDto.class);
	}

	@Override
	public ReportClienteDto findById(Integer id) {
		return mapper.map(repository.findById(id).get(), ReportClienteDto.class);
	}

}
