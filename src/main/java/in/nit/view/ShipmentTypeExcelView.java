package in.nit.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import in.nit.model.ShipmentType;

public class ShipmentTypeExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Download + filename
		response.setHeader("Content-Disposition", "attachment;filename=ShipmentTypes.xlsx");
		
		//read data from controller
		@SuppressWarnings("unchecked")
		List<ShipmentType>list =(List<ShipmentType>) model.get("obs");
		
		Sheet sheet=workbook.createSheet("Shipment Types");
		setHead(sheet);
		setBody(sheet,list);
	}


	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("MODE");
		row.createCell(2).setCellValue("CODE");
		row.createCell(3).setCellValue("ENABLE");
		row.createCell(4).setCellValue("GRADE");
		row.createCell(5).setCellValue("DESCRIPTION");
	}

	private void setBody(Sheet sheet, List<ShipmentType> list) {
		int rowNum=1;
		for(ShipmentType st:list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(st.getId());
			row.createCell(1).setCellValue(st.getShipmentMode());
			row.createCell(2).setCellValue(st.getShipmentCode());
			row.createCell(3).setCellValue(st.getEnableShipment());
			row.createCell(4).setCellValue(st.getShipmentGrade());
			row.createCell(5).setCellValue(st.getDescription());
			
		}
		
	}
}
