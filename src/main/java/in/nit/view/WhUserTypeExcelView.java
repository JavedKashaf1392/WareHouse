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
import in.nit.model.WhUserType;

public class WhUserTypeExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Download + filename
		response.setHeader("Content-Disposition", "attachment;filename=WhUserType.xlsx");
		
		//read data from controller
		@SuppressWarnings("unchecked")
		List<WhUserType>list =(List<WhUserType>) model.get("obs");
		
		Sheet sheet=workbook.createSheet("Wh User Types");
		setHead(sheet);
		setBody(sheet,list);
	}


	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("TYPE");
		row.createCell(2).setCellValue("CODE");
		row.createCell(3).setCellValue("FOR");
		row.createCell(4).setCellValue("EMAIL");
		row.createCell(5).setCellValue("CONTACT");
		row.createCell(6).setCellValue("IDTYPE");
		row.createCell(7).setCellValue("IDNUMBER");
	}

	private void setBody(Sheet sheet, List<WhUserType> list) {
		int rowNum=1;
		for(WhUserType wh:list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(wh.getId());
			row.createCell(1).setCellValue(wh.getUserType());
			row.createCell(2).setCellValue(wh.getUserCode());
			row.createCell(3).setCellValue(wh.getUserFor());
			row.createCell(4).setCellValue(wh.getUserEmail());
			row.createCell(5).setCellValue(wh.getUserContact());
			row.createCell(6).setCellValue(wh.getUserIdType());
			row.createCell(7).setCellValue(wh.getIdNumber());
			
		}
		
	}
}
