package es.eoi.mundobancario.service;

public interface EmailService {
	
	public void sendEmailReportCliente(String nombreCliente, String email, Integer idCliente);
	
	public void sendEmailReportPrestamo(String nombreCliente, String email, Integer idPrestamo);

}
