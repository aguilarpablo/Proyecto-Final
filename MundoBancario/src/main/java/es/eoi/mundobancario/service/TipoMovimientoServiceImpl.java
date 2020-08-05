package es.eoi.mundobancario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.repository.TipoMovimientoRepository;

@Service
public class TipoMovimientoServiceImpl implements TipoMovimientoService {
	
	@Autowired
	TipoMovimientoRepository repository;

	@Override
	public Integer findIdTipoMovimiento(String tipo) {
		return repository.findByTipo(tipo).getId();
	}

}
