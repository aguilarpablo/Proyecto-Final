package es.eoi.mundobancario.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.CreateCuentaDto;
import es.eoi.mundobancario.dto.CreateMovimientoDto;
import es.eoi.mundobancario.dto.CreatePrestamoDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.enums.EnumTipoMovimiento;
import es.eoi.mundobancario.service.AmortizacionService;
import es.eoi.mundobancario.service.CuentaService;
import es.eoi.mundobancario.service.MovimientoService;
import es.eoi.mundobancario.service.PrestamoService;
import es.eoi.mundobancario.service.TipoMovimientoService;

@RestController
public class CuentasController {
    
	@Autowired
	CuentaService service;

	@Autowired
	PrestamoService prestamoService;

	@Autowired
	AmortizacionService amortizacionService;

	@Autowired
	MovimientoService movimientoService;

	@Autowired
	TipoMovimientoService tipoMovimientoService;

	@GetMapping("/cuentas")
	public ResponseEntity<List<CuentaDto>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/cuentas/{id}")
	public ResponseEntity<CuentaDto> findByNumCuenta(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findCuentaByNumCuenta(id));
	}

	@GetMapping("/cuentas/deudoras")
	public ResponseEntity<List<CuentaDto>> findCuentasDeudoras() {
		return ResponseEntity.ok(service.findCuentaWithNegativeSaldo());
	}

	@GetMapping("/cuentas/{id}/movimientos")
	public ResponseEntity<List<MovimientoDto>> findMovimientosCuenta(@PathVariable Integer id) {
		return ResponseEntity.ok(movimientoService.findMovimientosCuenta(id));
	}

	@GetMapping("/cuentas/{id}/prestamos")
	public ResponseEntity<List<PrestamoDto>> findPrestamosCuenta(@PathVariable Integer id) {
		return ResponseEntity.ok(prestamoService.findPrestamosCuenta(id));
	}

	@GetMapping("/cuentas/{id}/prestamosVivos")
	public ResponseEntity<List<PrestamoDto>> findPrestamosVivosCuenta(@PathVariable Integer id) {
		return ResponseEntity.ok(prestamoService.findPrestamosVivosByNumCuenta(id));
	}

	@GetMapping("/cuentas/{id}/prestamosAmortizados")
	public ResponseEntity<List<PrestamoDto>> findPrestamosAmortizadosCuenta(@PathVariable Integer id) {
		return ResponseEntity.ok(prestamoService.findPrestamosAmortizadosByNumCuenta(id));
	}

	@PostMapping("/cuentas")
	public ResponseEntity<CuentaDto> createCuenta(@RequestBody CreateCuentaDto dto) {
		service.create(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/cuentas/{id}/prestamos")
	public ResponseEntity<String> createPrestamo(@PathVariable Integer id, @RequestBody CreatePrestamoDto dto) {
		if (prestamoService.findPrestamosVivosByNumCuenta(id).isEmpty()) {

			dto.setFecha(Calendar.getInstance().getTime());
			dto.setNumCuenta(id);
			dto.setPagado(false);
			Prestamo prestamo = prestamoService.create(dto);
			amortizacionService.create(prestamo);
			movimientoService.createMovimientoPrestamo(dto,
					tipoMovimientoService.findIdTipoMovimiento(EnumTipoMovimiento.PRESTAMO.getValor()));
			service.updateSaldo(id, dto.getImporte(), EnumTipoMovimiento.PRESTAMO.getValor());
			return new ResponseEntity<>(HttpStatus.CREATED);

		} else {
			throw new Error(
					"No se puede solicitar un préstamo si ya hay uno en curso hasta que este se haya amortizado totalmente.");
		}

	}

	@PostMapping("/cuentas/{id}/ingresos")
	public ResponseEntity<String> createIngreso(@PathVariable Integer id, @RequestBody CreateMovimientoDto dto) {
		dto.setFecha(Calendar.getInstance().getTime());
		dto.setNumCuenta(id);
		dto.setIdTipo(tipoMovimientoService.findIdTipoMovimiento(EnumTipoMovimiento.INGRESO.getValor()));
		service.updateSaldo(id, dto.getImporte(), EnumTipoMovimiento.INGRESO.getValor());
		movimientoService.create(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PostMapping("/cuentas/{id}/pagos")
	public ResponseEntity<String> createPago(@PathVariable Integer id, @RequestBody CreateMovimientoDto dto) {
		if (service.findCuentaByNumCuenta(id).getSaldo() >= dto.getImporte()) {
			dto.setFecha(Calendar.getInstance().getTime());
			dto.setNumCuenta(id);
			dto.setIdTipo(tipoMovimientoService.findIdTipoMovimiento(EnumTipoMovimiento.PAGO.getValor()));
			service.updateSaldo(id, dto.getImporte(), EnumTipoMovimiento.PAGO.getValor());
			movimientoService.create(dto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			throw new Error("Saldo insuficiente.");
		}

	}

	@Scheduled(cron = "0 0 8 * * *", zone = "Europe/Madrid")
	@PostMapping("/cuentas/ejecutarAmortizacionesDiarias")
	public ResponseEntity<String> runAmortizaciones() {
		Date fecha = Calendar.getInstance().getTime();
		List<Amortizacion> amortizaciones = amortizacionService.findByFechaAndPagadoFalse(fecha);
		for (Amortizacion amortizacion : amortizaciones) {

			Integer numCuenta = amortizacion.getPrestamo().getCuenta().getNumCuenta();
			service.updateSaldo(numCuenta, amortizacion.getImporte(), EnumTipoMovimiento.AMORTIZACION.getValor());
			movimientoService.amortizacion(amortizacion,
					tipoMovimientoService.findIdTipoMovimiento(EnumTipoMovimiento.AMORTIZACION.getValor()));
			service.updateSaldo(numCuenta, amortizacion.getImporte(), EnumTipoMovimiento.INTERES.getValor());
			movimientoService.intereses(amortizacion,
					tipoMovimientoService.findIdTipoMovimiento(EnumTipoMovimiento.INTERES.getValor()));
			amortizacion.setPagado(true);
			amortizacionService.update(amortizacion);
			prestamoService.pagado(amortizacion.getPrestamo());
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/cuentas/{id}")
	public ResponseEntity<CuentaDto> update(@PathVariable Integer id, @RequestBody String alias) {
		service.updateAlias(id, alias);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}
}
