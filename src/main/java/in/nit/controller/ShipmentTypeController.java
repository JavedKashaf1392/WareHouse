package in.nit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.nit.model.ShipmentType;
import in.nit.service.IShipmentTypeService;
import in.nit.spec.ShipmentTypeSpec;
import in.nit.util.ShipmentTypeUtil;
import in.nit.view.ShipmentTypeExcelView;
import in.nit.view.ShipmentTypePdfView;

@Controller
@RequestMapping("/shipmenttype")
public class ShipmentTypeController {

	private Logger logger = LoggerFactory.getLogger(OrderMethodController.class);
	@Autowired
	private IShipmentTypeService service;

	@Autowired
	private ServletContext context;

	@Autowired
	private ShipmentTypeUtil util;

	// 1. Show Register Page
	/**
	 * URL :/register, Type:GET Goto Page ShipmentTypeRegister.html
	 */
	/*
	 * @GetMapping("/register") public String showRegister() { return
	 * "shipmentTypeRegister"; }
	 */
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("shipmentType", new ShipmentType());
		return "shipmentTypeRegister";
	}

	@GetMapping("/admin")
	public String showAdmin() {
		return "AdminPage";
	}

	// 2. save : on click submit
	/**
	 * URL: /save, Type: POST Goto : ShipmentTypeRegister
	 */
	/*
	 * @PostMapping("/save") public String save(@ModelAttribute ShipmentType
	 * shipmentType, Model model) {
	 * 
	 * Integer id = null; String message = null; // peform save operation id =
	 * service.saveShipmentType(shipmentType); // construct one message message =
	 * "ShipmentType '" + id + "' saved successfully"; // send message to UI
	 * model.addAttribute("message", message); // got to Page return
	 * "ShipmentTypeRegister"; }
	 */
	@PostMapping("/save")
	public String save(@ModelAttribute ShipmentType shipmentType, Model model) {

		Integer id = null;
		String message = null;
		// peform save operation
		id = service.saveShipmentType(shipmentType);
		// construct one message
		message = "ShipmentType '" + id + "' saved successfully";
		// send message to UI
		model.addAttribute("message", message);

		model.addAttribute("shipmentType", new ShipmentType());
		// got to Page
		return "ShipmentTypeRegister";
	}

	// 3.Displaying data:
	@GetMapping("/all")
	public String fetchAll(@ModelAttribute ShipmentType shipmentType,Model model) {
		
		Specification<ShipmentType>spec=new ShipmentTypeSpec(shipmentType);
		
		model.addAttribute("list", service.getAllShipmentTypes(spec));
		model.addAttribute("shipmentType", shipmentType);
		return "ShipmentTypeData";
	}

	// 4.delete record
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model) {
		String msg = null;
		// invoke service
		if (service.isShipmentTypeExist(id)) {
			service.deleteShipmentType(id);
			msg = "Shipment Type '" + id + "' Type deleted !";
		} else {

			msg = "Shipment Type '" + id + "' Not Existed !";
		}
		// display other records
		List<ShipmentType> list = service.getAllShipmentTypes();
		model.addAttribute("list", list);

		// send confirmation to UI
		model.addAttribute("message", msg);
		return "ShipmentTypeData";
	}

	// 5.Edit form
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<ShipmentType> opt = service.getOneShipmentType(id);
		if (opt.isPresent()) {
			ShipmentType st = opt.get();
			model.addAttribute("shipmentType", st);
			page = "ShipmentTypeEdit";
		} else {
			page = "redirect:../all";
		}
		return page;
	}

	// 6.update method
	@PostMapping("/update")
	public String update(@ModelAttribute ShipmentType shipmentType, Model model) {
		String msg = null;
		try {
			logger.info("Making call to service");
			service.updateShipmentType(shipmentType);
			logger.info("Send confirmation to client");
			msg = "Shipment Type '" + shipmentType.getId() + "' updated successfully..";
			model.addAttribute("message", msg);
			
			// display other records
			logger.info("Getting data from object");
			List<ShipmentType> list = service.getAllShipmentTypes();
			logger.info("Sending data to View Resolver");
			model.addAttribute("list", list);
			
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "ShipmentTypeData";
	}

	// 7.show One
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {

		String page = null;
		try {
			logger.info("Making service call");
			Optional<ShipmentType> opt = service.getOneShipmentType(id);
			logger.info("Reading record from object");
			ShipmentType st = opt.get();
			logger.info("mapping obj to view");
			model.addAttribute("ob", st);
			page = "ShipmentTypeView";
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return page;
	}

	// 8. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new ShipmentTypeExcelView());

		// send data to MAV view
		m.addObject("obs", service.getAllShipmentTypes());
		return m;
	}
	// 9. Export One row to Excel File

	@GetMapping("excel/{id}")
	public ModelAndView exportOneExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypeExcelView());
		Optional<ShipmentType> opt = service.getOneShipmentType(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 10. Export Data to Pdf File
	@GetMapping("/pdf")
	public ModelAndView exportToPdf() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new ShipmentTypePdfView());

		// send data to MAV view
		m.addObject("obs", service.getAllShipmentTypes());
		return m;
	}
	// 11. Export One row to Pdf File

	@GetMapping("pdf/{id}")
	public ModelAndView exportOnePdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypePdfView());
		Optional<ShipmentType> opt = service.getOneShipmentType(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 12. ---AJAX VALIDATION----------
	// .. /shipmenttype/validatecode?code=ABCD
	@GetMapping("/validatecode")
	public @ResponseBody String validateShipmentCode(@RequestParam String code, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.isShipmentTypeCodeExist(code)) {
			message = "Shipment Code <b>'" + code + "' Already exist</b>!";
		} else if (service.isShipmentTypeCodeExistForEdit(code, id)) {
			message = "Shipment Code <b>'" + code + "' Already exist</b>!";
		}
		return message;
	}

	// 12. Generate Chart

	@GetMapping("/charts")
	public String generateCharts() {
		// get data from service
		List<Object[]> list = service.getShipmentModeCount();

		// Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");

		// invoke chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);
		return "ShipmentTypeCharts";
	}

	// Google Chars
	@GetMapping("/gcharts")
	public @ResponseBody List<Object[]> getGoogleCharts() {
		List<Object[]> list = service.getShipmentModeCount();
		return list;
	}
}
