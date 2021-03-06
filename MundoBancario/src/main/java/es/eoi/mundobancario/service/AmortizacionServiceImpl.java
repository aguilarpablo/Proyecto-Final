package es.eoi.mundobancario.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.CreateAmortizacionDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.repository.AmortizacionRepository;

@Service
public class AmortizacionServiceImpl implements AmortizacionService {
	
	@Autowired
	AmortizacionRepository repository;
	
	@Autowired
	ModelMapper mapper;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(Prestamo e) {
		for (int i = 1; i <= e.getPlazos(); i++) {
			CreateAmortizacionDto amortizacionDto = new CreateAmortizacionDto();
			amortizacionDto.setIdPrestamo(e.getId());
			amortizacionDto.setImporte(e.getImporte()/e.getPlazos());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(e.getFecha());
			calendar.add(Calendar.MONTH, i);
			amortizacionDto.setFecha(calendar.getTime());
			amortizacionDto.setPagado(false);
			
			repository.save(mapper.map(amortizacionDto, Amortizacion.class));
		}	
		
	}

	@Override
	public List<Amortizacion> findByFechaAndPagadoFalse(Date fecha) {
		return repository.findByFecha(fecha);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(Amortizacion entity) {
		repository.save(entity);
		
	}

}
