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

import in.nit.model.SaleOrder;

public class SaleOrderPdfView extends AbstractPdfView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SaleOrder> list = null;
		Paragraph p = null;
		Table t = null;

		// read data from controller
		list = (List<SaleOrder>) model.get("obs");

		// set download +file
		response.setHeader("Content-Disposition", "attachment;filename=SaleOrder.pdf");
		// get paragraph object
		p = new Paragraph("Sale Orders Report", new Font(Font.HELVETICA,20,Font.BOLD,Color.BLUE));
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		t = new Table(7, list.size());
		t.setWidths(new float[] {0.5f,1.0f,1.2f,1.2f,1.0f,1.5f,1.5f});
		t.setAlignment(Element.ALIGN_CENTER);
		t.setPadding(1.0f);
		setHead(t);

		setBody(document, list, t);
	}

	private void setHead(Table t) throws BadElementException {
	
		t.addCell("ID");
		t.addCell("CODE");
		t.addCell("REFNUM");
		t.addCell("STOCKMODE");	
		t.addCell("STOCKSOURCE");	
		t.addCell("DFSTATUS");	
		t.addCell("DESC");

	}

	private void setBody(Document doc, List<SaleOrder> list, Table t) throws BadElementException, DocumentException {
		for (SaleOrder so : list) {

			t.addCell(String.valueOf(so.getId()));
			t.addCell(so.getOrderCode());
			t.addCell(so.getReferenceNumber().toString());
			t.addCell(so.getStockMode());
			t.addCell(so.getStockSource());
			t.addCell(so.getStatus());
			t.addCell(so.getDescription());
		}
		doc.add(t);
	}

}
