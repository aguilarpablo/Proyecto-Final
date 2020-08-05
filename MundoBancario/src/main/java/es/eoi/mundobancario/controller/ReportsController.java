package es.eoi.mundobancario.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.ReportClienteDto;
import es.eoi.mundobancario.dto.ReportPrestamoDto;
import es.eoi.mundobancario.dto.ReportPrestamoVivoAmortizadoDto;
import es.eoi.mundobancario.service.ClienteService;
import es.eoi.mundobancario.service.PrestamoService;
import es.eoi.mundobancario.utils.GeneratePDFReport;

@RestController
public class ReportsController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	PrestamoService prestamoService;
	
	@Autowired
	GeneratePDFReport generatePDFReport;
	
	@GetMapping("/reports/clientes/{id}")
	public ResponseEntity<ReportClienteDto> reportClienteById(@PathVariable Integer id) {
		return ResponseEntity.ok(clienteService.findById(id));
	}
	
	@GetMapping("/reports/prestamos/{id}")
	public ResponseEntity<ReportPrestamoDto> reportPrestamoById(@PathVariable Integer id) {
		return ResponseEntity.ok(prestamoService.findReportPrestamoById(id));
	}
	
	@GetMapping("/reports/prestamosVivos")
	public ResponseEntity<List<ReportPrestamoVivoAmortizadoDto>> reportPrestamosVivos() {
		return ResponseEntity.ok(prestamoService.findPrestamosVivos());
	}
	
	@GetMapping("/reports/prestamosAmortizados")
	public ResponseEntity<List<ReportPrestamoVivoAmortizadoDto>> reportPrestamosAmortizados() {
		return ResponseEntity.ok(prestamoService.findPrestamosAmortizados());
	}
	
	@PostMapping("/reports/clientes/{id}")
	public ResponseEntity<GeneratePDFReport> reportClienteByIdPDF(@PathVariable Integer id) throws IOException {
		 generatePDFReport.createPDFReportCliente(clienteService.findById(id));
		 return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/reports/prestamos/{id}")
	public ResponseEntity<GeneratePDFReport> reportPrestamoByIdPDF(@PathVariable Integer id) throws IOException {
		 generatePDFReport.createPDFReportPrestamo(prestamoService.findReportPrestamoById(id));
		 return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
