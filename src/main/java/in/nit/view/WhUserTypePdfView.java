package in.nit.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import in.nit.model.WhUserType;

public class WhUserTypePdfView extends AbstractPdfView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<WhUserType> list = null;
		Paragraph p = null;
		Table t = null;

		// read data from controller
		list = (List<WhUserType>) model.get("obs");

		// set download +file
		response.setHeader("Content-Disposition", "attachment;filename=WhUserType.pdf");
		// get paragraph object
		p = new Paragraph("Wh User  Type Report", new Font(Font.HELVETICA,20,Font.BOLD,Color.GRAY));
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		t = new Table(8, list.size());
		t.setWidths(new float[] {3.5f,3.5f,3.5f,3.5f,5.5f,5.0f,6.0f,5.5f});
		t.setAlignment(Element.ALIGN_CENTER);
		t.setPadding(1.0f);
		setHead(t);

		setBody(document, list, t);
	}

	private void setHead(Table t) throws BadElementException {
	
		t.addCell("ID");
		t.addCell("TYPE");
		t.addCell("CODE");
		t.addCell("FOR");
		t.addCell("EMAIL");
		t.addCell("CONTACT");
		t.addCell("IDTYPE");
		t.addCell("IDNUMBER");

	}

	private void setBody(Document doc, List<WhUserType> list, Table t) throws BadElementException, DocumentException {
		for (WhUserType wh: list) {

			t.addCell(wh.getId().toString());
			t.addCell(wh.getUserType());
			t.addCell(wh.getUserCode());
			t.addCell(wh.getUserFor());
			t.addCell(wh.getUserEmail());
			t.addCell(wh.getUserContact());
			t.addCell(wh.getUserIdType());
			t.addCell(wh.getIdNumber());
		}
		doc.add(t);
	}

}
