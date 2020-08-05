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

public class HeaderFooter extends PdfPageEventHelper {

	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle rect = writer.getBoxSize("art");
		Paragraph p1 = new Paragraph("Página: " + writer.getPageNumber(),
				FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD));
		Phrase footer = new Phrase();
		footer.add(p1);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, footer,
				(rect.getRight() - rect.getLeft()) / 1, rect.getBottom() - 10, 0);

	}

}
