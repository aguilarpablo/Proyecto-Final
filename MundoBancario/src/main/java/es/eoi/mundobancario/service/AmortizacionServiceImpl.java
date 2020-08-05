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
			System.out.println();
			CreateAmortizacionDto amortizacionDto = new CreateAmortizacionDto();
			amortizacionDto.setIdPrestamo(e.getId());
			amortizacionDto.setImporte(e.getImporte()/e.getPlazos());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(e.getFecha());
			calendar.add(Calendar.MONTH, i);
			amortizacionDto.setFecha(calendar.getTime());
			
			repository.save(mapper.map(amortizacionDto, Amortizacion.class));
			System.out.println();
		}	
		
	}

	@Override
	public List<Amortizacion> findByFecha(Date fecha) {
		return repository.findByFecha(fecha);
	}

}
