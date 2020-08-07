package es.eoi.mundobancario.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
	private static final Font paragraphFontBold = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
	private static final Font paragraphFontUnderline = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE);

	private static final Font categoryFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static final Font subcategoryFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	private static final Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
	private static final Font greenFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.GREEN);

	private static final String pathCliente = "src/main/resources/EOI_BANK_CLIENTE_";
	private static final String pathPrestamo = "src/main/resources/EOI_BANK_PRESTAMO_";
	private static final String pathImage = "src/main/resources/eoi.png";

	public void createPDFReportCliente(ReportClienteDto report) throws IOException {
		try {
			Document document = new Document(PageSize.A4,55,55,55,55);
			try {
				String pathFile = pathCliente.concat(report.getId().toString()).concat(".pdf");
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(pathFile)));
				writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				FooterPage event = new FooterPage();
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
			// Primer capítulo
			Chapter chapter = new Chapter(new Paragraph(chunk), 1);
			chapter.setNumberDepth(0);
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Paragraph fechaEmision = new Paragraph(
					"\n Fecha de emisión: ".concat(formatter.format(Calendar.getInstance().getTime())), paragraphFont);
			fechaEmision.setAlignment(Element.ALIGN_RIGHT);
			chapter.add(fechaEmision);
			//Información cliente
			chapter.add(new Paragraph("DATOS DEL CLIENTE", paragraphFontBold));
			chapter.add(new Paragraph("\nCliente con id: ".concat(report.getId().toString()), paragraphFont));
			chapter.add(new Paragraph(report.getNombre(), paragraphFont));
			chapter.add(new Paragraph(report.getEmail(), paragraphFont));
			chapter.add(new Paragraph("Entidad Bancaria: EOI BANK", paragraphFont));
			chapter.add(new Paragraph(
					"\nEn este documento se puede ver información sobre las cuentas de las que dispone el cliente y sus movimientos. Es de uso personal.",
					paragraphFont));
			chapter.add(new Paragraph("\nEl cliente ".concat(report.getNombre()).concat(" dispone de ")
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
				//Información básica de la cuenta
				Paragraph paragraph = new Paragraph("Número cuenta: ".concat(cuenta.getNumCuenta().toString())
						.concat("\nAlias: ").concat(cuenta.getAlias())
						.concat("\nSaldo: ").concat(cuenta.getSaldo().toString().concat(" €")), categoryFont);
				chapSecond.add(paragraph);
				Paragraph paragraph1 = new Paragraph("Movimientos de la cuenta: \n", subcategoryFont);
				paragraph1.add("\n");
				chapSecond.add(paragraph1);
				List<MovimientoDto> movimientos = cuenta.getMovimientos();
				Collections.sort(movimientos);
				//Tabla donde tenemos los movimientos de la cuenta
				PdfPTable table = new PdfPTable(4);
				PdfPCell fecha = new PdfPCell(new Phrase("Fecha", paragraphFontBold));
				PdfPCell descripcionMovimiento = new PdfPCell(new Phrase("Descripción del movimiento", paragraphFontBold));
				PdfPCell tipoMovimiento = new PdfPCell(new Phrase("Tipo de movimiento", paragraphFontBold));
				PdfPCell importe = new PdfPCell(new Phrase("Importe", paragraphFontBold));
				fecha.setVerticalAlignment(Element.ALIGN_MIDDLE);
				fecha.setHorizontalAlignment(Element.ALIGN_CENTER);
				descripcionMovimiento.setVerticalAlignment(Element.ALIGN_MIDDLE);
				descripcionMovimiento.setHorizontalAlignment(Element.ALIGN_CENTER);
				tipoMovimiento.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tipoMovimiento.setHorizontalAlignment(Element.ALIGN_CENTER);
				importe.setVerticalAlignment(Element.ALIGN_MIDDLE);
				importe.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(fecha);
				table.addCell(descripcionMovimiento);
				table.addCell(tipoMovimiento);
				table.addCell(importe);
				for (MovimientoDto movimiento : movimientos) {
					PdfPCell date = new PdfPCell(new Phrase(formatter.format(movimiento.getFecha()), paragraphFont));
					PdfPCell descripcionMov = new PdfPCell(new Phrase(movimiento.getDescripcion(), paragraphFont));
					PdfPCell tipoMov = new PdfPCell(new Phrase(movimiento.getTipoMovimiento().getTipo(), paragraphFont));
					date.setVerticalAlignment(Element.ALIGN_MIDDLE);
					date.setHorizontalAlignment(Element.ALIGN_CENTER);
					descripcionMov.setVerticalAlignment(Element.ALIGN_MIDDLE);
					descripcionMov.setHorizontalAlignment(Element.ALIGN_CENTER);
					tipoMov.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tipoMov.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(date);
					table.addCell(descripcionMov);
					table.addCell(tipoMov);
					if (movimiento.getTipoMovimiento().getTipo().equals(EnumTipoMovimiento.INGRESO.getValor())
							|| movimiento.getTipoMovimiento().getTipo()
									.equals(EnumTipoMovimiento.PRESTAMO.getValor())) {
						
						PdfPCell dinero = new PdfPCell(new Phrase(movimiento.getImporte().toString().concat(" € \n"), 
								greenFont));
						dinero.setVerticalAlignment(Element.ALIGN_MIDDLE);
						dinero.setHorizontalAlignment(Element.ALIGN_CENTER);	
						table.addCell(dinero);

					} else {
						PdfPCell dinero = new PdfPCell(new Phrase(movimiento.getImporte().toString().concat(" € \n"), 
								redFont));
						dinero.setVerticalAlignment(Element.ALIGN_MIDDLE);	
						dinero.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(dinero);	
					}
					
				}
				chapSecond.add(table);
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
			Document document = new Document(PageSize.A4,60,60,60,60);
			try {
				String pathFile = pathPrestamo.concat(report.getId().toString()).concat(".pdf");
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(pathFile)));
				writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				writer.setBoxSize("art2", new Rectangle(30, 30, 550, 800));
				FooterPage event = new FooterPage();
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
			// Añadimos Imagen
			Image image = Image.getInstance(pathImage);
			addImage(image);
			chapter.add(image);
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Paragraph fecha = new Paragraph(
					"\n Fecha de emisión: ".concat(formatter.format(Calendar.getInstance().getTime())), paragraphFont);
			fecha.setAlignment(Element.ALIGN_RIGHT);
			chapter.add(fecha);
			// Información cliente
			chapter.add(new Paragraph("DATOS DEL CLIENTE", paragraphFontBold));
			chapter.add(
					new Paragraph("\nCliente con id: ".concat(report.getCliente().getId().toString()), paragraphFont));
			chapter.add(new Paragraph(report.getCliente().getNombre(), paragraphFont));
			chapter.add(new Paragraph(report.getCliente().getEmail(), paragraphFont));
			chapter.add(new Paragraph("Entidad Bancaria: EOI BANK", paragraphFont));
			chapter.add(new Paragraph(
					"\n En este documento se puede ver información sobre el préstamo del cliente y las amortizaciones planificadas. Es de uso personal.",
					paragraphFont));
			
			chapter.add(new Paragraph("\nDATOS DEL PRÉSTAMO", paragraphFontBold));
			chapter.add(new Paragraph("\nId del préstamo: ".concat(report.getId().toString()), paragraphFont));
			chapter.add(
					new Paragraph("Fecha de solicitud: ".concat(formatter.format(report.getFecha())), paragraphFont));
			chapter.add(new Paragraph("Descripción: ".concat(report.getDescripcion()), paragraphFont));
			chapter.add(new Paragraph("Importe solicitado: ".concat(report.getImporte().toString()).concat(" €"),
					paragraphFont));
			chapter.add(new Paragraph("Plazos: ".concat(report.getPlazos().toString()), paragraphFont));
			if (Boolean.TRUE.equals(report.getPagado())) {
				chapter.add(new Paragraph("PAGADO", paragraphFontBold));
			} else {
				chapter.add(new Paragraph("PENDIENTE DE PAGO", paragraphFontBold));
			}
			chapter.add(new Paragraph("\n", paragraphFont));
			chapter.add(new Paragraph("Amortizaciones planificadas: ", paragraphFontUnderline));
			chapter.add(new Paragraph("\n", paragraphFont));
			for (AmortizacionDto amortizacion : report.getAmortizaciones()) {
				chapter.add(new Paragraph("  Amortización con id: ".concat(amortizacion.getId().toString()),
						paragraphFont));
				chapter.add(
						new Paragraph("  Fecha de amortización: ".concat(formatter.format(amortizacion.getFecha())),
								paragraphFont));
				chapter.add(new Paragraph(
						"  Importe a pagar: ".concat(amortizacion.getImporte().toString().concat(" €*")),
						paragraphFont));
				if (Boolean.TRUE.equals(amortizacion.getPagado())) {
					chapter.add(new Paragraph("  PAGADA", paragraphFontBold));
				} else {
					chapter.add(new Paragraph("  PENDIENTE", paragraphFontBold));
				}
				chapter.add(new Paragraph("\n", paragraphFont));

			}
			
			document.add(chapter);
			document.close();
			System.out.println("Your PDF file has been generated!");
		} catch (DocumentException documentException) {
			System.out.println("The file not exists: " + documentException);
		}
	}

	public void addImage(Image image) {
		image.setAbsolutePosition(35, 740);
		image.scaleAbsolute(210, 120);
	}

}
