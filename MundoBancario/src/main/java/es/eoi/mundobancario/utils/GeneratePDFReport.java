package es.eoi.mundobancario.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import es.eoi.mundobancario.dto.AmortizacionDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.ReportClienteDto;
import es.eoi.mundobancario.dto.ReportCuentaDto;
import es.eoi.mundobancario.dto.ReportPrestamoDto;
import es.eoi.mundobancario.enums.EnumTipoMovimiento;

public class GeneratePDFReport {

	private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
	private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

	private static final Font categoryFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static final Font subcategoryFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	private static final Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
	private static final Font greenFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.GREEN);

	private static final String pathCliente = "src/main/resources/EOI_BANK_CLIENTE_";
	private static final String pathPrestamo = "src/main/resources/EOI_BANK_PRESTAMO_";
	private static final String pathImage = "src/main/resources/eoi.png";

	public void createPDFReportCliente(ReportClienteDto report) throws IOException {
		try {
			Document document = new Document();
			try {
				String pathFile = pathCliente.concat(report.getId().toString()).concat(".pdf");
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(pathFile)));
				writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				HeaderFooter event = new HeaderFooter();
				writer.setPageEvent(event);
			} catch (FileNotFoundException fileNotFoundException) {
				System.out.println("No such file was found to generate the PDF " + fileNotFoundException);
			}
			document.open();

			// Añadimos los metadatos del PDF
			document.addTitle("EOI BANK CLIENTE");
			document.addSubject("Información de cuentas y movimientos");
			document.addAuthor("Pablo Aguilar Martínez");
			// Primera página
			Chunk chunk = new Chunk("Información de cuentas y movimientos", chapterFont);
			chunk.setBackground(BaseColor.GRAY);
			// Creemos el primer capítulo
			Chapter chapter = new Chapter(new Paragraph(chunk), 1);
			chapter.setNumberDepth(0);
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Paragraph fecha = new Paragraph(
					"\n Fecha de emisión: ".concat(formatter.format(Calendar.getInstance().getTime())), paragraphFont);
			fecha.setAlignment(Element.ALIGN_RIGHT);
			chapter.add(fecha);
			chapter.add(new Paragraph("Cliente con id: ".concat(report.getId().toString()), paragraphFont));
			chapter.add(new Paragraph(report.getNombre(), paragraphFont));
			chapter.add(new Paragraph(report.getEmail(), paragraphFont));
			chapter.add(new Paragraph("Entidad Bancaria: EOI BANK", paragraphFont));
			chapter.add(new Paragraph(
					"\n En este documento se puede ver información sobre las cuentas de las que dispone el cliente y sus movimientos. Es de uso personal.",
					paragraphFont));
			chapter.add(new Paragraph("\n El cliente ".concat(report.getNombre()).concat(" dispone de ")
					.concat(String.valueOf(report.getCuentas().size()))
					.concat(" cuentas que se visualizarán en la página siguiente."), paragraphFont));
			// Añadimos Imagen
			Image image  = Image.getInstance(pathImage);
			addImage(image);
			chapter.add(image);
			document.add(chapter);
			// Segunda página
			Chapter chapSecond = new Chapter(new Paragraph(new Anchor("Cuentas y sus movimientos")), 1);
			for (ReportCuentaDto cuenta : report.getCuentas()) {
				Paragraph paragraph = new Paragraph("Número cuenta: ".concat(cuenta.getNumCuenta().toString())
						.concat(" y saldo: ").concat(cuenta.getSaldo().toString().concat(" €.")), categoryFont);
				chapSecond.add(paragraph);
				Paragraph paragraph1 = new Paragraph("Movimientos de la cuenta", subcategoryFont);
				chapSecond.add(paragraph1);
				for (MovimientoDto movimiento : cuenta.getMovimientos()) {
					Paragraph paragraph2 = new Paragraph(
							"Descripción del movimiento: ".concat(movimiento.getDescripcion()), paragraphFont);
					Chunk tipoMovimiento = new Chunk("Tipo de movimiento: "
							.concat(movimiento.getTipoMovimiento().getTipo()).concat(". Importe: "), paragraphFont);
					if (movimiento.getTipoMovimiento().getTipo().equals(EnumTipoMovimiento.INGRESO.getValor())
							|| movimiento.getTipoMovimiento().getTipo()
									.equals(EnumTipoMovimiento.PRESTAMO.getValor())) {

						Chunk importe = new Chunk(movimiento.getImporte().toString().concat(" € \n"), greenFont);

						Paragraph paragraph3 = new Paragraph();
						paragraph3.add(tipoMovimiento);
						paragraph3.add(importe);
						paragraph3.add("\n");
						chapSecond.add(paragraph2);
						chapSecond.add(paragraph3);
					} else {

						Chunk importe = new Chunk("-".concat(movimiento.getImporte().toString().concat(" € \n")),
								redFont);
						Paragraph paragraph3 = new Paragraph();
						paragraph3.add(tipoMovimiento);
						paragraph3.add(importe);
						paragraph3.add("\n");
						chapSecond.add(paragraph2);
						chapSecond.add(paragraph3);
					}

				}

			}
			document.add(chapSecond);
			document.close();
			System.out.println("Your PDF file has been generated!");
		} catch (DocumentException documentException) {
			System.out.println("The file not exists: " + documentException);
		}
	}

	public void createPDFReportPrestamo(ReportPrestamoDto report) throws IOException {
		try {
			Document document = new Document();
			try {
				String pathFile = pathPrestamo.concat(report.getId().toString()).concat(".pdf");
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(pathFile)));
				writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				writer.setBoxSize("art2", new Rectangle(30, 30, 550, 800));
				HeaderFooter event = new HeaderFooter();
				FooterInteresPrestamo event2 = new FooterInteresPrestamo();
				writer.setPageEvent(event);
				writer.setPageEvent(event2);
			} catch (FileNotFoundException fileNotFoundException) {
				System.out.println("No such file was found to generate the PDF " + fileNotFoundException);
			}

			document.open();

			// Añadimos los metadatos del PDF
			document.addTitle("EOI BANK PRESTAMO");
			document.addSubject("Información de préstamo");
			document.addAuthor("Pablo Aguilar Martínez");
			// Primera página
			Chunk chunk = new Chunk("Información de préstamo", chapterFont);
			chunk.setBackground(BaseColor.GRAY);
			// Creemos el primer capítulo
			Chapter chapter = new Chapter(new Paragraph(chunk), 1);
			chapter.setNumberDepth(0);
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Paragraph fecha = new Paragraph(
					"\n Fecha de emisión: ".concat(formatter.format(Calendar.getInstance().getTime())), paragraphFont);
			fecha.setAlignment(Element.ALIGN_RIGHT);
			chapter.add(fecha);
			chapter.add(
					new Paragraph("Cliente con id: ".concat(report.getCliente().getId().toString()), paragraphFont));
			chapter.add(new Paragraph(report.getCliente().getNombre(), paragraphFont));
			chapter.add(new Paragraph(report.getCliente().getEmail(), paragraphFont));
			chapter.add(new Paragraph("Entidad Bancaria: EOI BANK", paragraphFont));
			chapter.add(new Paragraph(
					"\n En este documento se puede ver información sobre el préstamo del cliente y las amortizaciones planificadas. Es de uso personal.",
					paragraphFont));
			chapter.add(new Paragraph("\nId del préstamo: ".concat(report.getId().toString()), paragraphFont));
			chapter.add(
					new Paragraph("Fecha de solicitud: ".concat(formatter.format(report.getFecha())), paragraphFont));
			chapter.add(new Paragraph("Descripción: ".concat(report.getDescripcion()), paragraphFont));
			chapter.add(new Paragraph("Importe solicitado: ".concat(report.getImporte().toString()).concat(" €"),
					paragraphFont));
			chapter.add(new Paragraph("Plazos: ".concat(report.getPlazos().toString()), paragraphFont));
			chapter.add(new Paragraph("Amortizaciones planificadas: ", paragraphFont));
			for (AmortizacionDto amortizacion : report.getAmortizaciones()) {
				chapter.add(new Paragraph("  Amortización con id: ".concat(amortizacion.getId().toString()),
						paragraphFont));
				chapter.add(
						new Paragraph("  Fecha de amortización: ".concat(formatter.format(amortizacion.getFecha())),
								paragraphFont));
				chapter.add(new Paragraph(
						"  Importe a pagar: ".concat(amortizacion.getImporte().toString().concat(" €*")),
						paragraphFont));
				chapter.add(new Paragraph("\n", paragraphFont));

			}
			// Añadimos Imagen
			Image image = Image.getInstance(pathImage);
			addImage(image);
			chapter.add(image);
			document.add(chapter);
			document.close();
			System.out.println("Your PDF file has been generated!");
		} catch (DocumentException documentException) {
			System.out.println("The file not exists: " + documentException);
		}
	}

	public void addImage(Image image) {
		image.setAbsolutePosition(35, 760);
		image.scaleAbsolute(190, 100);
	}

}
