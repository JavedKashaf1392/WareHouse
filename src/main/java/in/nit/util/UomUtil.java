package in.nit.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;
@Component
public class UomUtil{

	public void generatePieChart(String location, List<Object[]> list) {
		// a. Create DataSet and read List<Object[]> into DataSet values

		DefaultPieDataset dataset = new DefaultPieDataset();
	
		
		// get value from list
		for (Object[] ob : list) {
			dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}

		// b. Convert DataSet data into JFreeChart object using ChartFactory class
		JFreeChart chart = ChartFactory.createPieChart3D("Uom Type", dataset);
		chart.setBorderVisible(true);
		PiePlot plot = (PiePlot) chart.getPlot();
		/*
		 * for(Object[] ob:list) { plot.setSectionPaint(ob[0].toString(), new
		 * Color(255,new Random().nextInt(100),new Random().nextInt(10))); }
		 */
		plot.setSectionPaint("Train", Color.PINK);
		plot.setSectionPaint("Truck", Color.ORANGE);
		plot.setSectionPaint("Air", Color.GREEN);
		
		//// c. Convert JFreeChart object into one Image using ChartUtil class
		try {
			ChartUtils.saveChartAsJPEG(new File(location + "/uomA.jpg"), chart, 400, 300);		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void generateBarChart(String location, List<Object[]> list) {
		// a. Create DataSet and read List<Object[]> into DataSet values
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Object[] ob : list) {
			dataset.setValue(Double.valueOf(ob[1].toString()), ob[0].toString(), "");
		}

		// b. Convert DataSet data into JFreeChart object using ChartFactory class
		JFreeChart chart = ChartFactory.createBarChart("Uom Type ", "UOM TYPE", "COUNT", dataset);

		//// c. Convert JFreeChart object into one Image using ChartUtil class
		try {
			ChartUtils.saveChartAsJPEG(new File(location + "/uomB.jpg"), chart, 500, 340);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
