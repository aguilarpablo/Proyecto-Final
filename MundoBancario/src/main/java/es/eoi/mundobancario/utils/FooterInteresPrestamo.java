package es.eoi.mundobancario.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class FooterInteresPrestamo extends PdfPageEventHelper {

	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle rect2 = writer.getBoxSize("art2");

		Paragraph p2 = new Paragraph("*Se cobrará un 2% de interés por importe de amortización",
				FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD));
		Phrase footer2 = new Phrase();
		footer2.add(p2);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, footer2, rect2.getLeft(),
				rect2.getBottom(), 0);

	}
}
