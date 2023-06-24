package in.nit.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import in.nit.model.SaleOrder;

public class SaleOrderExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Download + filename
		response.setHeader("Content-Disposition", "attachment;filename=SaleOrder.xlsx");
		
		//read data from controller
		@SuppressWarnings("unchecked")
		List<SaleOrder>list =(List<SaleOrder>) model.get("obs");
		
		Sheet sheet=workbook.createSheet("Sale Orders");
		setHead(sheet);
		setBody(sheet,list);
	}


	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("CODE");
		row.createCell(2).setCellValue("REFNUM");
		row.createCell(3).setCellValue("STOCKMODE");		
		row.createCell(4).setCellValue("STOCKSOURCE");		
		row.createCell(5).setCellValue("DFSTATUS");		
		row.createCell(6).setCellValue("DESC");
	}

	private void setBody(Sheet sheet, List<SaleOrder> list) {
		int rowNum=1;
		for(SaleOrder so:list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(so.getId());
			row.createCell(1).setCellValue(so.getOrderCode());
			row.createCell(2).setCellValue(so.getReferenceNumber());
			row.createCell(3).setCellValue(so.getStockMode());			
			row.createCell(4).setCellValue(so.getStockSource());			
			row.createCell(5).setCellValue(so.getStatus());			
			row.createCell(6).setCellValue(so.getDescription());
			
		}
		
	}
}
