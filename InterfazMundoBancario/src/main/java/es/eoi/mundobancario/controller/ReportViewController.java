package es.eoi.mundobancario.controller;

import es.eoi.mundobancario.InterfazMundoBancarioApplication;
import es.eoi.mundobancario.utils.GeneratePDFReport;

public class ReportViewController {
	
	private static final String URL_REPORT_CLIENTES = "/reports/clientes/";
	private static final String URL_REPORT_PRESTAMOS = "/reports/prestamos/";
	
	public static void generarPDFCliente(Integer id) {
		String urlReport = InterfazMundoBancarioApplication.URL_GATEWAY.
				concat(URL_REPORT_CLIENTES).concat(id.toString());
		
		InterfazMundoBancarioApplication.restTemplate.
				postForEntity(urlReport, id, GeneratePDFReport.class);
	}
	
	public static void generarPDFPrestamo(Integer id) {
		String urlReport = InterfazMundoBancarioApplication.URL_GATEWAY.
				concat(URL_REPORT_PRESTAMOS).concat(id.toString());
		
		InterfazMundoBancarioApplication.restTemplate.
		postForEntity(urlReport, id, GeneratePDFReport.class);

		
	}

}
