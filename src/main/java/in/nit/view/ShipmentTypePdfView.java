package in.nit.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.GroupLayout.Alignment;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Row;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import in.nit.model.ShipmentType;

public class ShipmentTypePdfView extends AbstractPdfView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ShipmentType> list = null;
		Paragraph p = null;
		Table t = null;

		// read data from controller
		list = (List<ShipmentType>) model.get("obs");

		// set download +file
		response.setHeader("Content-Disposition", "attachment;filename=ShipmentTypes.pdf");
		// get paragraph object
		p = new Paragraph("Shipment Type Report", new Font(Font.HELVETICA,20,Font.BOLD,Color.BLUE));
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		t = new Table(6, list.size());
		t.setWidths(new float[] {0.5f,1.0f,1.2f,1.2f,1.0f,1.5f});
		t.setAlignment(Element.ALIGN_CENTER);
		t.setPadding(1.0f);
		setHead(t);

		setBody(document, list, t);
	}

	private void setHead(Table t) throws BadElementException {
	
		t.addCell("ID");
		t.addCell("MODE");
		t.addCell("CODE");
		t.addCell("ENABLE");
		t.addCell("GRADE");
		t.addCell("DESC");

	}

	private void setBody(Document doc, List<ShipmentType> list, Table t) throws BadElementException, DocumentException {
		for (ShipmentType st : list) {

			t.addCell(String.valueOf(st.getId()));
			t.addCell(st.getShipmentMode());
			t.addCell(st.getShipmentCode());
			t.addCell(st.getEnableShipment());
			t.addCell(st.getShipmentGrade());
			t.addCell(st.getDescription());
		}
		doc.add(t);
	}

}
