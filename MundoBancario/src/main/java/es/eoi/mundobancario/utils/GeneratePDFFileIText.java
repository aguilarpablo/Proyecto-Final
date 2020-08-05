package es.eoi.mundobancario.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.ReportClienteDto;
import es.eoi.mundobancario.dto.ReportCuentaDto;
import es.eoi.mundobancario.enums.EnumTipoMovimiento;

public class GeneratePDFFileIText {

	private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
	private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

	private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static final Font greenFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.GREEN);
	private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	private static final String path = "src/main/resources/EOI_BANK_CLIENTE_";
	private static final String pathImage = "src/main/resources/eoi.png";

	public void createPDF(ReportClienteDto report) {
		try {
			Document document = new Document();
			try {
				Integer idCliente = report.getId();
				String pathFile = path.concat(idCliente.toString()).concat(".pdf");
				PdfWriter.getInstance(document, new FileOutputStream(new File(pathFile)));
			} catch (FileNotFoundException fileNotFoundException) {
				System.out.println("No such file was found to generate the PDF "
						+ "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
			}
			document.open();

			// Añadimos los metadatos del PDF
			document.addTitle("EOI BANK CLIENTE");
			document.addSubject("Información de cuentas y movimientos");
			document.addAuthor("Cliente: ".concat(report.getNombre()));
			// Primera página
			Chunk chunk = new Chunk("Información de cuentas y movimientos", chapterFont);
			chunk.setBackground(BaseColor.GRAY);
			// Creemos el primer capítulo
			Chapter chapter = new Chapter(new Paragraph(chunk), 1);
			chapter.setNumberDepth(0);
			chapter.add(new Paragraph("Cliente con id: ".concat(report.getId().toString()), paragraphFont));
			chapter.add(new Paragraph("En este documento se puede ver información sobre las cuentas de las que dispone el cliente y sus movimientos. Es de uso personal.", paragraphFont));
			// Añadimos Imagen
			Image image;
			try {
			    image = Image.getInstance(pathImage);  
			    image.setAbsolutePosition(50, 300);
			    image.scaleAbsolute(500, 200);
			    chapter.add(image);
			} catch (BadElementException ex) {
			    System.out.println("Image BadElementException" +  ex);
			} catch (IOException ex) {
			    System.out.println("Image IOException " +  ex);
			}
			document.add(chapter);
			// Segunda página
			Chapter chapSecond = new Chapter(new Paragraph(new Anchor("Cuentas y sus movimientos")), 1);
			for (ReportCuentaDto cuenta : report.getCuentas()) {
				Paragraph paragraph = new Paragraph("Número cuenta: ".concat(cuenta.getNumCuenta().toString()).
						concat(" y saldo: ").concat(cuenta.getSaldo().toString().concat(" €.")), categoryFont);
				chapSecond.add(paragraph);
				Paragraph paragraph1 = new Paragraph("Movimientos de la cuenta", subcategoryFont);
				chapSecond.add(paragraph1);
				for (MovimientoDto movimiento : cuenta.getMovimientos()) {
					Paragraph paragraph2 = new Paragraph("Descripción del movimiento: ".
							concat(movimiento.getDescripcion()), paragraphFont);
					Chunk saltoLinea = new Chunk(" ", redFont);
					Chunk tipoMovimiento = new Chunk("Tipo de movimiento: ".
							concat(movimiento.getTipoMovimiento().getTipo()).concat(". Importe: "), paragraphFont);
					if (movimiento.getTipoMovimiento().getTipo().equals(EnumTipoMovimiento.INGRESO.getValor()) ||
							movimiento.getTipoMovimiento().getTipo().equals(EnumTipoMovimiento.PRESTAMO.getValor())) {
						
						Chunk importe = new Chunk(movimiento.getImporte().toString(), greenFont);
						
						Paragraph paragraph3 = new Paragraph();
						Paragraph paragraph4 = new Paragraph();
						paragraph3.add(tipoMovimiento);
						paragraph3.add(importe);
						paragraph4.add(saltoLinea);
						chapSecond.add(paragraph2);
						chapSecond.add(paragraph3);
						chapSecond.add(paragraph4);
					} else {
					
						Chunk importe = new Chunk(movimiento.getImporte().toString(), redFont);
						Paragraph paragraph3 = new Paragraph();
						Paragraph paragraph4 = new Paragraph();
						paragraph3.add(tipoMovimiento);
						paragraph3.add(importe);
						paragraph4.add(saltoLinea);
						chapSecond.add(paragraph2);
						chapSecond.add(paragraph3);
						chapSecond.add(paragraph4);
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

}
