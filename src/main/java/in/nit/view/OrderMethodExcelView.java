package in.nit.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import in.nit.model.OrderMethod;

public class OrderMethodExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Download + filename
				response.setHeader("Content-Disposition", "attachment;filename=OrderMethods.xlsx");
				
				//read data from controller
				@SuppressWarnings("unchecked")
				List<OrderMethod>list =(List<OrderMethod>) model.get("obs");
				
				Sheet sheet=workbook.createSheet("Order Method");
				setHead(sheet);
				setBody(sheet,list);
			}


			private void setHead(Sheet sheet) {
				Row row = sheet.createRow(0);
				row.createCell(0).setCellValue("ID");
				row.createCell(1).setCellValue("ORDERMODE");
				row.createCell(2).setCellValue("ORDERCODE");
				row.createCell(3).setCellValue("ORDERTYPE");
				row.createCell(4).setCellValue("ORDERACPT");
				row.createCell(5).setCellValue("DESCRIPTION");				
			}

			private void setBody(Sheet sheet, List<OrderMethod> list) {
				int rowNum=1;
				for(OrderMethod om:list) {
					Row row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(om.getId());
					row.createCell(1).setCellValue(om.getOrderMode());
					row.createCell(2).setCellValue(om.getOrderCode());
					row.createCell(3).setCellValue(om.getOrderType());
					row.createCell(4).setCellValue(om.getOrderAcpt().toString());
					row.createCell(5).setCellValue(om.getDescription());
					
				}

	}

}
