package es.eoi.mundobancario.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private JavaMailSender sender;

	@Override
	public void sendEmailReportCliente(String nombreCliente, String email, Integer idCliente) {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String textMessage = "Estimado/a ".concat(nombreCliente).concat(", \n").
				concat("\nSe adjunta en este email el pdf con el reporte solicitado el día ").
				concat(formatter.format(Calendar.getInstance().getTime())).
				concat(" sobre las cuentas que dispone con nosotros, EOI BANK.\n").
				concat("\nEste email se ha autogenerado, no responda.");
		String file = "src/main/resources/EOI_BANK_CLIENTE_".concat(idCliente.toString()).
				concat(".pdf");
		String filename = "EOI_BANK_CLIENTE_".concat(idCliente.toString()).
				concat(".pdf");
		
		BodyPart textBodyPart = new MimeBodyPart();
		BodyPart fileBodyPart = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		DataSource source = new FileDataSource(file);
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			textBodyPart.setText(textMessage);
			fileBodyPart.setDataHandler(new DataHandler(source));
	        fileBodyPart.setFileName(filename);
	        multipart.addBodyPart(textBodyPart);
	        multipart.addBodyPart(fileBodyPart);
	        message.setContent(multipart);
			helper.setTo(email);
			helper.setSubject("Reporte solicitado de EOI BANK");
			
			sender.send(message);
			LOGGER.info("Mail enviado!");
		} catch (MessagingException e) {
			LOGGER.error("Hubo un error al enviar el mail", e);
		}
		
	}

	@Override
	public void sendEmailReportPrestamo(String nombreCliente, String email, Integer idPrestamo) {
	
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String textMessage = "Estimado/a ".concat(nombreCliente).concat(", \n").
				concat("\nSe adjunta en este email el pdf con el reporte solicitado el día ").
				concat(formatter.format(Calendar.getInstance().getTime())).
				concat(" sobre el préstamo contratado con nosotros, EOI BANK.\n").
				concat("\nEste email se ha autogenerado, no responda.");
		String file = "src/main/resources/EOI_BANK_PRESTAMO_".concat(idPrestamo.toString()).
				concat(".pdf");
		String filename = "EOI_BANK_PRESTAMO_".concat(idPrestamo.toString()).
				concat(".pdf");
		
		BodyPart textBodyPart = new MimeBodyPart();
		BodyPart fileBodyPart = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		DataSource source = new FileDataSource(file);
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);		
		try {
			textBodyPart.setText(textMessage);
			fileBodyPart.setDataHandler(new DataHandler(source));
	        fileBodyPart.setFileName(filename);
	        multipart.addBodyPart(textBodyPart);
	        multipart.addBodyPart(fileBodyPart);
	        message.setContent(multipart);
			helper.setTo(email);
			helper.setSubject("Reporte solicitado de EOI BANK");
			
			sender.send(message);
			LOGGER.info("Mail enviado!");
		} catch (MessagingException e) {
			LOGGER.error("Hubo un error al enviar el mail", e);
		}
		
	}


}
